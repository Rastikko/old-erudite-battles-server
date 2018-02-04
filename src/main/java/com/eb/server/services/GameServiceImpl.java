package com.eb.server.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.*;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.types.GameType;
import com.eb.server.domain.types.UserStateType;
import com.eb.server.repositories.GameRepository;
import com.eb.server.repositories.MatchmakingRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    GameMapper gameMapper;
    GameRepository gameRepository;
    GamePhaseService gamePhaseService;
    UserService userService;
    MatchmakingRequestRepository matchmakingRequestRepository;

    public GameServiceImpl(
            GameMapper gameMapper,
            GameRepository gameRepository,
            MatchmakingRequestRepository matchmakingRequestRepository,
            GamePhaseService gamePhaseService,
            UserService userService) {
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;
        this.matchmakingRequestRepository = matchmakingRequestRepository;
        this.gamePhaseService = gamePhaseService;
        this.userService = userService;
    }

    @Override
    public GameDTO requestNewGame(RequestGameDTO requestGameDTO) {
        if (requestGameDTO.getType().equals(GameType.VS_PLAYER)) {
            return createNewVSGame(requestGameDTO);
        }
        return createNewBotGame(requestGameDTO);
    }

    @Override
    public GameDTO handleCommand(Long gameId, GameCommandDTO gameCommandDTO) {
        Game game = gameRepository.findOne(gameId);
        // TODO: we should be able to just use the gameCommandDTO
        GameCommand command = gameMapper.requestGameCommandDTOToGameCommand(gameCommandDTO);

        gamePhaseService.handleCommand(game, command);

        if (game.getGamePhase().equals(GamePhaseType.PHASE_NONE)) {
            game.getGamePlayers().stream().forEach(this::removeGameFromUser);
        }

        return saveAndReturnDTO(game);
    }

    @Override
    public GameDTO findGameDTOById(Long gameId) {
        return gameMapper.gameToGameDTO(gameRepository.findOne(gameId));
    }

    private GameDTO createNewVSGame(RequestGameDTO requestGameDTO) {
        User user = userService.findUserByID(requestGameDTO.getUserId());
        MatchmakingRequest matchmakingRequest = matchmakingRequestRepository.findFirstByOrderByRequestDateAsc();

        if (matchmakingRequest == null) {
            createMatchmakingRequest(user);
            return null;
        }

        User otherUser = userService.findUserByID(matchmakingRequest.getUserId());
        GamePlayer gamePlayer = createGamePlayer(user);
        GamePlayer otherGamePlayer = createGamePlayer(otherUser);
        List<GamePlayer> gamePlayers = new ArrayList<>();

        gamePlayers.add(gamePlayer);
        gamePlayers.add(otherGamePlayer);

        Game game = createNewGame(gamePlayers, GameType.VS_PLAYER);

        GameDTO savedGameDTO = saveAndReturnDTO(game);

        user.setGameId(savedGameDTO.getId());
        user.setState(UserStateType.IN_GAME);
        otherUser.setGameId(savedGameDTO.getId());
        otherUser.setState(UserStateType.IN_GAME);

        userService.updateUser(user);
        userService.updateUser(otherUser);

        return savedGameDTO;
    }

    private GameDTO createNewBotGame(RequestGameDTO requestGameDTO) {
        User user = userService.findUserByID(requestGameDTO.getUserId());
        User bot = userService.findUserByID(Bootstrap.BOT_ID);

        GamePlayer gamePlayerBot = createGamePlayer(bot);
        gamePlayerBot.setIsBot(true);

        List<GamePlayer> gamePlayers = new ArrayList<>();

        gamePlayers.add(gamePlayerBot);
        gamePlayers.add(createGamePlayer(user));

        Game game = createNewGame(gamePlayers, GameType.VS_BOT);

        GameDTO savedGameDTO = saveAndReturnDTO(game);
        user.setGameId(savedGameDTO.getId());
        user.setState(UserStateType.IN_GAME);
        userService.updateUser(user);

        return savedGameDTO;
    }

    private Game createNewGame(List<GamePlayer> gamePlayers, GameType gameType) {
        Game game = new Game();

        game.setGamePlayers(gamePlayers);
        game.setTurn(1);
        game.setGameType(gameType);

        gamePhaseService.handleNewGame(game);

        return game;
    }

    private void createMatchmakingRequest(User user) {
        MatchmakingRequest matchmakingRequest = new MatchmakingRequest();
        matchmakingRequest.setUserId(user.getId());
        matchmakingRequest.setRequestDate(new GregorianCalendar());
        user.setState(UserStateType.SEARCHING_GAME);
        matchmakingRequestRepository.save(matchmakingRequest);
        userService.updateUser(user);
    }

    private GamePlayer createGamePlayer(User user) {

        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setUserId(user.getId());

        // TODO: derive these from user attributes
        gamePlayer.setAttack(50);
        gamePlayer.setHealth(200);

        gamePlayer.setDeck(shuffleDeck(user.getDeck(), gamePlayer));
        gamePlayer.setHand(new ArrayList<>());

        return gamePlayer;
    }

    private List<GameCard> shuffleDeck(List<Card> userDeck, GamePlayer gamePlayer) {
        // TODO: shuffle
        List<GameCard> deck = gameMapper.cardsToGameCards(userDeck);
        return deck;
    }

    private void removeGameFromUser(GamePlayer gamePlayer) {
        User user = userService.findUserByID(gamePlayer.getUserId());
        user.setGameId(null);
        userService.updateUser(user);
    }

    private GameDTO saveAndReturnDTO(Game game) {
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameDTO(savedGame);
    }
}

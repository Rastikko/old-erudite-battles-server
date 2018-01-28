package com.eb.server.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.*;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    GameMapper gameMapper;
    GameRepository gameRepository;
    GamePhaseService gamePhaseService;
    UserService userService;

    public GameServiceImpl(
            GameMapper gameMapper,
            GameRepository gameRepository,
            GamePhaseService gamePhaseService,
            UserService userService) {
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;
        this.gamePhaseService = gamePhaseService;
        this.userService = userService;
    }

    @Override
    public GameDTO requestNewGame(RequestGameDTO requestGameDTO) {
        User user = userService.findUserByID(requestGameDTO.getUserId());
        User bot = userService.findUserByID(Bootstrap.BOT_ID);
        Game game = createNewBotGame(user, bot);

        gamePhaseService.handleNewGame(game);

        GameDTO savedGameDTO = saveAndReturnDTO(game);

        user.setGameId(savedGameDTO.getId());
        userService.updateUser(user);

        return savedGameDTO;
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

    private Game createNewBotGame(User user, User bot) {
        Game game = new Game();

        GamePlayer gamePlayerBot = createGamePlayer(bot);
        gamePlayerBot.setIsBot(true);

        List<GamePlayer> gamePlayers = new ArrayList<>();

        gamePlayers.add(gamePlayerBot);
        gamePlayers.add(createGamePlayer(user));

        game.setGamePlayers(gamePlayers);
        game.setTurn(1);

        return game;
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

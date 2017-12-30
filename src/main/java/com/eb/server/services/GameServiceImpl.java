package com.eb.server.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.RequestGameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.*;
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
    public GameDTO createNewGame(RequestGameDTO requestGameDTO) {
        User user = userService.findUserByID(requestGameDTO.getUserId());
        User bot = userService.findUserByID(Bootstrap.BOT_ID);
        Game game = createNewBotGame(user, bot);

        gamePhaseService.handleNewGame(game);

        return saveAndReturnDTO(game);
    }

    @Override
    public GameDTO handleCommand(Long gameId, RequestGameCommandDTO requestGameCommandDTO) {
        Game game = gameRepository.findOne(gameId);
        return null;
    }

    private Game createNewBotGame(User user, User bot) {
        Game game = new Game();

        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(createGamePlayer(game, bot));
        gamePlayers.add(createGamePlayer(game, user));

        game.setGamePlayers(gamePlayers);

        return game;
    }

    private GamePlayer createGamePlayer(Game game, User user) {

        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setUserId(user.getId());
        gamePlayer.setGame(game);

        gamePlayer.setDeck(shuffleDeck(user.getDeck(), gamePlayer));
        gamePlayer.setHand(new ArrayList<>());

        return gamePlayer;
    }

    private List<GameCard> shuffleDeck(List<Card> userDeck, GamePlayer gamePlayer) {
        // TODO: shuffle
        List<GameCard> deck = gameMapper.cardsToGameCards(userDeck);
        deck.stream().forEach(card -> {
            card.setDeckGamePlayer(gamePlayer);
            card.setHandGamePlayer(gamePlayer);
        });
        return deck;
    }

    private GameDTO saveAndReturnDTO(Game game) {
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameDTO(savedGame);
    }
}

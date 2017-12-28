package com.eb.server.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.Game;
import com.eb.server.domain.GamePlayer;
import com.eb.server.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    GameMapper gameMapper;
    GameRepository gameRepository;
    GamePhaseService gamePhaseService;

    public GameServiceImpl(GameMapper gameMapper, GameRepository gameRepository, GamePhaseService gamePhaseService) {
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;
        this.gamePhaseService = gamePhaseService;
    }

    @Override
    public GameDTO createNewGame(FindGameDTO findGameDTO) {
        Game game = createNewBotGame(findGameDTO.getUserId());
        gamePhaseService.handleNewGame(game);
        return saveAndReturnDTO(game);
    }

    private Game createNewBotGame(Long userId) {
        Game game = new Game();

        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(createGamePlayer(game, Bootstrap.BOT_ID));
        gamePlayers.add(createGamePlayer(game, userId));

        game.setGamePlayers(gamePlayers);

        return game;
    }

    private GamePlayer createGamePlayer(Game game, Long userId) {
        // TODO: swallow copy and shuffle the deck
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setUserId(userId);
        gamePlayer.setGame(game);
        return gamePlayer;
    }

    private GameDTO saveAndReturnDTO(Game game) {
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameDTO(savedGame);
    }
}

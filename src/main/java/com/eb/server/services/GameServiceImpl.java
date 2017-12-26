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

    public GameServiceImpl(GameMapper gameMapper, GameRepository gameRepository) {
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameDTO createNewGame(FindGameDTO findGameDTO) {
        Game game = createNewBotGame(findGameDTO.getUserId());
        return saveAndReturnDTO(game);
    }

    private Game createNewBotGame(Long userId) {
        Game game = new Game();

        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.add(createGamePlayer(Bootstrap.BOT_ID));
        gamePlayers.add(createGamePlayer(userId));

        game.setGamePlayers(gamePlayers);

        return game;
    }

    private GamePlayer createGamePlayer(Long userId) {
        // TODO: swallow copy and shuffle the deck
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setUserId(userId);
        return gamePlayer;
    }

    private GameDTO saveAndReturnDTO(Game game) {
        return gameMapper.gameToGameDTO(gameRepository.save(game));
    }
}

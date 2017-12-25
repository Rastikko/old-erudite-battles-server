package com.eb.server.services;

import com.eb.server.api.v1.mapper.GameMapper;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import com.eb.server.repositories.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    GameMapper gameMapper;
    GameRepository gameRepository;

    public GameServiceImpl(GameMapper gameMapper, GameRepository gameRepository) {
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameDTO createNewGame(Long userId) {
        Game game = createNewBotGame(userId);
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameDTO(savedGame);
    }

    private Game createNewBotGame(Long userId) {
        Game game = new Game();
        return game;
    }
}

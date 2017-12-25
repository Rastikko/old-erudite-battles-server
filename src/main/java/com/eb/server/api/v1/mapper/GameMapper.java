package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);
    GameDTO gameToGameDTO(Game game);
}

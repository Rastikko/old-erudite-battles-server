package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.domain.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    // TODO: this will not allow us filter the enemyPlayer data
    @Mappings({
            @Mapping(target = "gamePhase", ignore = true),
    })
    GameDTO gameToGameDTO(Game game);
}

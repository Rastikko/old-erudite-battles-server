package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.RequestGameCommandDTO;
import com.eb.server.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameCommand requestGameCommandDTOToGameCommand(RequestGameCommandDTO requestGameCommandDTO);

    @Mappings({
            @Mapping(target="id", ignore=true),
    })
    GameCard cardToGameCard(Card card);

    List<GameCard> cardsToGameCards(List<Card> cards);

    // TODO: filter the enemyPlayer data
    GameDTO gameToGameDTO(Game game);
}

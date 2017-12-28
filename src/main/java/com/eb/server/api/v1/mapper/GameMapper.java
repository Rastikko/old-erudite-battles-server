package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.GamePhaseDTO;
import com.eb.server.domain.Card;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameCard;
import com.eb.server.domain.GamePhase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    List<GameCard> cardsToGameCards(List<Card> cards);

    // TODO: this will not allow us filter the enemyPlayer data
    GameDTO gameToGameDTO(Game game);
    GamePhaseDTO gamePhaseToGamePhaseDTO(GamePhase gamePhase);
}

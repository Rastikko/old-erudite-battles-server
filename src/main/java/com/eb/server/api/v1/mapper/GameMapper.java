package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.GamePhaseDTO;
import com.eb.server.api.v1.model.GamePlayerDTO;
import com.eb.server.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameCommand requestGameCommandDTOToGameCommand(GameCommandDTO gameCommandDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true), // this is to avoid id collisions
    })
    GameCard cardToGameCard(Card card);

    @Mapping(target = "deck", expression = "java( Integer.valueOf(player.getDeck().size()) )")
    GamePlayerDTO gamePlayerDTOToGamePlayer(GamePlayer player);

    List<GameCard> cardsToGameCards(List<Card> cards);

    GamePhaseDTO gamePhaseToGamePhaseDTO(GamePhase gamePhase);

    // TODO: filter the enemyPlayer data
    GameDTO gameToGameDTO(Game game);
}

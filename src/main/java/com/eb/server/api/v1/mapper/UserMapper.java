package com.eb.server.api.v1.mapper;

import com.eb.server.api.v1.model.CardDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.Card;
import com.eb.server.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default Long cardToId(Card card) {
        return card.getId();
    }

    List<Long> userDeckToUserDTODeck(List<Card> deck);

    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target = "deck", ignore = true),
    })
    User userDTOToUser(UserDTO user);
}

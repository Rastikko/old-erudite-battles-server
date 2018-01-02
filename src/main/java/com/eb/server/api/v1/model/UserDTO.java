package com.eb.server.api.v1.model;

import com.eb.server.domain.Card;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private Long gameId;
    private String name;
    private List<Long> deck;
}

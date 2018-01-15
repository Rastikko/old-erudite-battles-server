package com.eb.server.api.v1.model;

import com.eb.server.domain.Card;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(hidden = true)
    private Long gameId;
    @ApiModelProperty(required = true)
    private String name;
    @ApiModelProperty(hidden = true)
    private List<Long> deck;
}

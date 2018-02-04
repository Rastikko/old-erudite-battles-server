package com.eb.server.api.v1.model;

import com.eb.server.domain.types.UserStateType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class UserDTO {
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(hidden = true)
    private Long gameId;
    @ApiModelProperty(required = true)
    private String name;
    @Enumerated(value = EnumType.STRING)
    private UserStateType state;
    @ApiModelProperty(hidden = true)
    private List<Long> deck;
}

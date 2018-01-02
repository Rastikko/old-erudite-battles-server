package com.eb.server.api.v1.model;

import com.eb.server.domain.types.AttributeType;
import lombok.Data;

@Data
public class AttributeDTO {
    private Long id;
    AttributeType attributeType;
    Integer value;
}

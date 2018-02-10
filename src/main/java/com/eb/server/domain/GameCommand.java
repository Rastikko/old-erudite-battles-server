package com.eb.server.domain;

import com.eb.server.domain.types.GameCommandType;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class GameCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private GameCommandType type;

    private Calendar date;

    private Long userId;
    private String payload;
}

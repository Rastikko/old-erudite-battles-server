package com.eb.server.domain.interfaces;

import com.eb.server.domain.Attribute;
import com.eb.server.domain.GamePlayer;

public class Attributer {
    public void setAttribute(GamePlayer player, Attribute attribute) {
        alignLogic(player, attribute);
    }

    void alignLogic(GamePlayer player, Attribute attribute) {
        Integer finalAlignment = player.getGameAlignment().getLogicAlignment() + attribute.getValue();
        player.getGameAlignment().setLogicAlignment(finalAlignment);
    }
}

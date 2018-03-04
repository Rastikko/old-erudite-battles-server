package com.eb.server.domain.interfaces;

import com.eb.server.domain.Attribute;
import com.eb.server.domain.Game;
import com.eb.server.domain.GamePlayer;

public class Attributer {
    public void setAttribute(GamePlayer player, Attribute attribute) {
        alignLogic(player, attribute);
    }

    public void setAttribute(Game game, Attribute attribute) {
        alignLogic(game, attribute);
    }

    void alignLogic(GamePlayer player, Attribute attribute) {
        Integer finalAlignment = player.getPlayerAlignment().getLogicAlignment() + attribute.getValue();
        player.getPlayerAlignment().setLogicAlignment(finalAlignment);
    }

    void alignLogic(Game game, Attribute attribute) {
        Integer finalAlignment = game.getGameAlignment().getLogicAlignment() + attribute.getValue();
        game.getGameAlignment().setLogicAlignment(finalAlignment);
    }
}

package com.eb.server.services.phases;

import com.eb.server.domain.Game;
import org.springframework.stereotype.Service;

@Service
public class PhaseHandlerNone extends AbstractPhaseHandler {
    @Override
    public void definePhaseAttributes(Game game) {

    }

    @Override
    public void handleBotCommands(Game game) {

    }
}

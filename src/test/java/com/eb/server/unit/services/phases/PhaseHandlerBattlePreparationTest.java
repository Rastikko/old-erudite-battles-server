package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.services.phases.PhaseHandlerBattlePreparation;
import org.junit.Before;
import org.junit.Test;

public class PhaseHandlerBattlePreparationTest {
    PhaseHandlerBattlePreparation phaseHandler;

    @Before
    public void setUp() {
        phaseHandler = new PhaseHandlerBattlePreparation();
        phaseHandler.RANDOM_DEFAULT_CATEGORY = false;
    }

    @Test
    public void shouldDisplayTheQuestionCategory() throws Exception {
        Game game = GameFixtures.botGame();
        phaseHandler.definePhase(game);

    }
}

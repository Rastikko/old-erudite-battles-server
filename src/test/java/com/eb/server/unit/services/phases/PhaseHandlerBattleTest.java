package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.services.QuestionService;
import com.eb.server.services.phases.PhaseHandlerBattle;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PhaseHandlerBattleTest {

    @Mock
    QuestionService questionService;


    PhaseHandlerBattle phaseHandlerBattle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        phaseHandlerBattle = new PhaseHandlerBattle(questionService);
    }

    @Test
    @DisplayName("PhaseHandlerBattle::handleCommandAnswer should return if not both players answered")
    public void handleCommandAnswerReturns() {
        Game game = GameFixtures.game();

        game.getGamePlayers().get(0).setCurrentGameQuestion(GameFixtures.gameQuestion());
        game.getGamePlayers().get(1).setCurrentGameQuestion(GameFixtures.gameQuestion());


    }

    @Test
    @DisplayName("PhaseHandlerBattle::handleCommandAnswer should set the performance and move question")
    public void handleCommandAnswerPerformance() {

    }
}

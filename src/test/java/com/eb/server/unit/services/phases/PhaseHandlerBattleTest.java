package com.eb.server.unit.services.phases;

import com.eb.server.GameFixtures;
import com.eb.server.domain.Game;
import com.eb.server.domain.GameQuestion;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.services.QuestionService;
import com.eb.server.services.phases.PhaseHandlerBattle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhaseHandlerBattleTest {

    @Mock
    QuestionService questionService;


    PhaseHandlerBattle phaseHandlerBattle;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        phaseHandlerBattle = new PhaseHandlerBattle(questionService);
    }

    @Test
    @DisplayName("PhaseHandlerBattle::handleCommandAnswer should return if not both players answered")
    public void handleCommandAnswerReturns() {
        Game game = GameFixtures.botGame();

        game.getGamePlayers().get(0).setCurrentGameQuestion(GameFixtures.gameQuestion());
        game.getGamePlayers().get(1).setCurrentGameQuestion(GameFixtures.gameQuestion());

        phaseHandlerBattle.handleCommand(game, GameFixtures.gameCommand(1L, GameCommandType.COMMAND_ANSWER, ""));

        String CORRECT_ANSWER = game.getGamePlayers().get(0).getCurrentGameQuestion().getQuestion().getCorrectAnswer();
        assertEquals(CORRECT_ANSWER, game.getGamePlayers().get(0).getCurrentGameQuestion().getSelectedAnswer());
        assertEquals(null, game.getGamePlayers().get(0).getCurrentGameQuestion().getPerformance());
        assertEquals(null, game.getGamePlayers().get(0).getCurrentGameQuestion().getPerformance());
    }

    @Test
    @DisplayName("PhaseHandlerBattle::handleCommandAnswer should set the performance and move question")
    public void handleCommandAnswerPerformance() {
        Game game = GameFixtures.botGame();

        game.getGamePlayers().get(0).setCurrentGameQuestion(GameFixtures.gameQuestion());
        game.getGamePlayers().get(1).setCurrentGameQuestion(GameFixtures.gameQuestion());

        String CORRECT_ANSWER = game.getGamePlayers().get(0).getCurrentGameQuestion().getQuestion().getCorrectAnswer();

        phaseHandlerBattle.handleCommand(game, GameFixtures.gameCommand(1L, GameCommandType.COMMAND_ANSWER, ""));
        phaseHandlerBattle.handleCommand(game, GameFixtures.gameCommand(2L, GameCommandType.COMMAND_ANSWER, CORRECT_ANSWER));

        GameQuestion botGameQuestion = game.getGamePlayers().get(0).getGameQuestions().get(0);
        GameQuestion playerGameQuestion = game.getGamePlayers().get(1).getGameQuestions().get(0);
        assertEquals(-1, botGameQuestion.getEndDate().compareTo(playerGameQuestion.getEndDate()));
        assertEquals(Integer.valueOf(0), game.getGamePlayers().get(0).getGameQuestions().get(0).getPerformance());
        assertEquals(Integer.valueOf(1), game.getGamePlayers().get(1).getGameQuestions().get(0).getPerformance());
        assertEquals(null, game.getGamePlayers().get(0).getCurrentGameQuestion());
    }
}
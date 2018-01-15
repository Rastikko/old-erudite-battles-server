package com.eb.server.services.phases;

import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.types.QuestionAffinityType;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhaseHandlerBattle extends AbstractPhaseHandler {

    QuestionService questionService;

    public PhaseHandlerBattle(QuestionService questionService) {
        this.questionService = questionService;

        GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE;
        NEXT_GAME_PHASE_TYPE = GamePhaseType.PHASE_BATTLE_RESOLUTION;
    }

    @Override
    public void definePhaseAttributes(Game game) {
        Question question = getNextQuestion(/*game*/);
        game.getGamePlayers().get(0).getGameQuestions().add(getNextGameQuestion(game,question));
        game.getGamePlayers().get(1).getGameQuestions().add(getNextGameQuestion(game,question));
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) {
        switch (gameCommand.getType()) {
            case COMMAND_ANSWER:
                handleCommandAnswer(game, gameCommand);
                return;
        }
        super.handleCommand(game, gameCommand);
    }

    @Override
    public void handleBotCommands(Game game) {
        handleCommandEnd(game, createBotCommand(GameCommandType.COMMAND_END, ""));

    }

    void handleCommandAnswer(Game game, GameCommand gameCommand) {
        GamePlayer gamePlayer = findGamePlayerCommand(game.getGamePlayers(), gameCommand);
        GamePlayer otherGamePlayer = findOtherGamePlayerCommand(game.getGamePlayers(), gameCommand);

        GameQuestion gameQuestion = gamePlayer.getCurrentGameQuestion();

        if (gamePlayer.getIsBot()) {
            Calendar endTime = (Calendar) gameQuestion.getStartDate().clone();
            endTime.add(Calendar.SECOND, gameQuestion.getQuestion().getAverageAnswerTime());
            gameQuestion.setSelectedAnswer(gameQuestion.getQuestion().getCorrectAnswer());
            gameQuestion.setEndDate(endTime);
        } else {
            gameQuestion.setEndDate(Calendar.getInstance());
            gameQuestion.setSelectedAnswer(gameCommand.getPayload());
        }

        if (otherGamePlayer.getCurrentGameQuestion().getEndDate() == null) {
            // we wait until both players have answer the question
            return;
        }

        // check both game players questions, if both are correct then use time to tiebreak

        GamePlayer victoriousGamePlayer = game.getGamePlayers().stream()
                .filter(gp -> gp.getCurrentGameQuestion().getSelectedAnswer().equals(gp.getCurrentGameQuestion().getQuestion().getCorrectAnswer()))
                .sorted((gp1, gp2) -> gp1.getCurrentGameQuestion().getEndDate().compareTo(gp2.getCurrentGameQuestion().getEndDate()))
                .findFirst()
                .get();

        gameQuestion.setPerformance(1);
        victoriousGamePlayer.getGameQuestions().add(gameQuestion);
    }

    Question getNextQuestion(/*Game game*/) {
        // TODO: calculate next category and affinity
        List<Long> excludedIds = new ArrayList<>();
        // HACK: JPA NotIn requires at least 1 element
        excludedIds.add(-1L);
        QuestionCategoryType category = QuestionCategoryType.TRIGONOMETRY;
        QuestionAffinityType affinity = QuestionAffinityType.LOGIC;

        return questionService.getRandomQuestion(category, affinity, excludedIds);
    }

    GameQuestion getNextGameQuestion(Game game, Question question) {
        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setQuestion(question);
        gameQuestion.setStartDate(Calendar.getInstance());
        gameQuestion.setTurn(game.getTurn());

        return gameQuestion;
    }
}

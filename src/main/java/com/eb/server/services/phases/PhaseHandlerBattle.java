package com.eb.server.services.phases;

import com.eb.server.domain.*;
import com.eb.server.domain.types.GameCommandType;
import com.eb.server.domain.types.GamePhaseType;
import com.eb.server.domain.types.QuestionCategoryType;
import com.eb.server.domain.types.QuestionSubcategoryType;
import com.eb.server.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
        game.getGamePlayers().get(0).setCurrentGameQuestion(getNextGameQuestion(game,question));
        game.getGamePlayers().get(1).setCurrentGameQuestion(getNextGameQuestion(game,question));
    }

    @Override
    public void handleCommand(Game game, GameCommand gameCommand) throws Exception {
        switch (gameCommand.getType()) {
            case COMMAND_ANSWER:
                handleCommandAnswer(game, gameCommand);
        }
        super.handleCommand(game, gameCommand);
    }

    @Override
    public void handleBotCommands(Game game) throws Exception {
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_ANSWER, ""));
        handleCommand(game, createBotCommand(GameCommandType.COMMAND_END, ""));

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
        setCurrentQuestionOutcome(game);

        // TODO: check some sort of question stack to see if there is another question in this turn
    }

    void setCurrentQuestionOutcome(Game game) {
        GamePlayer victoriousGamePlayer = game.getGamePlayers().stream()
                .filter(gp -> gp.getCurrentGameQuestion().getSelectedAnswer().equals(gp.getCurrentGameQuestion().getQuestion().getCorrectAnswer()))
                .sorted((gp1, gp2) -> gp2.getCurrentGameQuestion().getEndDate().compareTo(gp1.getCurrentGameQuestion().getEndDate()))
                .findFirst()
                .get();

        if (victoriousGamePlayer != null) {
            GamePlayer defeatedGamePlayer = game.getGamePlayers().stream()
                .filter(gp -> gp.getUserId() != victoriousGamePlayer.getUserId())
                .findFirst()
                .get();

            GameQuestion victoriousGameQuestion = victoriousGamePlayer.getCurrentGameQuestion();
            GameQuestion defeatedGameQuestion = defeatedGamePlayer.getCurrentGameQuestion();

            victoriousGameQuestion.setPerformance(1);
            defeatedGameQuestion.setPerformance(0);

            victoriousGamePlayer.getGameQuestions().add(victoriousGameQuestion);
            defeatedGamePlayer.getGameQuestions().add(defeatedGameQuestion);

            victoriousGamePlayer.setCurrentGameQuestion(null);
            defeatedGamePlayer.setCurrentGameQuestion(null);
        } else {
            // TODO: test and reduce the complexity
            GameQuestion firstGameQuestion = game.getGamePlayers().get(0).getCurrentGameQuestion();
            GameQuestion secondGameQuestion = game.getGamePlayers().get(1).getCurrentGameQuestion();

            firstGameQuestion.setPerformance(0);
            secondGameQuestion.setPerformance(0);

            game.getGamePlayers().get(0).getGameQuestions().add(firstGameQuestion);
            game.getGamePlayers().get(1).getGameQuestions().add(secondGameQuestion);

            game.getGamePlayers().get(0).setCurrentGameQuestion(null);
            game.getGamePlayers().get(1).setCurrentGameQuestion(null);
        }
    }

    Question getNextQuestion(/*Game game*/) {
        // TODO: calculate next category and subcategory
        List<Long> excludedIds = new ArrayList<>();
        // HACK: JPA NotIn requires at least 1 element
        excludedIds.add(-1L);
        QuestionCategoryType category = QuestionCategoryType.LOGIC;
        QuestionSubcategoryType subcategory = QuestionSubcategoryType.TRIGONOMETRY;

        return questionService.getRandomQuestion(category, subcategory, excludedIds);
    }

    GameQuestion getNextGameQuestion(Game game, Question question) {
        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setQuestion(question);
        gameQuestion.setStartDate(new GregorianCalendar());
        gameQuestion.setTurn(game.getTurn());

        return gameQuestion;
    }
}

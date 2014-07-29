package com.breanawiggins.quizdom;

import android.app.Application;

import com.breanawiggins.quiz.QuizPlay;

public class AppActivity extends Application {
    private QuizPlay currentGame;

    /**
     * @param currentGame
     *            the currentGame to set
     */
    public void setCurrentGame(QuizPlay currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * @return the currentGame
     */
    public QuizPlay getCurrentGame() {
        return this.currentGame;
    }
}
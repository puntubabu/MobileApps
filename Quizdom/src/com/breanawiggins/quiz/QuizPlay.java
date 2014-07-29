package com.breanawiggins.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizPlay {

    private int numRounds;
    private int difficulty;
    private String playerName;
    private int right;
    private int wrong;
    private int round;

    private List<Question> questions = new ArrayList<Question>();

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * @param playerName
     *            the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the right
     */
    public int getRight() {
        return this.right;
    }

    /**
     * @param right
     *            the right to set
     */
    public void setRight(int right) {
        this.right = right;
    }

    /**
     * @return the wrong
     */
    public int getWrong() {
        return this.wrong;
    }

    /**
     * @param wrong
     *            the wrong to set
     */
    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    /**
     * @return the round
     */
    public int getRound() {
        return this.round;
    }

    /**
     * @param round
     *            the round to set
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * @param difficulty
     *            the difficulty to set
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the difficulty
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * @param questions
     *            the questions to set
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * @param q
     *            the question to add
     */
    public void addQuestions(Question q) {
        this.questions.add(q);
    }

    /**
     * @return the questions
     */
    public List<Question> getQuestions() {
        return this.questions;
    }

    public Question getNextQuestion() {

        // get the question
        Question next = this.questions.get(this.getRound());
        // update the round number to the next round
        this.setRound(this.getRound() + 1);
        return next;
    }

    /**
     * method to increment the number of correct answers this game
     */
    public void incrementRightAnswers() {
        this.right++;
    }

    /**
     * method to increment the number of incorrect answers this game
     */
    public void incrementWrongAnswers() {
        this.wrong++;
    }

    /**
     * @param numRounds
     *            the numRounds to set
     */
    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    /**
     * @return the numRounds
     */
    public int getNumRounds() {
        return this.numRounds;
    }

    /**
     * method that checks if the game is over
     * 
     * @return boolean
     */
    public boolean isGameOver() {
        return (this.getRound() >= this.getNumRounds());
    }

}
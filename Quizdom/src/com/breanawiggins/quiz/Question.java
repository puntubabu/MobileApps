package com.breanawiggins.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    private String question;
    private String answer;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String category;
    private int rating;

    /**
     * @return the question
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return this.answer;
    }

    /**
     * @param answer
     *            the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the choice1
     */
    public String getChoice1() {
        return this.choice1;
    }

    /**
     * @param choice1
     *            the choice1
     */
    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    /**
     * @return the choice2
     */
    public String getChoice2() {
        return this.choice2;
    }

    /**
     * @param choice2
     *            the choice2
     */
    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    /**
     * @return the choice3
     */
    public String getChoice3() {
        return this.choice3;
    }

    /**
     * @param choice
     *            3 the choice3
     */
    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * @param cat
     *            the category
     */
    public void setCategory(String cat) {
        this.category = cat;
    }

    public List<String> getQuestionOptions() {
        List<String> shuffle = new ArrayList<String>();
        shuffle.add(this.answer);
        shuffle.add(this.choice1);
        shuffle.add(this.choice2);
        shuffle.add(this.choice3);
        // shuffle.add(this.choice4);
        Collections.shuffle(shuffle);
        return shuffle;
    }

}
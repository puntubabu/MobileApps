package com.breanawiggins.quiz;

import com.breanawiggins.quizdom.R;

public class Helper {

    /**
     * This method selects a end game response based on the players score and
     * current difficulty level
     * 
     * @param numCorrect
     *            - num correct answers
     * @param numRounds
     *            - number of questions
     * @param diff
     *            - the difficulty level
     * @return String comment
     */
    public static String getResultComment(int numCorrect) {
        String comm = "Good Job";

        return comm;
    }

    /**
     * Method to return an image to use for the end of game screen
     * 
     * @param numCorrect
     *            - number of correct answers
     * @param numRounds
     *            - number of rounds
     * @param diff
     *            - difficulty level
     * @return int Image ID
     */
    public static int getResultImage(int numCorrect, int numRounds) {
        // calculate percentage
        int percentage = calculatePercentage(numCorrect, numRounds);

        // work out which image
        return R.drawable.loginback;

    }

    /**
     * Calculate the percentage result based on the number correct and number of
     * questions
     * 
     * @param numCorrect
     *            - number of questions right
     * @param numRounds
     *            - total number of questions
     * @return int percentage correct
     */
    private static int calculatePercentage(int numCorrect, int numRounds) {
        double frac = (double) numCorrect / (double) numRounds;
        int percentage = (int) (frac * 100);
        return percentage;
    }
}
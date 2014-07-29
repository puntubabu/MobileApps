package com.breanawiggins.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDatabase {

    Connection con;
    Statement stmt;
    ResultSet rs;

    public QuestionDatabase() {
        this.Connect();
    }

    public void Connect() {
        try {
            String host = "sql4.freemysqlhosting.net";
            String username = "sql445515";
            String password = "wT9%kZ7*";
            this.con = DriverManager.getConnection(host, username, password);

        } catch (SQLException e) {
            System.out.print("Error connecting");
        }
    }

    // Add your public helper methods to access and get content from the
    // database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd
    // be easy
    // to you to create adapters for your views.

    public List<Question> getQuestionSet(String category) {
        List<Question> questionSet = new ArrayList<Question>();
        try {
            this.stmt = this.con.createStatement();
            String sql = "SELECT * FROM questions WHERE category = \'"
                    + category + "\'";
            this.rs = this.stmt.executeQuery(sql);

            while (this.rs.next()) {
                // Log.d("QUESTION", "Question Found in DB: " + c.getString(1));
                Question q = new Question();
                q.setQuestion(this.rs.getString("question"));
                q.setAnswer(this.rs.getString("answer"));
                q.setChoice1(this.rs.getString("choice1"));
                q.setChoice2(this.rs.getString("choice2"));
                q.setChoice3(this.rs.getString("choice3"));
                questionSet.add(q);
            }
        } catch (SQLException e) {

        }

        return questionSet;
    }
}
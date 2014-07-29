package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.breanawiggins.quiz.Category;
import com.breanawiggins.quiz.Question;

public class Quiz extends Activity implements OnClickListener {

    public Question currentQ = new Question();
    public int score = 0;
    public int answered = 0;
    // public Connection con;
    public List<Question> questions = new ArrayList<Question>();
    public int numq = 0;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    public JSONObject o = new JSONObject();
    public List<Question> idk = new ArrayList<Question>();
    public List<Question> filtered = new ArrayList<Question>();
    public List<String> asked = new ArrayList<String>();
    public Category c = new Category();
    View btn1;
    View btn2;
    View btn3;
    View btn4;

    public static GameSessionManager session;

    private static final String QURL = "http://pradeepkeshary.com/webservice/questions2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.quiz_question);

        this.btn1 = this.findViewById(R.id.button1);
        this.btn1.setOnClickListener(this);
        this.btn2 = this.findViewById(R.id.button2);
        this.btn2.setOnClickListener(this);
        this.btn3 = this.findViewById(R.id.button3);
        this.btn3.setOnClickListener(this);
        this.btn4 = this.findViewById(R.id.button4);
        this.btn4.setOnClickListener(this);
        String userscore = Integer.toString(this.score);
        TextView sc = (TextView) this.findViewById(R.id.scoretxt);
        sc.setText("Score = " + userscore);
        session = new GameSessionManager(this.getApplicationContext());
        new AsyncTaskParseJson().execute();

    }

    public void addToScore() {
        this.score = this.score + 10;
        String userscore = Integer.toString(this.score);
        TextView sc = (TextView) this.findViewById(R.id.scoretxt);
        sc.setText("Score = " + userscore);
    }

    public void setupQuestion() {
        List<Question> newlist = this.idk;
        Log.d("Getting question", "new");
        this.filtered = this.getFilteredQuestions(newlist);
        Log.d("Completed", "!");
        Question w = this.filtered.get(0);
        Log.d("The cat is:", w.getCategory());
        this.getNextQuestion();
    }

    public void getNextQuestion() {
        // this.questions = this.getQuestionsJson();
        this.answered = this.answered + 1;
        // if (this.answered == 7) {
        // this.quizFinished();
        // }
        if (this.filtered.size() > 0) {
            Collections.shuffle(this.filtered);
            Question qt = new Question();
            qt = this.filtered.get(0);
            this.filtered.remove(0);
            this.currentQ = qt;

            this.setNextQuestions(qt);
        } else {
            this.quizFinished();
        }

        // this.setNextQuestions(q);

    }

    private void setNextQuestions(Question qu) {
        // set the question text from current question
        this.currentQ = qu;
        String question = qu.getQuestion();
        TextView qText = (TextView) this.findViewById(R.id.quizquestion);
        qText.setText(question);

        // set the available options
        List<String> answers = qu.getQuestionOptions();
        Log.d("Answers are:", answers.toString());
        Button option1 = (Button) this.findViewById(R.id.button1);
        option1.setText(answers.get(0));

        Button option2 = (Button) this.findViewById(R.id.button2);
        option2.setText(answers.get(1));

        Button option3 = (Button) this.findViewById(R.id.button3);
        option3.setText(answers.get(2));

        Button option4 = (Button) this.findViewById(R.id.button4);
        option4.setText(answers.get(3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void quizFinished() {
        session.setFinalScore(this.score);
        this.startActivity(new Intent(this, EndGame.class));
    }

    /**
     * Check if a checkbox has been selected, and if it has then check if its
     * correct and update gamescore
     * 
     * @return
     */
    private void checkAnswer(String answer) {
        // Log.d("Questions",
        // "Valid Checkbox selection made - check if correct");
        if (this.currentQ.getAnswer().equalsIgnoreCase(answer)) {
            this.addToScore();
            // Log.d("Questions", "Correct Answer!");
            // this.currentGame.incrementRightAnswers();
            this.getNextQuestion();
            // this.done();
        }
        this.getNextQuestion();
        // else {
        // Log.d("Questions", "Incorrect Answer!");
        // this.currentGame.incrementWrongAnswers();
        // }

    }

    /**
*
*/
    /*
     * private String getSelectedAnswer() { RadioButton c1 = (RadioButton)
     * this.findViewById(R.id.radioButton1); RadioButton c2 = (RadioButton)
     * this.findViewById(R.id.radioButton2); RadioButton c3 = (RadioButton)
     * this.findViewById(R.id.radioButton3); RadioButton c4 = (RadioButton)
     * this.findViewById(R.id.radioButton4); if (c1.isChecked()) { return
     * c1.getText().toString(); } if (c2.isChecked()) { return
     * c2.getText().toString(); } if (c3.isChecked()) { return
     * c3.getText().toString(); } if (c4.isChecked()) { return
     * c4.getText().toString(); }
     * 
     * return null; }
     */
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        // List<Question> idk = new ArrayList<Question>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Quiz.this.pDialog = new ProgressDialog(Quiz.this);
            Quiz.this.pDialog.setMessage("Attempting login...");
            Quiz.this.pDialog.setIndeterminate(false);
            Quiz.this.pDialog.setCancelable(true);
            // pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // JSONParser jsonP = new JSONParser();
            // JSONArray j = this.jsonParser.getJSONArrayFromUrl(QURL);
            // JSONArray j = this.jsonParser.getJSONArrayFromUrl(QURL);
            Log.d("request!", "starting");
            JSONArray j = Quiz.this.jsonParser.getJSONArrayFromUrl(QURL);
            // JSONObject obj = jsonParser.getJSONFromUrl(QURL);
            Log.d("Login attempt", j.toString());

            try {

                for (int i = 0; i < j.length(); i++) {
                    Question qs = new Question();
                    JSONObject obj = j.getJSONObject(i);
                    // Quiz.this.o = j.getJSONObject(i);
                    Log.d("request object", obj.toString());
                    // Storing each json item in variable
                    qs.setQuestion(obj.getString("question"));
                    qs.setAnswer(obj.getString("answer"));
                    qs.setChoice1(obj.getString("choice1"));
                    qs.setChoice2(obj.getString("choice2"));
                    qs.setChoice3(obj.getString("choice3"));
                    qs.setCategory(obj.getString("category"));
                    Log.d("request!", "start question");
                    // Quiz.this.setQuestions();
                    Log.d("request!", "done");
                    // q.add(qs);
                    Quiz.this.idk.add(qs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            // pDialog.dismiss();
            /*
             * for (int k = 0; k < Quiz.this.idk.size(); k++) { int random =
             * (int) (Math.random() * 3 + 1); Question qt = new Question(); qt =
             * Quiz.this.idk.get(random); Quiz.this.setNextQuestions(qt); }
             */
            Log.d("setting up", "here we are");
            Quiz.this.setupQuestion();
            // if (file_url != null) {
            // Toast.makeText(Quiz.this, file_url, Toast.LENGTH_LONG).show();
            // }

        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Log.d("Checking answer", "!");
        Log.d("Chosen is", b.getText().toString());
        if (v.getId() == R.id.button1) {
            this.checkAnswer(b.getText().toString());
            // this.startActivity(new Intent(this, EndGame.class));
            this.getNextQuestion();
        } else if (v.getId() == R.id.button2) {
            this.checkAnswer(b.getText().toString());
            // this.startActivity(new Intent(this, EndGame.class));
            this.getNextQuestion();
        } else if (v.getId() == R.id.button3) {
            this.checkAnswer(b.getText().toString());
            // this.startActivity(new Intent(this, EndGame.class));
            this.getNextQuestion();
        } else if (v.getId() == R.id.button4) {
            this.checkAnswer(b.getText().toString());
            // this.startActivity(new Intent(this, EndGame.class));
            this.getNextQuestion();
        }

    }

    public List<Question> getFilteredQuestions(List<Question> q) {
        List<Question> qbyCategory = new ArrayList<Question>();
        Log.d("getting category", "!");
        String category = "Sports";
        // this.c.getCategoryChosen();
        Log.d("The Category chosen is:", category);
        for (int i = 0; i < q.size(); i++) {
            Question qu = q.get(i);
            if (qu.getCategory().equalsIgnoreCase(category)) {
                qbyCategory.add(qu);
            }
        }
        return qbyCategory;
    }
}

package com.breanawiggins.quizdom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.breanawiggins.quiz.Category;

public class Categories extends Activity implements OnClickListener {

    public Category c = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_categories_list);

        View btnSports = this.findViewById(R.id.sports_category);
        btnSports.setOnClickListener(this);
        View btnFood = this.findViewById(R.id.food_category);
        btnFood.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        // Intent i;
        String category = "";
        if (v.getId() == R.id.sports_category) {
            category = "Sports";
            this.c.setCategoryChosen(category);
            Log.d("Category is", this.c.getCategoryChosen());
            this.startActivity(new Intent(this, Quiz.class));
            // startActivityForResult(i, Constants.PLAYBUTTON);
        } else if (v.getId() == R.id.food_category) {
            category = "Food";
            this.c.setCategoryChosen(category);
            this.startActivity(new Intent(this, Quiz.class));
        }

    }

}
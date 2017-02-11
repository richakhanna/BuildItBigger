package com.richdroid.androidjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        TextView jokeTextView = (TextView) findViewById(R.id.tv_joke_text);

        if(getIntent().getExtras()!=null && getIntent().hasExtra(Constants.JOKE_ID)){
            String joke = getIntent().getStringExtra(Constants.JOKE_ID);
            jokeTextView.setText(joke);
        }

    }
}

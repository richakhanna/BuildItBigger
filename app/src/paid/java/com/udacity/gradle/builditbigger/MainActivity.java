package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.richdroid.androidjokes.Constants;
import com.richdroid.androidjokes.DisplayJokeActivity;
import com.udacity.gradle.builditbigger.network.FetchJokeAsyncTask;
import com.udacity.gradle.builditbigger.utils.NetworkUtils;
import com.udacity.gradle.builditbigger.utils.ProgressBarUtil;


public class MainActivity extends ActionBarActivity implements FetchJokeAsyncTask.ApiResponse {

    private ProgressBarUtil mProgressBar;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJokeButton = (Button) findViewById(R.id.btn_joke);
        mProgressBar = new ProgressBarUtil(this);
    }

    public void tellJoke(View view) {
        requestJokeFromServer();
    }

    public void requestJokeFromServer() {
        if (NetworkUtils.isOnline(this)) {
            Log.v(TAG, "Calling : fetchJoke Api to fetch the joke from server for paid version");
            mProgressBar.show();
            mJokeButton.setVisibility(View.GONE);
            new FetchJokeAsyncTask(this, this).execute();
        } else {
            Toast.makeText(this, getString(R.string.internet_con_failed), Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    public void onResponseReceived(String result) {
        mProgressBar.hide();
        mJokeButton.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, DisplayJokeActivity.class);
        intent.putExtra(Constants.JOKE_ID, result);
        startActivity(intent);
    }
}

package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.richdroid.androidjokes.Constants;
import com.richdroid.androidjokes.DisplayJokeActivity;
import com.udacity.gradle.builditbigger.network.FetchJokeAsyncTask;
import com.udacity.gradle.builditbigger.utils.NetworkUtils;
import com.udacity.gradle.builditbigger.utils.ProgressBarUtil;


public class MainActivity extends ActionBarActivity implements FetchJokeAsyncTask.ApiResponse {

    private static final String TAG = MainActivity.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private ProgressBarUtil mProgressBar;
    private Button mJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJokeButton = (Button) findViewById(R.id.btn_joke);
        mProgressBar = new ProgressBarUtil(this);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                requestJokeFromServer();
            }
        });

        requestNewInterstitial();
    }

    public void tellJoke(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            requestJokeFromServer();
        }
    }

    public void requestJokeFromServer() {
        if (NetworkUtils.isOnline(this)) {
            Log.v(TAG, "Calling : fetchJoke Api to fetch the joke from server for free version");
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

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("BD5B43786AA8887B2FEA2B934560ED2A")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}

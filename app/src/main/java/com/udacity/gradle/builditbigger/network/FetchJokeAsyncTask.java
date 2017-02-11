package com.udacity.gradle.builditbigger.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.richroid.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;

/**
 * Created by richa.khanna on 07/02/17.
 */

public class FetchJokeAsyncTask extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = FetchJokeAsyncTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private ApiResponse apiResponse;
    private Context context;

    public FetchJokeAsyncTask(ApiResponse apiResponse,Context context){
        this.apiResponse = apiResponse;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = context.getResources().getString(R.string.joke_api_endpoint);
        Log.d(LOG_TAG, "url : " + url);

        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(url);
            myApiService = builder.build();
        }

        try {
            return myApiService.fetchJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        apiResponse.onResponseReceived(result);
    }

    public interface ApiResponse{
        void onResponseReceived(String result);
    }
}
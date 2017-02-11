package com.udacity.gradle.builditbigger;

import android.os.ConditionVariable;
import android.test.AndroidTestCase;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.network.FetchJokeAsyncTask;

/**
 * Created by richa.khanna on 09/02/17.
 */

public class JokeAsyncTaskAndroidTest extends AndroidTestCase implements FetchJokeAsyncTask.ApiResponse {

    private FetchJokeAsyncTask fetchJokeAsyncTask;
    private ConditionVariable waiter;

    @Override protected void setUp() throws Exception {
        super.setUp();
        fetchJokeAsyncTask = new FetchJokeAsyncTask(JokeAsyncTaskAndroidTest.this, getContext());
        waiter = new ConditionVariable();
    }

    public void testJokeFetchedIsNotEmpty() {
        fetchJokeAsyncTask.execute();
        waiter.block();
    }

    @Override
    public void onResponseReceived(String result) {
        assertFalse(TextUtils.isEmpty(result));
        waiter.open();
    }
}

package com.test.gittracker;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.readStream;

class AsyncFollowedUser extends AsyncTask<String, Void, JSONObject> {
    private URL url;
    private JSONObject result;
    private final WeakReference<FollowedUserAdapter> followedUserAdapter;

    public AsyncFollowedUser(WeakReference<FollowedUserAdapter> followedUserAdapter) {
        this.followedUserAdapter = followedUserAdapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);

                result = new JSONObject(s);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        FollowedUserAdapter adapter = followedUserAdapter.get();
        adapter.add(jsonObject);
        adapter.notifyDataSetChanged();
    }
}

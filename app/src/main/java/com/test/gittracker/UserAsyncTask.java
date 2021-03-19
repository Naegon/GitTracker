package com.test.gittracker;

import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.readStream;

class UserAsyncTask extends AsyncTask<String, Void, JSONArray> {
    private URL url;
    private JSONArray result;
    private final WeakReference<UserAdapter> userAdapter;
    private String login;
    private String token;

    public UserAsyncTask(WeakReference<UserAdapter> userAdapter, String login, String token) {
        this.userAdapter = userAdapter;
        this.login = login;
        this.token = token;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        try {
            url = new URL("https://api.github.com/user/followers?accept=application/vnd.github.v3+json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic " + Base64.encodeToString((login + ":" + token).getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", basicAuth);

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);

                result = new JSONArray(s);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        UserAdapter adapter = userAdapter.get();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                adapter.add(new User(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
}

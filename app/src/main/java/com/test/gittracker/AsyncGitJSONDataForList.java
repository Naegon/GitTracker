package com.test.gittracker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

class AsyncGitJSONDataForList extends AsyncTask<String, Void, JSONArray> {
    private URL url;
    private JSONArray result;
    private final WeakReference<UserAdapter> userAdapter;

    public AsyncGitJSONDataForList(WeakReference<UserAdapter> userAdapter) {
        this.userAdapter = userAdapter;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i("JFL", s);

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
        try {
            UserAdapter adapter = userAdapter.get();
//            Log.i("JFL", result.getJSONArray("items").getJSONObject(0).getString("link"));
//            JSONArray items = result.getJSONArray("items");
            for (int i = 0; i < result.length(); i++) {
                adapter.add(result.getJSONObject(i));
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString().replaceFirst("jsonFlickrFeed\\(", "");
        } catch (IOException e) {
            return "";
        }
    }
}

package com.test.gittracker;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

class AsyncGitJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    private URL url;
    private JSONObject result;
    private final WeakReference<UserAdapter> userAdapter;
    private final WeakReference<TextView> resultCount;

    public AsyncGitJSONDataForList(WeakReference<UserAdapter> userAdapter, WeakReference<TextView> resultCount) {
        this.userAdapter = userAdapter;
        this.resultCount = resultCount;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i("JFL", s);

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
        try {
            UserAdapter adapter = userAdapter.get();
            int total_count = jsonObject.getInt("total_count");

            JSONArray temp = jsonObject.getJSONArray("items");
            for (int i = 0; i < temp.length(); i++) {
                adapter.add(temp.getJSONObject(i));
            }
            resultCount.get().setText("Showing " + temp.length() + " of " + total_count + " results");
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

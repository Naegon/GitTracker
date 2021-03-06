package com.test.gittracker;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.readStream;

class AsyncGitJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    private URL url;
    private JSONObject result;
    private final WeakReference<UserAdapter> userAdapter;
    private final WeakReference<TextView> resultCount;
    private final AppCompatActivity activity;

    public AsyncGitJSONDataForList(WeakReference<UserAdapter> userAdapter, WeakReference<TextView> resultCount, AppCompatActivity activity) {
        this.userAdapter = userAdapter;
        this.resultCount = resultCount;
        this.activity = activity;
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
                adapter.add(new User(temp.getJSONObject(i)));
            }
            resultCount.get().setText(activity.getString(R.string.show_result, temp.length(), total_count));
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

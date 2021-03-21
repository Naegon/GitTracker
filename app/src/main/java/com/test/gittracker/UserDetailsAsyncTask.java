package com.test.gittracker;

import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.getActivity;
import static com.test.gittracker.Utils.readStream;

class UserDetailsAsyncTask extends AsyncTask<String, Void, JSONObject> {
    private JSONObject result;
    private final WeakReference<User> user;
    private View convertView;
    private final String login;
    private final String token;

    public UserDetailsAsyncTask(WeakReference<User> user, View convertView, String login, String token) {
        this.convertView = convertView;
        this.user = user;
        this.login = login;
        this.token = token;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic " + Base64.encodeToString((login + ":" + token).getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", basicAuth);

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
    protected void onPostExecute(JSONObject data) {
        super.onPostExecute(data);
        User target = user.get();

        try {
            target.setHireable(data.getString("hireable").equals("true"));
            target.setType(data.getString("type"));
            target.setCompany(data.getString("company"));
            target.setEmail(data.getString("email"));
            target.setFollowers(data.getInt("followers"));
            target.setFollowing(data.getInt("following"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new UserDialog().showDialog(getActivity(convertView), target);
    }
}

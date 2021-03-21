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

import static com.test.gittracker.Utils.readStream;

class RepositoryDetailsAsyncTask extends AsyncTask<String, Void, JSONObject> {
    private JSONObject result;
    private final WeakReference<Repository> repositoryRef;
    private View convertView;
    private final String login;
    private final String token;

    public RepositoryDetailsAsyncTask(WeakReference<Repository> repositoryRef, View convertView, String login, String token) {
        this.convertView = convertView;
        this.repositoryRef = repositoryRef;
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

//    @Override
//    protected void onPostExecute(JSONObject data) {
//        super.onPostExecute(data);
//        Repository repository = repositoryRef.get();
//
//        try {
//            repository.setHireable(data.getString("hireable").equals("true"));
//            repository.setType(data.getString("type"));
//            repository.setCompany(data.getString("company"));
//            repository.setEmail(data.getString("email"));
//            repository.setFollowers(data.getInt("followers"));
//            repository.setFollowing(data.getInt("following"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        repository.setDetailed(true);
//        new RepositoryDialog().showDialog(getActivity(convertView), repository);
//    }
}

package com.test.gittracker;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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
    private URL url;
    private JSONObject result;
    private WeakReference<User> user;
    private View convertView;
    private String login;
    private String token;

    public UserDetailsAsyncTask(WeakReference<User> user, View convertView, String login, String token) {
        this.convertView = convertView;
        this.user = user;
        this.login = login;
        this.token = token;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
//            url = new URL("https://api.github.com/users/" +  target + "?accept=application/vnd.github.v3+json");
            url = new URL(strings[0]);
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
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        User target = user.get();
//        target = new User(jsonObject);
        try {
            target.setFollowers(Integer.parseInt(jsonObject.getString("followers")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ViewDialog().showDialog(getActivity(convertView), target);
    }

    public static class ViewDialog {
        public void showDialog(Activity activity, User user){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);

            TextView textViewUsername = dialog.findViewById(R.id.text_view_username);
            try {
                textViewUsername.setText(String.valueOf(user.getFollowers()));
            } catch (Error e) {
                e.printStackTrace();
                dialog.dismiss();
                return;
            }

            Button btnFollow = dialog.findViewById(R.id.btn_follow);
            btnFollow.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        }
    }
}

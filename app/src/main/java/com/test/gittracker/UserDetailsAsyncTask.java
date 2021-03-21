package com.test.gittracker;

import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.test.gittracker.Utils.readStream;

class UserDetailsAsyncTask extends AsyncTask<String, Void, JSONObject> {
    private JSONObject result;
    private final WeakReference<User> user;
    private View convertView;
    private final String login;
    private final String token;
    private final boolean showDialog;

    public UserDetailsAsyncTask(WeakReference<User> user, View convertView, String login, String token, Boolean showDialog) {
        this.convertView = convertView;
        this.user = user;
        this.login = login;
        this.token = token;
        this.showDialog = showDialog;
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


            ShapeableImageView avatar = convertView.findViewById(R.id.avatar);
            TextView textViewHireable = convertView.findViewById(R.id.textViewHireable);
            TextView textViewUsername = convertView.findViewById(R.id.textViewUsername);
            TextView textViewType = convertView.findViewById(R.id.textViewType);
            TextView textViewCompany = convertView.findViewById(R.id.textViewCompany);
            TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
            TextView textViewFollowers = convertView.findViewById(R.id.textViewFollowers);
            TextView textViewFollowing = convertView.findViewById(R.id.textViewFollowing);
            TextView btnUnfollow = convertView.findViewById(R.id.btnUnfollow);

            try {
                avatar.setImageBitmap(target.getAvatar());
                textViewUsername.setText(target.getLogin());
                textViewType.setText(convertView.getContext().getString(R.string.dialog_type, target.getType()));
                textViewFollowers.setText(convertView.getContext().getString(R.string.dialog_followers, target.getFollowers()));
                textViewFollowing.setText(convertView.getContext().getString(R.string.dialog_following, target.getFollowing()));

                if (target.isHireable()) {
                    textViewHireable.setVisibility(View.VISIBLE);
                }

                if (!target.getCompany().equals("null")) {
                    textViewCompany.setText(convertView.getContext().getString(R.string.dialog_company, target.getCompany()));
                    textViewCompany.setVisibility(View.VISIBLE);
                }

                if (!target.getEmail().equals("null")) {
                    textViewEmail.setText(convertView.getContext().getString(R.string.dialog_email, target.getEmail()));
                    textViewEmail.setVisibility(View.VISIBLE);
                }
            } catch (Error e) {
                e.printStackTrace();
                return;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        target.setDetailed(true);
//        if (showDialog) new UserDialog().showDialog(getActivity(convertView), target);
    }
}

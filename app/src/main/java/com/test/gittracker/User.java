package com.test.gittracker;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

class User {
    private boolean followed = false;

    private final TextView textViewUsername;
    private final ShapeableImageView avatar;
    private final TextView learnMore;
    private final MaterialButton btnFollow;
    private final View convertView;
    private String url = "";


    public User(View convertView, JSONObject data, ViewGroup parent) {
        this.convertView = convertView;
        textViewUsername = convertView.findViewById(R.id.text_view_username);
        avatar = convertView.findViewById(R.id.avatar);
        learnMore = convertView.findViewById(R.id.text_view_learn_more);
        btnFollow = convertView.findViewById(R.id.btn_follow);

        Response.Listener<Bitmap> rep_listener = avatar::setImageBitmap;

        try {
            url = data.getString("url");

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(convertView.getContext());
            Set<String> followedUser = sharedPreferences.getStringSet("followed_user", null);
            if (followedUser != null && followedUser.contains(url)) {
                Log.i("Followed", "Contains");
                btnFollow.setText(R.string.unfollow);
                followed = true;
            }

            btnFollow.setOnClickListener(follow);

            ImageRequest imageRequest = new ImageRequest(data.getString("avatar_url"), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
            MySingleton.getInstance(parent.getContext()).addToRequestQueue(imageRequest);

            textViewUsername.setText(data.getString("login"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        convertView.getContext();
    }

    private final View.OnClickListener follow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(convertView.getContext());
            Set<String> followedUser = sharedPreferences.getStringSet("followed_user", null);

            if (!followed) {
                Log.i("Followed", "Not followed");
                if (followedUser == null) followedUser = new HashSet<>();
                followedUser.add(url);
            }
            else {
                Log.i("Followed", "Followed");
                followedUser.remove(url);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("followed_user", followedUser);
            editor.apply();

            Log.i("Followed", "Switching state");
            followed = !followed;
            Log.i("Followed", "Setting text");
            btnFollow.setText(followed?R.string.unfollow:R.string.follow);
        }
    };
}

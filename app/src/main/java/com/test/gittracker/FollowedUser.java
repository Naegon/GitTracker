package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

class FollowedUser {
    private final boolean FOLLOW = true;
    private final boolean UNFOLLOW = false;
    private boolean state = FOLLOW;

    private final TextView textViewUsername;
    private final TextView textViewCompany;
    private final TextView textViewRepositories;
    private final TextView textViewFollowers;
    private final ShapeableImageView avatar;
    private final ShapeableImageView learnMore;
    private final MaterialButton btnFollow;

    public FollowedUser(View convertView, JSONObject data, ViewGroup parent) {
        textViewUsername = convertView.findViewById(R.id.text_view_username);
        textViewCompany = convertView.findViewById(R.id.text_view_company);
        textViewRepositories = convertView.findViewById(R.id.text_view_repositories);
        textViewFollowers = convertView.findViewById(R.id.text_view_followers);
        avatar = convertView.findViewById(R.id.avatar);
        learnMore = convertView.findViewById(R.id.img_learn_more);
        btnFollow = convertView.findViewById(R.id.btn_follow);

        btnFollow.setOnClickListener(follow);

        Response.Listener<Bitmap> rep_listener = avatar::setImageBitmap;

        try {
            ImageRequest imageRequest = new ImageRequest(data.getString("avatar_url"), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
            MySingleton.getInstance(parent.getContext()).addToRequestQueue(imageRequest);

            textViewUsername.setText(data.getString("login"));
            textViewCompany.setText(data.getString("type"));
            textViewRepositories.setText(data.getString("login"));
            textViewFollowers.setText(data.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final View.OnClickListener follow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            state = !state;
            btnFollow.setText(state?R.string.follow:R.string.unfollow);
        }
    };
}

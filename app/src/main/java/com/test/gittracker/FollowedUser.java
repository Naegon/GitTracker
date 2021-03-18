package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

class FollowedUser {

    public FollowedUser(View convertView, JSONObject data, ViewGroup parent) {
        TextView textViewUsername = convertView.findViewById(R.id.text_view_username);
        TextView textViewCompany = convertView.findViewById(R.id.text_view_company);
        TextView textViewRepositories = convertView.findViewById(R.id.text_view_repositories);
        TextView textViewFollowers = convertView.findViewById(R.id.text_view_followers);
        ShapeableImageView avatar = convertView.findViewById(R.id.avatar);

        Response.Listener<Bitmap> rep_listener = avatar::setImageBitmap;

        try {
            ImageRequest imageRequest = new ImageRequest(data.getString("avatar_url"), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
            MySingleton.getInstance(parent.getContext()).addToRequestQueue(imageRequest);

            textViewUsername.setText(data.getString("login"));
            textViewCompany.setText(data.getString("type"));
            if (Integer.parseInt(data.getString("public_repos")) <= 0) {
                textViewRepositories.setText(R.string.no_repo);
            }
            else {
                textViewRepositories.setText(parent.getContext().getResources().getString(R.string.repo_count, data.getString("public_repos")));
            }
            if (Integer.parseInt(data.getString("followers")) <= 0) {
                textViewFollowers.setText(R.string.no_followers);
            }
            else {
                textViewFollowers.setText(parent.getContext().getResources().getString(R.string.follower_count, data.getString("followers")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

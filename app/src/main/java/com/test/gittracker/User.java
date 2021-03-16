package com.test.gittracker;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

class User {
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

    public User(View convertView) {
        textViewUsername = convertView.findViewById(R.id.text_view_username);
        textViewCompany = convertView.findViewById(R.id.text_view_company);
        textViewRepositories = convertView.findViewById(R.id.text_view_repositories);
        textViewFollowers = convertView.findViewById(R.id.text_view_followers);
        avatar = convertView.findViewById(R.id.avatar);
        learnMore = convertView.findViewById(R.id.img_learn_more);
        btnFollow = convertView.findViewById(R.id.btn_follow);

        btnFollow.setOnClickListener(follow);
    }

    private final View.OnClickListener follow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            state = !state;
            btnFollow.setText(state?R.string.follow:R.string.unfollow);
        }
    };

    public void setWithJSON(JSONObject data) {
        try {
            textViewUsername.setText(data.getString("login"));
            textViewCompany.setText(data.getString("type"));
            textViewRepositories.setText(data.getString("login"));
            textViewFollowers.setText(data.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TextView getTextViewUsername() {
        return textViewUsername;
    }

    public TextView getTextViewCompany() {
        return textViewCompany;
    }

    public TextView getTextViewRepositories() {
        return textViewRepositories;
    }

    public TextView getTextViewFollowers() {
        return textViewFollowers;
    }

    public ShapeableImageView getAvatar() {
        return avatar;
    }

    public ShapeableImageView getLearnMore() {
        return learnMore;
    }

    public MaterialButton getBtnFollow() {
        return btnFollow;
    }
}

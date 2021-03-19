package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.imageview.ShapeableImageView;

class UserComponent {
    private final TextView textViewUsername;
    private final ShapeableImageView avatar;
    private final TextView learnMore;
    private final View convertView;
    private String url = "";


    public UserComponent(View convertView, User user, ViewGroup parent) {
        this.convertView = convertView;
        textViewUsername = convertView.findViewById(R.id.text_view_username);
        avatar = convertView.findViewById(R.id.avatar);
        learnMore = convertView.findViewById(R.id.text_view_learn_more);

        Response.Listener<Bitmap> rep_listener = avatar::setImageBitmap;

        ImageRequest imageRequest = new ImageRequest(user.getAvatar_url(), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
        MySingleton.getInstance(parent.getContext()).addToRequestQueue(imageRequest);

        textViewUsername.setText(user.getLogin());

        convertView.getContext();
    }
}

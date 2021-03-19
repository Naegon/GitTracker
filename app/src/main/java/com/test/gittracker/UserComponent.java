package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.imageview.ShapeableImageView;

class UserComponent {
    private final TextView textViewUsername;
    private final ShapeableImageView avatar;
    private final TextView learnMore;
    private final View convertView;
    private final CardView cardView;
    private final User user;
    private String url = "";


    public UserComponent(View convertView, User user) {
        this.convertView = convertView;
        this.user = user;
        textViewUsername = convertView.findViewById(R.id.text_view_username);
        avatar = convertView.findViewById(R.id.avatar);
        learnMore = convertView.findViewById(R.id.text_view_learn_more);
        cardView = convertView.findViewById(R.id.card);

        Response.Listener<Bitmap> rep_listener = avatar::setImageBitmap;

        ImageRequest imageRequest = new ImageRequest(user.getAvatar_url(), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
        MySingleton.getInstance(convertView.getContext()).addToRequestQueue(imageRequest);

        textViewUsername.setText(user.getLogin());
        cardView.setOnClickListener(showUserData);
    }

    private final View.OnClickListener showUserData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(convertView.getContext(), user.getLogin(), Toast.LENGTH_LONG).show();
        }
    };
}

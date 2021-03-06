package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.imageview.ShapeableImageView;

import java.lang.ref.WeakReference;

import static com.test.gittracker.Utils.getActivity;

class UserComponent {
    private final TextView textViewUsername;
    private final ShapeableImageView avatar;
    private final TextView learnMore;
    private final View convertView;
    private final CardView cardView;
    private final WeakReference<User> userRef;
    private String url = "";

    public UserComponent(View convertView, WeakReference<User> userRef) {
        this.convertView = convertView;
        this.userRef = userRef;

        User user = userRef.get();
        textViewUsername = convertView.findViewById(R.id.text_view_username);
        avatar = convertView.findViewById(R.id.avatar);
        learnMore = convertView.findViewById(R.id.text_view_learn_more);
        cardView = convertView.findViewById(R.id.card);

        Response.Listener<Bitmap> rep_listener = bm -> {
            user.setAvatar(bm);
            avatar.setImageBitmap(bm);
        };

        ImageRequest imageRequest = new ImageRequest(user.getAvatar_url(), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
        MySingleton.getInstance(convertView.getContext()).addToRequestQueue(imageRequest);

        textViewUsername.setText(user.getLogin());
        cardView.setOnClickListener(showUserData);
    }

    private final View.OnClickListener showUserData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = userRef.get();
            new UserDialog().showDialog(getActivity(convertView), user);
        }
    };
}

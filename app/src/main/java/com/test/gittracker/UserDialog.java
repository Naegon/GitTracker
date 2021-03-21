package com.test.gittracker;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class UserDialog {
    public void showDialog(Activity activity, User user){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.user_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ShapeableImageView avatar = dialog.findViewById(R.id.avatar);
        TextView textViewHireable = dialog.findViewById(R.id.textViewHireable);
        TextView textViewUsername = dialog.findViewById(R.id.textViewUsername);
        TextView textViewType = dialog.findViewById(R.id.textViewType);
        TextView textViewCompany = dialog.findViewById(R.id.textViewCompany);
        TextView textViewEmail = dialog.findViewById(R.id.textViewEmail);
        TextView textViewFollowers = dialog.findViewById(R.id.textViewFollowers);
        TextView textViewFollowing = dialog.findViewById(R.id.textViewFollowing);
        TextView btnUnfollow = dialog.findViewById(R.id.btnUnfollow);

        try {
            avatar.setImageBitmap(user.getAvatar());
            textViewUsername.setText(user.getLogin());
            textViewType.setText(user.getType());
            textViewCompany.setText(user.getLogin());
            textViewEmail.setText(user.getLogin());
            textViewFollowers.setText(String.valueOf(user.getFollowers()));
            textViewFollowing.setText(String.valueOf(user.getFollowers()));
        } catch (Error e) {
            e.printStackTrace();
            dialog.dismiss();
            return;
        }


        btnUnfollow.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

        Window window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}

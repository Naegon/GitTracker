package com.test.gittracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class UserDialog {
    public void showDialog(Activity activity, User user){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.user_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(dialog.getContext());
        String login = sharedPreferences.getString("login", null);
        String token = sharedPreferences.getString("token", null);
        UserDetailsAsyncTask task = new UserDetailsAsyncTask(new WeakReference<>(user), dialog.getWindow().getDecorView(), login, token, true);
        task.execute("https://api.github.com/users/" + user.getLogin() + "?accept=application/vnd.github.v3+json");

        Button btnUnfollow = dialog.findViewById(R.id.btnUnfollow);
        btnUnfollow.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

        Window window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}

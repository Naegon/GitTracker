package com.test.gittracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.imageview.ShapeableImageView;

import java.lang.ref.WeakReference;

public class RepositoryDialog {
    public void showDialog(Activity activity, Repository repository){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.repository_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(dialog.getContext());
        String login = sharedPreferences.getString("login", null);
        String token = sharedPreferences.getString("token", null);
        UserDetailsAsyncTask task = new UserDetailsAsyncTask(new WeakReference<>(repository.getOwner()), dialog.getWindow().getDecorView(), login, token, false);
        task.execute("https://api.github.com/users/" +  repository.getOwner().getLogin() + "?accept=application/vnd.github.v3+json");

        Response.Listener<Bitmap> rep_listener = bm -> {
            repository.getOwner().setAvatar(bm);
            ShapeableImageView avatar = dialog.findViewById(R.id.avatar);
            avatar.setImageBitmap(bm);
        };

        ImageRequest imageRequest = new ImageRequest(repository.getOwner().getAvatar_url(), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
        MySingleton.getInstance(dialog.getContext()).addToRequestQueue(imageRequest);

        Button btnExit = dialog.findViewById(R.id.btnQuitRepo);
        btnExit.setOnClickListener(v -> dialog.dismiss());
        Button userQuit = dialog.findViewById(R.id.btnExit);
        userQuit.setVisibility(View.GONE);

        TextView textViewProjectName = dialog.findViewById(R.id.textViewProjectName);
        TextView textViewDescription = dialog.findViewById(R.id.textViewDescription);
        TextView textViewLanguage = dialog.findViewById(R.id.textViewLanguage);
        TextView textViewCreated = dialog.findViewById(R.id.textViewCreated);
        TextView textViewUpdated = dialog.findViewById(R.id.textViewUpdated);
        TextView textViewPushed = dialog.findViewById(R.id.textViewPushed);
        TextView textViewStars = dialog.findViewById(R.id.textViewStars);

        try {
            textViewProjectName.setText(repository.getName());
            if (!repository.getDescription().equals("null")) textViewDescription.setText(repository.getDescription());
            textViewCreated.setText(dialog.getContext().getString(R.string.dialog_created_at, repository.getCreatedAt()));
            textViewUpdated.setText(dialog.getContext().getString(R.string.dialog_updated_at, repository.getUpdatedAt()));
            textViewPushed.setText(dialog.getContext().getString(R.string.dialog_pushed_at, repository.getPushedAt()));
            textViewStars.setText(dialog.getContext().getString(R.string.dialog_stars, repository.getStars()));

            if (!repository.getLanguage().equals("null")) {
                textViewLanguage.setText(dialog.getContext().getString(R.string.dialog_language, repository.getLanguage()));
                textViewLanguage.setVisibility(View.VISIBLE);
            }

        } catch (Error e) {
            e.printStackTrace();
            dialog.dismiss();
            return;
        }

        dialog.show();

        Window window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}

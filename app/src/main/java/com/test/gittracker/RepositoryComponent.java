package com.test.gittracker;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

class RepositoryComponent {
    private TextView textViewAppName;
    private TextView textViewOwner;
    private TextView textViewDesc;
    private TextView textViewLanguage;

    public RepositoryComponent(View convertView, Repository repository) {
        textViewAppName = convertView.findViewById(R.id.text_view_app_name);
        textViewOwner = convertView.findViewById(R.id.text_view_owner);
        textViewDesc = convertView.findViewById(R.id.text_view_description);
        textViewLanguage = convertView.findViewById(R.id.text_view_language);

        textViewAppName.setText(repository.getName());
        textViewOwner.setText(repository.getOwnerLogin());

        String language = repository.getLanguage();
        if (language.equals("null")) textViewLanguage.setVisibility(View.GONE);
        else textViewLanguage.setText(language);

        String description = repository.getDescription();
        if (!description.equals("null")) textViewDesc.setText(description);
        else {
            textViewDesc.setVisibility(View.GONE);
        }

        if (repository.isPrivate()) {
            ShapeableImageView imgPublic = convertView.findViewById(R.id.imgPublic);
            imgPublic.setImageResource(R.drawable.ic_padlock);
        }
    }
}

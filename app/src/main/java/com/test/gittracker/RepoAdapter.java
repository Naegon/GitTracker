package com.test.gittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Vector;

class RepoAdapter extends BaseAdapter {
    private final Vector<Repository> repositories;

    public RepoAdapter() {
        repositories = new Vector<>();
    }

    public void add(Repository repository) {
        this.repositories.add(repository);
    }

    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public Object getItem(int position) {
        return repositories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.repository, parent, false);
        }

        TextView textViewAppName = convertView.findViewById(R.id.text_view_app_name);
        TextView textViewOwner = convertView.findViewById(R.id.text_view_owner);
        TextView textViewDesc = convertView.findViewById(R.id.text_view_description);
        TextView textViewLanguage = convertView.findViewById(R.id.text_view_language);

        Repository repo = repositories.get(position);
        textViewAppName.setText(repo.getName());
        textViewOwner.setText(repo.getOwnerLogin());

        String language = repo.getLanguage();
        if (language.equals("null")) textViewLanguage.setVisibility(View.GONE);
        else textViewLanguage.setText(language);

        String description = repo.getDescription();
        if (!description.equals("null")) textViewDesc.setText(description);
        else {
            textViewDesc.setVisibility(View.GONE);
        }

        if (repo.isPrivate()) {
            ShapeableImageView imgPublic = convertView.findViewById(R.id.imgPublic);
            imgPublic.setImageResource(R.drawable.ic_padlock);
        }

        return convertView;
    }
}

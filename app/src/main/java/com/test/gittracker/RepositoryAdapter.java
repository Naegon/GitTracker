package com.test.gittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Vector;

class RepositoryAdapter extends BaseAdapter {
    private final Vector<Repository> repositories;

    public RepositoryAdapter() {
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

        new RepositoryComponent(convertView, repositories.get(position));
        return convertView;
    }
}

package com.test.gittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Vector;

class UserAdapter extends BaseAdapter {
    private final Vector<User> users;

    UserAdapter() {
        this.users = new Vector<>();
    }

    public void add(User user) {
        this.users.add(user);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.user, parent, false);
        }
        new UserComponent(convertView, users.get(position));
        return convertView;
    }
}

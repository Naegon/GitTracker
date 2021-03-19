package com.test.gittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONObject;

import java.util.Vector;

class UserAdapter extends BaseAdapter {
    private final Vector<JSONObject> data;

    UserAdapter() {
        this.data = new Vector<>();
    }

    public void add(Object data) {
        this.data.add((JSONObject)data);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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

        new UserComponent(convertView, data.get(position), parent);
        return convertView;
    }
}

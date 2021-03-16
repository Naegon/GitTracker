package com.test.gittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

class RepoAdapter extends BaseAdapter {
    private final Vector<JSONObject> data;

    public RepoAdapter() {
        data = new Vector<>();
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
            convertView = inflater.inflate(R.layout.repository, parent, false);
        }

        TextView textViewAppName = convertView.findViewById(R.id.text_view_app_name);
        TextView textViewOwner = convertView.findViewById(R.id.text_view_owner);
        TextView textViewDesc = convertView.findViewById(R.id.text_view_description);
        TextView textViewLanguage = convertView.findViewById(R.id.text_view_language);

        try {
            JSONObject repo = data.get(position);
            textViewAppName.setText(repo.getString("name"));
            textViewOwner.setText(repo.getJSONObject("owner").getString("login"));

            String language = repo.getString("language");
            if (language.equals("null")) textViewLanguage.setVisibility(View.GONE);
            else textViewLanguage.setText(language);

            String description = repo.getString("description");
            if (!description.equals("null")) textViewDesc.setText(description);
            else {
                textViewDesc.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

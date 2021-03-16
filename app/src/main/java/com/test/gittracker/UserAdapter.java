package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONException;
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

        User user = new User(convertView);

        Response.Listener<Bitmap> rep_listener = user.getAvatar()::setImageBitmap;

        ImageRequest imageRequest = null;
        try {
            imageRequest = new ImageRequest(data.get(position).getString("avatar_url"), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MySingleton.getInstance(parent.getContext()).addToRequestQueue(imageRequest);
        JSONObject userData = data.get(position);
        user.setWithJSON(userData);

        return convertView;
    }
}

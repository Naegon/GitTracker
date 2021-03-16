package com.test.gittracker;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

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

        TextView textViewUsername = convertView.findViewById(R.id.text_view_username);
        TextView textViewCompany = convertView.findViewById(R.id.text_view_company);
        TextView textViewRepositories = convertView.findViewById(R.id.text_view_repositories);
        TextView textViewFollowers = convertView.findViewById(R.id.text_view_followers);
        ShapeableImageView avatar = convertView.findViewById(R.id.avatar);
        ShapeableImageView learnMore = convertView.findViewById(R.id.img_learn_more);
        MaterialButton btnFollow = convertView.findViewById(R.id.btn_follow);

        Response.Listener<Bitmap> rep_listener = avatar::setImageBitmap;

        try {
            ImageRequest imageRequest = null;
            imageRequest = new ImageRequest(data.get(position).getString("avatar_url"), rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP, null, null);
            MySingleton.getInstance(parent.getContext()).addToRequestQueue(imageRequest);

            JSONObject user = data.get(position);
            textViewUsername.setText(user.getString("login"));
            textViewCompany.setText(user.getString("type"));
            textViewRepositories.setText(user.getString("login"));
            textViewFollowers.setText(user.getString("type"));
//            btnFollow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    state = !state;
//                    btnFollow.setText(R.string.unfollow);
//                }
//            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

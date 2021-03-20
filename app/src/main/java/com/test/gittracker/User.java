package com.test.gittracker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

class User implements Parcelable {
    private String login;
    private String avatar_url;
    private Bitmap avatar;
    private String type;
    private int public_repos;
    private int total_private_repos;
    private int followers;

    public User(JSONObject data) {
        try {
            this.login = data.getString("login");
            this.avatar_url = data.getString("avatar_url");
            this.avatar = null;
            this.type = data.getString("type");
            this.public_repos = Integer.parseInt(data.getString("public_repos"));
            this.total_private_repos = Integer.parseInt(data.getString("total_private_repos"));
            this.followers = Integer.parseInt(data.getString("followers"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    protected User(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
        avatar = in.readParcelable(Bitmap.class.getClassLoader());
        type = in.readString();
        public_repos = in.readInt();
        total_private_repos = in.readInt();
        followers = in.readInt();
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getType() {
        return type;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public int getTotal_private_repos() {
        return total_private_repos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatar_url);
        dest.writeParcelable(avatar, flags);
        dest.writeString(type);
        dest.writeInt(public_repos);
        dest.writeInt(total_private_repos);
        dest.writeInt(followers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

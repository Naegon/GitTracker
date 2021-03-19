package com.test.gittracker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

class UserClass implements Parcelable {
    private String login;
    private String avatar_url;
    private Bitmap avatar;
    private String type;
    private int public_repos;
    private int total_private_repos;
    private int followers;

    public UserClass(JSONObject data) {
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

    protected UserClass(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
        avatar = in.readParcelable(Bitmap.class.getClassLoader());
        type = in.readString();
        public_repos = in.readInt();
        total_private_repos = in.readInt();
        followers = in.readInt();
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

    public static final Creator<UserClass> CREATOR = new Creator<UserClass>() {
        @Override
        public UserClass createFromParcel(Parcel in) {
            return new UserClass(in);
        }

        @Override
        public UserClass[] newArray(int size) {
            return new UserClass[size];
        }
    };
}

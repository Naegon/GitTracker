package com.test.gittracker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

class User implements Parcelable {
    private boolean isDetailed;
    private String login;
    private String avatar_url;
    private Bitmap avatar;
    private String type;
    private String company;
    private String email;
    private Boolean hireable;
    private int followers;
    private int following;

    public User(JSONObject data) {
        try {
            this.isDetailed = false;
            this.login = data.getString("login");
            this.avatar_url = data.getString("avatar_url");
            this.avatar = null;
            this.type = data.getString("type");
            this.company = data.getString("company");
            this.email = data.getString("email");
            this.hireable = data.getBoolean("hireable");
            this.followers = data.getInt("followers");
            this.following = data.getInt("following");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isDetailed() {
        return isDetailed;
    }

    public void setDetailed(boolean detailed) {
        isDetailed = detailed;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isHireable() {
        return hireable;
    }

    public void setHireable(Boolean hireable) {
        this.hireable = hireable;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    protected User(Parcel in) {
        isDetailed = (in.readInt() == 1);
        login = in.readString();
        avatar_url = in.readString();
        avatar = in.readParcelable(Bitmap.class.getClassLoader());
        type = in.readString();
        company = in.readString();
        email = in.readString();
        hireable = (in.readInt() == 1);
        followers = in.readInt();
        following = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isDetailed?1:0);
        dest.writeString(login);
        dest.writeString(avatar_url);
        dest.writeParcelable(avatar, flags);
        dest.writeString(type);
        dest.writeString(company);
        dest.writeString(email);
        dest.writeInt(hireable?1:0);
        dest.writeInt(followers);
        dest.writeInt(following);
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

package com.test.gittracker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

class UserClass implements Parcelable {
    private String login;
    private String avatar_url;
    private Bitmap avatar;
    private String type;
    private String company;
    private int public_repos;
    private int total_private_repos;
    private int followers;

    public UserClass(String login, String avatar_url, String type, String company, int public_repos, int total_private_repos, int followers) {
        this.login = login;
        this.avatar_url = avatar_url;
        this.avatar = null;
        this.type = type;
        this.company = company;
        this.public_repos = public_repos;
        this.total_private_repos = total_private_repos;
        this.followers = followers;
    }

    public String getLogin() {
        return login;
    }

    protected UserClass(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
        avatar = in.readParcelable(Bitmap.class.getClassLoader());
        type = in.readString();
        company = in.readString();
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
        dest.writeString(company);
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

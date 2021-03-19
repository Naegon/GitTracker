package com.test.gittracker;

import android.graphics.Bitmap;

class Repository {
    private String name;
    private String owner;
    private String avatar_url;
    private Bitmap avatar;
    private String description;
    private String language;

    public Repository(String name, String owner, String avatar_url, String description, String language) {
        this.name = name;
        this.owner = owner;
        this.avatar_url = avatar_url;
        this.avatar = null;
        this.description = description;
        this.language = language;
    }
}

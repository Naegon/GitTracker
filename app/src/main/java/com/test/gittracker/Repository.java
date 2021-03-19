package com.test.gittracker;

import org.json.JSONException;
import org.json.JSONObject;

class Repository {
    private String name;
    private User owner;
    private String description;
    private String language;
    private boolean isPrivate;

    public Repository(String name, User owner, String avatar_url, String description, String language, boolean isPrivate) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.language = language;
        this.isPrivate = isPrivate;
    }

    public Repository(JSONObject data) {
        try {
            this.name = data.getString("name");
            this.description = data.getString("description");
            this.language = data.getString("language");
            this.owner = new User(data.getJSONObject("owner"));
            this.isPrivate = data.getString("private").equals("true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerLogin() {
        return owner.getLogin();
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}

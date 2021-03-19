package com.test.gittracker;

import org.json.JSONException;
import org.json.JSONObject;

class Repository {
    private String name;
    private UserClass owner;
    private String description;
    private String language;

    public Repository(String name, UserClass owner, String avatar_url, String description, String language) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.language = language;
    }

    public Repository(JSONObject data) {
        try {
            this.name = data.getString("name");
            this.description = data.getString("description");
            this.language = data.getString("language");
            this.owner = new UserClass(data.getJSONObject("owner"));
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

    public UserClass getOwner() {
        return owner;
    }

    public String getOwnerLogin() {
        return owner.getLogin();
    }

    public void setOwner(UserClass owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

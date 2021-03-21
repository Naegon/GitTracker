package com.test.gittracker;

import org.json.JSONException;
import org.json.JSONObject;

class Repository {
    private String name;
    private User owner;
    private User organization;
    private String description;
    private String language;
    private boolean isPrivate;
    private String createdAt;
    private String UpdatedAt;
    private String pushedAt;
    private int stars;

    public Repository(JSONObject data) {
        try {
            this.name = data.getString("name");
            this.owner = new User(data.getJSONObject("owner"));
            this.description = data.getString("description");
            this.language = data.getString("language");
            this.isPrivate = data.getString("private").equals("true");
            this.createdAt = data.getString("created_at");
            this.UpdatedAt = data.getString("updated_at");
            this.pushedAt = data.getString("pushed_at");
            this.stars = data.getInt("stargazers_count");
            this.organization = new User(data.getJSONObject("organization"));
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOrganization() {
        return organization;
    }

    public void setOrganization(User organization) {
        this.organization = organization;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}

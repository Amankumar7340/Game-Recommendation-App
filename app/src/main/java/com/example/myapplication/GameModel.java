package com.example.myapplication;

import java.util.List;

public class GameModel {
    private String name;
    private String description;
    private int imageResource;
    private List<String> tags;
    private boolean isPlayStoreResult;

    // Constructor for local games
    public GameModel(String name, String description, int imageResource, List<String> tags) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.tags = tags;
        this.isPlayStoreResult = false;
    }

    // Constructor with Play Store flag
    public GameModel(String name, String description, int imageResource, List<String> tags, boolean isPlayStoreResult) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.tags = tags;
        this.isPlayStoreResult = isPlayStoreResult;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean isPlayStoreResult() {
        return isPlayStoreResult;
    }

    public Object getImage() {
        return imageResource;
    }
}
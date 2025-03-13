package com.example.myapplication;

import java.util.List;

public class GameModel {
    private String name;
    private String description;
    private int image;
    private List<String> tags; // Add tags for categorization

    // Updated constructor to include tags
    public GameModel(String name, String description, int image, List<String> tags) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    // Add getTags() method
    public List<String> getTags() {
        return tags;
    }
}
package com.example.myapplication;

import androidx.recyclerview.widget.DiffUtil;
import java.util.List;

public class GameDiffCallback extends DiffUtil.Callback {
    private final List<GameModel> oldList;
    private final List<GameModel> newList;

    public GameDiffCallback(List<GameModel> oldList, List<GameModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // Compare unique identifiers (e.g., game names)
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // Compare contents (e.g., game descriptions and images)
        GameModel oldGame = oldList.get(oldItemPosition);
        GameModel newGame = newList.get(newItemPosition);
        return oldGame.getName().equals(newGame.getName()) &&
                oldGame.getDescription().equals(newGame.getDescription()) &&
                oldGame.getImage() == newGame.getImage();
    }
}
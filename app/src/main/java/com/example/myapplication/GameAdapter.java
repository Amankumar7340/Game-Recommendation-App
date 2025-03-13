package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private final List<GameModel> gameList;
    private final List<GameModel> gameListFull;
    private final OnGameClickListener listener;

    public interface OnGameClickListener {
        void onGameClick(GameModel game);
    }

    public GameAdapter(List<GameModel> gameList, OnGameClickListener listener) {
        this.gameList = gameList;
        this.gameListFull = new ArrayList<>(gameList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameModel game = gameList.get(position);
        holder.gameName.setText(game.getName());
        holder.gameDescription.setText(game.getDescription());
        holder.gameImage.setImageResource(game.getImage());

        // Display tags
        StringBuilder tags = new StringBuilder();
        for (String tag : game.getTags()) {
            tags.append(tag).append(", ");
        }
        if (tags.length() > 0) {
            tags.setLength(tags.length() - 2); // Remove the last ", "
        }
        holder.gameTags.setText(tags.toString());

        // Handle item click
        holder.itemView.setOnClickListener(v -> listener.onGameClick(game));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void filter(String text) {
        gameList.clear();
        if (text.isEmpty()) {
            gameList.addAll(gameListFull);
        } else {
            text = text.toLowerCase();
            for (GameModel game : gameListFull) {
                if (game.getName().toLowerCase().contains(text) || game.getTags().toString().toLowerCase().contains(text)) {
                    gameList.add(game);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameName, gameDescription, gameTags;
        ImageView gameImage;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.game_name);
            gameDescription = itemView.findViewById(R.id.game_description);
            gameTags = itemView.findViewById(R.id.game_tags);
            gameImage = itemView.findViewById(R.id.game_image);
        }
    }
}
package com.example.myapplication;

import android.content.Context;
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
    private List<GameModel> gameList;
    private List<GameModel> gameListFull;
    private OnGameClickListener listener;
    private Context context;

    public interface OnGameClickListener {
        void onGameClick(GameModel game);
    }

    public GameAdapter(List<GameModel> gameList, Context context, OnGameClickListener listener) {
        this.gameList = gameList;
        this.gameListFull = new ArrayList<>(gameList);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameModel game = gameList.get(position);
        holder.bind(game);

        // Add a badge or indicator for Play Store games
        if (game.isPlayStoreResult()) {
            holder.playStoreIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.playStoreIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    // Filter method for search functionality
    public void filter(String text) {
        gameList.clear();
        if (text.isEmpty()) {
            gameList.addAll(gameListFull);
        } else {
            text = text.toLowerCase();
            for (GameModel game : gameListFull) {
                if (game.getName().toLowerCase().contains(text) ||
                        game.getDescription().toLowerCase().contains(text)) {
                    gameList.add(game);
                }
            }
        }
        notifyDataSetChanged();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        private ImageView gameImage;
        private TextView gameName;
        private TextView gameDescription;
        private TextView gameTags;
        private TextView playStoreIndicator;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.image_game);
            gameName = itemView.findViewById(R.id.text_game_name);
            gameDescription = itemView.findViewById(R.id.text_game_description);
            gameTags = itemView.findViewById(R.id.text_game_tags);
            playStoreIndicator = itemView.findViewById(R.id.text_play_store);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onGameClick(gameList.get(position));
                    }
                }
            });
        }

        public void bind(GameModel game) {
            gameName.setText(game.getName());
            gameDescription.setText(game.getDescription());
            gameImage.setImageResource(game.getImageResource());

            // Format tags as a comma-separated string
            StringBuilder tagsBuilder = new StringBuilder();
            for (int i = 0; i < game.getTags().size(); i++) {
                tagsBuilder.append(game.getTags().get(i));
                if (i < game.getTags().size() - 1) {
                    tagsBuilder.append(", ");
                }
            }
            gameTags.setText(tagsBuilder.toString());
        }
    }
}
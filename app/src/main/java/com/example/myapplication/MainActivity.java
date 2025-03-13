package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GameAdapter adapter;
    private List<GameModel> gameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set up the title "GameReX" with "X" in red
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        String title = "GameREX";
        SpannableString spannableString = new SpannableString(title);

        // Find the index of "X" in the title
        int indexOfX = title.indexOf("REX");

        // Apply red color to "X"
        if (indexOfX != -3) {
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.RED), // Red color
                    indexOfX, // Start index of "X"
                    indexOfX + 3, // End index of "X"
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Set the styled title to the TextView
        textViewTitle.setText(spannableString);

        // Initialize game list with tags
        gameList = new ArrayList<>();
        gameList.add(new GameModel("Flappy Bird Clone", "Navigate through obstacles", R.drawable.flappy_bird, Arrays.asList("Casual", "Action")));
        gameList.add(new GameModel("Tic-Tac-Toe", "Classic two-player game", R.drawable.tic_tac_toe, Arrays.asList("Puzzle", "Strategy")));
        gameList.add(new GameModel("2048", "Combine tiles to reach 2048", R.drawable.game_2048, Arrays.asList("Puzzle", "Numbers")));
        gameList.add(new GameModel("Endless Runner", "Run endlessly and avoid obstacles", R.drawable.endless_runner, Arrays.asList("Action", "Casual")));
        gameList.add(new GameModel("Memory Match", "Match pairs of cards", R.drawable.memory_match, Arrays.asList("Puzzle", "Brain Training")));
        gameList.add(new GameModel("Ninja Arashi", "Defeat your enemies", R.drawable.ninja_arashi, Arrays.asList("Action", "Adventure")));


        // Set up RecyclerView with GridLayoutManager
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        adapter = new GameAdapter(gameList, new GameAdapter.OnGameClickListener() {
            @Override
            public void onGameClick(GameModel game) {
                // Launch the corresponding game activity or show video preview
                switch (game.getName()) {
                    case "Flappy Bird Clone":
                        Videopopup2 videopopup2 = new Videopopup2(MainActivity.this);
                        videopopup2.show();
                        break;
                    case "Tic-Tac-Toe":
                        startActivity(new Intent(MainActivity.this, TicTacToeActivity.class));
                        break;
                    case "2048":
                        startActivity(new Intent(MainActivity.this, Game2048Activity.class));
                        break;
                    case "Endless Runner":
                        // Show video preview popup
                        VideoPopupDialog videoPopupDialog = new VideoPopupDialog(MainActivity.this);
                        videoPopupDialog.show();
                        break;
                    case "Memory Match":
                        String url = "https://g.co/kgs/fGU4nnQ";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        break;
                    case"Ninja Arashi":
                        String url1 = "https://play.google.com/store/apps/details?id=com.blackpanther.ninjaarashi2";
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                        startActivity(intent1);
                        break;

                }
            }
        });
        recyclerView.setAdapter(adapter);

        // Set up SearchView
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }
}
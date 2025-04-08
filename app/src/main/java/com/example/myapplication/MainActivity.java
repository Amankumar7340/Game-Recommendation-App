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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GameAdapter adapter;
    private List<GameModel> gameList;
    private List<GameModel> allGamesList; // To store the complete list of games

    // Category buttons
    private Button buttonAction;
    private Button buttonPuzzle;
    private Button buttonCasual;
    private Button buttonStrategy;
    private Button buttonAdventure;
    private Button buttonArcade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up profile image click listener to open bottom sheet menu
        ImageView profileImageView = findViewById(R.id.profileImageView);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserMenu();
            }
        });

        // Set up the title "GameReX" with "REX" in red
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        String title = "GameREX";
        SpannableString spannableString = new SpannableString(title);

        // Find the index of "REX" in the title
        int indexOfX = title.indexOf("REX");

        // Apply red color to "REX"
        if (indexOfX != -1) {
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.RED), // Red color
                    indexOfX, // Start index of "REX"
                    indexOfX + 3, // End index of "REX"
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

        // Create a backup of the full list
        allGamesList = new ArrayList<>(gameList);

        // Set up RecyclerView with GridLayoutManager
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

        // Pass the context to the GameAdapter constructor
        adapter = new GameAdapter(gameList, this, new GameAdapter.OnGameClickListener() {
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
                    case "Ninja Arashi":
                        String url1 = "https://play.google.com/store/apps/details?id=com.blackpanther.ninjaarashi2";
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                        startActivity(intent1);
                        break;
                    // For Play Store results
                    default:
                        if (game.isPlayStoreResult()) {
                            openPlayStore(game.getName());
                        }
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

        // Initialize category buttons
        initCategoryButtons();
    }

    private void showUserMenu() {
        BottomSheetDialogFragment bottomSheet = new UserMenuBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "UserMenuBottomSheet");
    }

    private void initCategoryButtons() {
        buttonAction = findViewById(R.id.buttonAction);
        buttonPuzzle = findViewById(R.id.buttonPuzzle);
        buttonCasual = findViewById(R.id.buttonCasual);
        buttonStrategy = findViewById(R.id.buttonStrategy);
        buttonAdventure = findViewById(R.id.buttonAdventure);
        buttonArcade = findViewById(R.id.buttonArcade);

        // Set up click listeners for category buttons
        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByCategory("Action");
            }
        });

        buttonPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByCategory("Puzzle");
            }
        });

        buttonCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByCategory("Casual");
            }
        });

        buttonStrategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByCategory("Strategy");
            }
        });

        buttonAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByCategory("Adventure");
            }
        });

        buttonArcade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByCategory("Arcade");
            }
        });
    }

    // Method to filter games by category and fetch Play Store results
    private void filterByCategory(String category) {
        resetButtonStyles();

        // Highlight selected button
        highlightButton(category);

        // If "All" is selected, show all games
        if (category.equals("All")) {
            gameList.clear();
            gameList.addAll(allGamesList);
            adapter.notifyDataSetChanged();
            return;
        }

        // Filter local games by category
        List<GameModel> filteredList = new ArrayList<>();
        for (GameModel game : allGamesList) {
            if (game.getTags().contains(category)) {
                filteredList.add(game);
            }
        }

        // Add fake Play Store results
        addPlayStoreResults(filteredList, category);

        gameList.clear();
        gameList.addAll(filteredList);
        adapter.notifyDataSetChanged();

        // Show toast to indicate we're showing local and Play Store results
        Toast.makeText(this, "Showing " + category + " games", Toast.LENGTH_SHORT).show();
    }

    // Method to add Play Store results based on category
    private void addPlayStoreResults(List<GameModel> list, String category) {
        // These are mock Play Store results
        switch (category) {
            case "Action":
                list.add(new GameModel("Call of Duty Mobile", "First-person shooter game",
                        R.drawable.ic_launcher, Arrays.asList("Action", "Shooter"), true));
                list.add(new GameModel("PUBG Mobile", "Battle royale game",
                        R.drawable.ic_launcher, Arrays.asList("Action", "Shooter"), true));
                list.add(new GameModel("Brawl Stars", "3v3 multiplayer action",
                        R.drawable.ic_launcher, Arrays.asList("Action", "Multiplayer"), true));
                break;
            case "Puzzle":
                list.add(new GameModel("Candy Crush Saga", "Match-3 puzzle game",
                        R.drawable.ic_launcher, Arrays.asList("Puzzle", "Casual"), true));
                list.add(new GameModel("Brain Out", "Challenging puzzles",
                        R.drawable.ic_launcher, Arrays.asList("Puzzle", "Brain Training"), true));
                list.add(new GameModel("Monument Valley", "Optical illusion puzzles",
                        R.drawable.ic_launcher, Arrays.asList("Puzzle", "Adventure"), true));
                break;
            case "Casual":
                list.add(new GameModel("Subway Surfers", "Endless runner game",
                        R.drawable.ic_launcher, Arrays.asList("Casual", "Arcade"), true));
                list.add(new GameModel("Hill Climb Racing", "Physics-based driving game",
                        R.drawable.ic_launcher, Arrays.asList("Casual", "Racing"), true));
                list.add(new GameModel("Fruit Ninja", "Fruit slicing game",
                        R.drawable.ic_launcher, Arrays.asList("Casual", "Arcade"), true));
                break;
            case "Strategy":
                list.add(new GameModel("Clash of Clans", "Build your village and attack others",
                        R.drawable.ic_launcher, Arrays.asList("Strategy", "MMO"), true));
                list.add(new GameModel("Chess", "Classic strategy board game",
                        R.drawable.ic_launcher, Arrays.asList("Strategy", "Board"), true));
                list.add(new GameModel("Plants vs Zombies", "Tower defense game",
                        R.drawable.ic_launcher, Arrays.asList("Strategy", "Tower Defense"), true));
                break;
            case "Adventure":
                list.add(new GameModel("Minecraft", "Open world adventure",
                        R.drawable.ic_launcher, Arrays.asList("Adventure", "Survival"), true));
                list.add(new GameModel("Genshin Impact", "Open-world action RPG",
                        R.drawable.ic_launcher, Arrays.asList("Adventure", "RPG"), true));
                list.add(new GameModel("Sky: Children of the Light", "Social adventure",
                        R.drawable.ic_launcher, Arrays.asList("Adventure", "Social"), true));
                break;
            case "Arcade":
                list.add(new GameModel("Pac-Man", "Classic arcade game",
                        R.drawable.ic_launcher, Arrays.asList("Arcade", "Classic"), true));
                list.add(new GameModel("Crossy Road", "Endless arcade hopper",
                        R.drawable.ic_launcher, Arrays.asList("Arcade", "Casual"), true));
                list.add(new GameModel("Geometry Dash", "Rhythm-based action platformer",
                        R.drawable.ic_launcher, Arrays.asList("Arcade", "Music"), true));
                break;
        }
    }

    // Method to open Play Store for a game
    private void openPlayStore(String gameName) {
        try {
            // Format the game name for search
            String query = gameName.replace(" ", "+");
            Uri uri = Uri.parse("market://search?q=" + query);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            // Play Store app is not installed, open in browser
            Uri uri = Uri.parse("https://play.google.com/store/search?q=" + gameName.replace(" ", "+"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    // Method to reset all button styles
    private void resetButtonStyles() {
        buttonAction.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        buttonAction.setTextColor(getResources().getColor(android.R.color.black));

        buttonPuzzle.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        buttonPuzzle.setTextColor(getResources().getColor(android.R.color.black));

        buttonCasual.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        buttonCasual.setTextColor(getResources().getColor(android.R.color.black));

        buttonStrategy.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        buttonStrategy.setTextColor(getResources().getColor(android.R.color.black));

        buttonAdventure.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        buttonAdventure.setTextColor(getResources().getColor(android.R.color.black));

        buttonArcade.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        buttonArcade.setTextColor(getResources().getColor(android.R.color.black));
    }

    // Method to highlight the selected button
    private void highlightButton(String category) {
        switch(category) {
            case "Action":
                buttonAction.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                buttonAction.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Puzzle":
                buttonPuzzle.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                buttonPuzzle.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Casual":
                buttonCasual.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                buttonCasual.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Strategy":
                buttonStrategy.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                buttonStrategy.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Adventure":
                buttonAdventure.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                buttonAdventure.setTextColor(getResources().getColor(android.R.color.white));
                break;
            case "Arcade":
                buttonArcade.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                buttonArcade.setTextColor(getResources().getColor(android.R.color.white));
                break;
        }
    }
}
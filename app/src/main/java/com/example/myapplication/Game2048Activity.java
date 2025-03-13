package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Game2048Activity extends AppCompatActivity {

    private TextView[][] cells = new TextView[4][4]; // 4x4 grid for 2048
    private int[][] board = new int[4][4]; // Internal board state
    private TextView textViewScore; // To display the score
    private int score = 0; // Current score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        // Initialize the grid cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String cellID = "cell_" + i + j;
                int resID = getResources().getIdentifier(cellID, "id", getPackageName());
                cells[i][j] = findViewById(resID);
            }
        }

        // Initialize the score text view
        textViewScore = findViewById(R.id.textViewScore);

        // Reset button to restart the game
        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(v -> resetGame());

        // Start the game
        resetGame();
    }

    private void resetGame() {
        // Clear the board and score
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
                cells[i][j].setText("");
                cells[i][j].setBackgroundColor(getResources().getColor(android.R.color.white));
            }
        }
        score = 0;
        textViewScore.setText("Score: " + score);

        // Add two initial tiles
        addRandomTile();
        addRandomTile();
    }

    private void addRandomTile() {
        Random random = new Random();
        int value = (random.nextInt(2) + 1) * 2; // 2 or 4
        int row, col;

        // Find an empty cell
        do {
            row = random.nextInt(4);
            col = random.nextInt(4);
        } while (board[row][col] != 0);

        // Place the tile
        board[row][col] = value;
        updateCell(row, col, value);
    }

    private void updateCell(int row, int col, int value) {
        cells[row][col].setText(String.valueOf(value));
        cells[row][col].setBackgroundColor(getCellColor(value));
    }

    private int getCellColor(int value) {
        // Assign colors based on the tile value
        switch (value) {
            case 2:
                return getResources().getColor(android.R.color.holo_orange_light);
            case 4:
                return getResources().getColor(android.R.color.holo_green_light);
            case 8:
                return getResources().getColor(android.R.color.holo_blue_light);
            case 16:
                return getResources().getColor(android.R.color.holo_red_light);
            case 32:
                return getResources().getColor(android.R.color.holo_purple);
            case 64:
                return getResources().getColor(android.R.color.holo_orange_dark);
            case 128:
                return getResources().getColor(android.R.color.holo_green_dark);
            case 256:
                return getResources().getColor(android.R.color.holo_blue_dark);
            case 512:
                return getResources().getColor(android.R.color.holo_red_dark);
            case 1024:
                return getResources().getColor(android.R.color.holo_purple);
            case 2048:
                return getResources().getColor(android.R.color.holo_orange_light);
            default:
                return getResources().getColor(android.R.color.white);
        }
    }

    // Handle swipe gestures (left, right, up, down)
    // Implement swipe logic here (e.g., using GestureDetector or OnTouchListener)
    // For simplicity, this example does not include swipe handling.
}
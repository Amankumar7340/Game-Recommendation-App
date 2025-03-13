package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3]; // 3x3 grid of buttons
    private boolean player1Turn = true; // Player 1 starts first
    private int roundCount = 0; // Track the number of moves

    private TextView textViewStatus; // To display the game status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // Set sky bluish background color
        findViewById(R.id.ticTacToeLayout).setBackgroundColor(Color.parseColor("#87CEEB"));

        // Initialize the buttons and set click listeners
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        // Initialize the status text view
        textViewStatus = findViewById(R.id.textViewStatus);

        // Reset button to restart the game
        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(v -> resetGame());
    }

    @Override
    public void onClick(View v) {
        // Handle button clicks
        if (!((Button) v).getText().toString().equals("")) {
            return; // If the button is already clicked, do nothing
        }

        if (player1Turn) {
            ((Button) v).setText("X"); // Player 1 is X
        } else {
            ((Button) v).setText("O"); // Player 2 is O
        }

        roundCount++; // Increment the move count

        // Check if a player has won
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw(); // If all buttons are filled and no one wins, it's a draw
        } else {
            player1Turn = !player1Turn; // Switch turns
            updateStatus();
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // Get the current state of the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows for a win
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        // Check columns for a win
        for (int j = 0; j < 3; j++) {
            if (field[0][j].equals(field[1][j]) && field[0][j].equals(field[2][j]) && !field[0][j].equals("")) {
                return true;
            }
        }

        // Check diagonals for a win
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false; // No winner yet
    }

    private void player1Wins() {
        textViewStatus.setText("Player 1 (X) Wins!");
        disableButtons();
    }

    private void player2Wins() {
        textViewStatus.setText("Player 2 (O) Wins!");
        disableButtons();
    }

    private void draw() {
        textViewStatus.setText("It's a Draw!");
        disableButtons();
    }

    private void updateStatus() {
        if (player1Turn) {
            textViewStatus.setText("Player 1's Turn (X)");
        } else {
            textViewStatus.setText("Player 2's Turn (O)");
        }
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false); // Disable all buttons after the game ends
            }
        }
    }

    private void resetGame() {
        // Reset the game state
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(""); // Clear the button text
                buttons[i][j].setEnabled(true); // Re-enable the buttons
            }
        }

        player1Turn = true; // Player 1 starts again
        roundCount = 0; // Reset the move count
        textViewStatus.setText("Player 1's Turn (X)");
    }
}
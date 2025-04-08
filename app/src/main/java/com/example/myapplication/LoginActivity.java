package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewError;
    private Button buttonLogin;

    // Define valid username and password combinations
    private final String USERNAME_1 = "admin";
    private final String PASSWORD_1 = "admin123";
    private final String USERNAME_2 = "user";
    private final String PASSWORD_2 = "user123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewError = findViewById(R.id.textViewError);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Style the app name in the login title
        TextView textViewLoginTitle = findViewById(R.id.textViewLoginTitle);
        String title = "GameREX";
        SpannableString spannableString = new SpannableString(title);

        // Find the index of "REX" in the title
        int indexOfRex = title.indexOf("REX");

        // Apply red color to "REX"
        if (indexOfRex != -1) {
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.RED), // Red color
                    indexOfRex, // Start index of "REX"
                    indexOfRex + 3, // End index of "REX"
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Set the styled title to the TextView
        textViewLoginTitle.setText(spannableString);

        // Set click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if username and password match any valid combination
        if ((username.equals(USERNAME_1) && password.equals(PASSWORD_1)) ||
                (username.equals(USERNAME_2) && password.equals(PASSWORD_2))) {
            // Successful login, proceed to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close login activity
        } else {
            // Show error message
            textViewError.setVisibility(View.VISIBLE);
        }
    }
}
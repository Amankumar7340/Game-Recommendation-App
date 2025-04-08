package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserMenuBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_user_menu, container, false);

        // Set up click listeners for menu items
        LinearLayout rateUsLayout = view.findViewById(R.id.rateUsLayout);
        LinearLayout contactUsLayout = view.findViewById(R.id.contactUsLayout);
        LinearLayout logoutLayout = view.findViewById(R.id.logoutLayout);
        TextView userNameTextView = view.findViewById(R.id.userNameTextView);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        Button submitFeedbackButton = view.findViewById(R.id.submitFeedbackButton);

        // Set up the feedback submission button
        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Toast.makeText(getContext(), "Thank you for your " + (int)rating + "-star feedback!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        rateUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Google Play Store
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getActivity().getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    // If Play Store not installed, open in browser
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
                dismiss();
            }
        });

        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open email client
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:amankumar.987635@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for GameREX App");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out functionality
                Toast.makeText(getContext(), "Logout clicked", Toast.LENGTH_SHORT).show();
                // Uncomment if you have a LoginActivity
                // Intent intent = new Intent(getActivity(), LoginActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // startActivity(intent);
                dismiss();
            }
        });

        return view;
    }
}
package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

public class Videopopup2 extends Dialog {

    private Context context;

    public Videopopup2(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_video_preview);

        // Initialize VideoView
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + context.getPackageName() + "/" + R.raw.flappy_bird_clone;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start(); // Start playing the video

        // Close Button
        Button buttonClose = findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(v -> dismiss()); // Close the dialog
    }
}
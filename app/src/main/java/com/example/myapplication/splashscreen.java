package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.VideoView;

public class splashscreen extends AppCompatActivity {

    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPOsition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoBG=findViewById(R.id.videoview);
        Uri uri=Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.penshoppe);
        videoBG.setVideoURI(uri);
        videoBG.start();

        videoBG.setOnPreparedListener((mediaPlayer) -> {
            mMediaPlayer=mediaPlayer;
            mMediaPlayer.setLooping(true);

            if (mCurrentVideoPOsition!=0){
                mMediaPlayer.seekTo(mCurrentVideoPOsition);
                mMediaPlayer.start();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in=new Intent(splashscreen.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        },10000);
    }
}
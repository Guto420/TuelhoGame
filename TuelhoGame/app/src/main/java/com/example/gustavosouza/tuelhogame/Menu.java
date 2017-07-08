package com.example.gustavosouza.tuelhogame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    private ImageButton buttonJogar;
    private ImageButton buttonCredito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttonJogar = (ImageButton)findViewById(R.id.btnPlay);
        buttonCredito = (ImageButton)findViewById(R.id.btncred);

        buttonJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                startActivity(intent);
            }
        });

        buttonCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), Creditos.class);
                startActivity(intent2);
            }
        });

    }
}

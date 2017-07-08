package com.example.gustavosouza.tuelhogame;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Game extends AppCompatActivity implements Runnable, ServiceConnection {

    private Recursos recursos;

    private Intent intent;

    final ServiceConnection connection = this;

    public ImageButton btncomida;
    public ImageButton btnagua;

    public TextView textcomida;
    public TextView textagua;

    private Handler handler;


    public boolean isUpdating, isDead, isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = new Intent(this, Service.class);
        startService(intent);
        bindService(new Intent(Game.this, Service.class), connection, Context.BIND_AUTO_CREATE);

        btnagua = (ImageButton) findViewById(R.id.btnAgua);
        btncomida = (ImageButton) findViewById(R.id.btnComida);

        textagua = (TextView) findViewById(R.id.numAgua);
        textcomida = (TextView) findViewById(R.id.numComida);

        handler = new Handler();
        handler.post(this);

        isDead = isPaused = false;
        isUpdating = true;

        btncomida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (recursos !=null && recursos.GetFood() <= 95) {
                    recursos.SetFood(5);
                    String text = " " + recursos.GetFood();
                    textcomida.setText(text);
                }
            }
        });

        btnagua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recursos !=null && recursos.GetWater() <= 95) {
                    recursos.SetWater(5);
                    String text = " " + recursos.GetWater();
                    textcomida.setText(text);
                }

            }
        });

    }

    public void update() {
        if(recursos != null) {
            textagua.setText(" " + recursos.GetWater());
            textcomida.setText(" " + recursos.GetFood());
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        UnbindConnection();
    }

    private void UnbindConnection(){

        if(recursos != null)
        {
            recursos = null;
            unbindService(connection);
        }
    }


    @Override
    public void run() {
        update();

        handler.postDelayed(this, 30);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Service.LocalBinder binder = (Service.LocalBinder) iBinder;
        recursos = binder.getService();

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        recursos = null;
    }


}

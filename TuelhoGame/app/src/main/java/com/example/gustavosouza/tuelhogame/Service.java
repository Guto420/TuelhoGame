package com.example.gustavosouza.tuelhogame;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavo.souza on 04/07/2017.
 */

public class Service extends android.app.Service implements Runnable, Recursos {

    private int agua;
    private int comida;
    private boolean active;

    private final LocalBinder connection = new LocalBinder();

    //This class returns to Activity the service reference.
    //With this reference is possible to get the Counter value and show to user.
    public class LocalBinder extends Binder
    {
        public Recursos getService()
        {
            return Service.this;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        comida = 100;
        agua = 100;

        active = true;
        new Thread(this).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        active = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return connection;
    }

    @Override
    public int GetFood() {
        return comida;
    }

    public void SetFood(int value) {
        if(value <= 5) comida += value;
        else comida = 0;
    }

    @Override
    public int GetWater() {
        return agua;
    }


    public void SetWater(int value) {
        agua += 5;
    }

    @Override
    public void run() {
        while(active)
        {
            comida--;
            agua--;

            if(comida <= 25 || agua <= 25)
            {
                //gerarNotificacao(view);
            }
            if (comida == 0 || agua == 0)
            {
                Intent intent2 = new Intent(getApplicationContext(), TuelhoMorreu.class);
                startActivity(intent2);
                active = false;
            }

            SetInterval();
        }

        stopSelf();
    }

    private void SetInterval()
    {
        try { Thread.sleep(1000); }
        catch(InterruptedException e) { e.printStackTrace(); }
    }

    public void gerarNotificacao(View view){

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, gerarnotificacao.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("TuelhoGame Notification");
        builder.setContentTitle("Tuelho está com fome");
        builder.setContentText("Alimente ele");
        builder.setSmallIcon(R.drawable.tuelho2);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        nm.notify(R.drawable.tuelho2, n);


    }
}

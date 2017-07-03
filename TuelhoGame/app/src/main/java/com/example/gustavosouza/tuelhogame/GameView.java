package com.example.gustavosouza.tuelhogame;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by gustavo on 03/07/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;

    private Thread gameThread = null;

    public GameView(Context context)
    {
        super(context);
    }

    @Override
    public void run() {
        while (playing)
        {
            update();
            
            draw();
            
            control();
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void draw() {
    }

    private void update() {
    }

    public void pause()
    {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e){

        }
    }


    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}

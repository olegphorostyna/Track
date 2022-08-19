package com.example.my.test.track;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double avarageUPS;
    private double avarageFPS;
    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public double getAvarageUPS() {
        return avarageUPS;
    }

    public double getAvarageFPS() {
        return avarageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime=0;

        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning){
            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            } finally {
                if(canvas!=null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }



            //Make this loop to run slower if update count is larger then MAX_UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime >0 ){
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Skip frames to keep up with target UPS
            while(sleepTime<0 && updateCount < MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
            }


            //Calculate average FPS and UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000){
                avarageUPS = updateCount/ (1E-3 * elapsedTime);
                avarageFPS = frameCount/ (1E-3 * elapsedTime);
                frameCount=0;
                updateCount=0;
                startTime = System.currentTimeMillis();
            }


        }
    }
}

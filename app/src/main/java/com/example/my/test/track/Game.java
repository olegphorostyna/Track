package com.example.my.test.track;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop loop;
    private Context context;

    public Game(Context context) {
        super(context);


        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.context = context;
        loop = new GameLoop(this, surfaceHolder);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        loop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
    }

    public void drawUPS(Canvas canvas){
            String avarageUPS = Double.toString(loop.getAvarageUPS());
            Paint paint = new Paint();
            int color  = ContextCompat.getColor(context, R.color.yellow);
            paint.setColor(color);
            paint.setTextSize(50);
            canvas.drawText("UPS: "+avarageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas){
        String avarageFPS = Double.toString(loop.getAvarageFPS());
        Paint paint = new Paint();
        int color  = ContextCompat.getColor(context, R.color.yellow);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: "+avarageFPS, 100, 200, paint);
    }

    public void update() {
    }
}

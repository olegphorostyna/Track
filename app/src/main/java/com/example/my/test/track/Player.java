package com.example.my.test.track;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private float posX;
    private float posY;
    private float radius;
    private Paint paint;

    public  Player(Context context, float posX, float posY, float radius){
        this.posX=posX;
        this.posY=posY;
        this.radius=radius;
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.playerColor);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(posX, posY,radius, paint);
    }

    public void update() {
    }

    public void setPosition(float x, float y) {
        this.posX=x;
        this.posY=y;
    }
}

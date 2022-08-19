package com.example.my.test.track;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Joystick {
    private Paint innerCirclePaint;
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private Paint outerCirclePaint;
    private double actuatorTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(Context context, int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius){
        this.outerCircleCenterPositionX = centerPositionX;
        this.outerCircleCenterPositionY = centerPositionY;
        this.innerCircleCenterPositionX = centerPositionX;
        this.innerCircleCenterPositionY = centerPositionY;

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(ContextCompat.getColor(context, R.color.yellow));
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
            canvas.drawCircle(outerCircleCenterPositionX,
                    outerCircleCenterPositionY,
                    outerCircleRadius,
                    outerCirclePaint);

        canvas.drawCircle(innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int)(outerCircleCenterPositionX+actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int)(outerCircleCenterPositionY+actuatorY*outerCircleRadius);
    }

    public boolean isPressed(float x, float y) {
        actuatorTouchDistance = Math.sqrt(Math.pow(outerCircleCenterPositionX - x,2)+
                Math.pow(outerCircleCenterPositionY - y,2));
        return actuatorTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean pressed) {
        this.isPressed = pressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(float x, float y) {
        double deltaX = x-outerCircleCenterPositionX;
        double deltaY = y-outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX,2)+
                Math.pow(deltaY,2));

        if(deltaDistance<outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }
    public double getActuatorY() {
        return actuatorY;
    }
}

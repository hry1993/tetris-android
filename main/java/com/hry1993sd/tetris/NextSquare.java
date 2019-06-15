package com.hry1993sd.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class NextSquare extends SurfaceView implements SurfaceHolder.Callback{

    public DrawSquare drawSquare;

    public NextSquare(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.getHolder().addCallback(this);
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawSquare.draw(canvas);
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}

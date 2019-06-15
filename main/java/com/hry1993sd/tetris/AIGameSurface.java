package com.hry1993sd.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AIGameSurface extends SurfaceView implements SurfaceHolder.Callback{

    public AIGameLogic aiGameLogic;
    public AIGameThread aiGameThread;
    public AILogicThread aiLogicThread;

    public AIGameSurface(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.getHolder().addCallback(this);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.aiGameLogic.draw(canvas);
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}

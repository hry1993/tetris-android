package com.hry1993sd.tetris;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class AIGameThread extends Thread {

    public boolean running;
    public int speed = 1000;

    private AIGameSurface aiGameSurface;
    private SurfaceHolder surfaceHolder;
    private AIGameLogic aiGameLogic;

    public AIGameThread(AIGameSurface aiGameSurface, SurfaceHolder surfaceHolder, AIGameLogic aiGameLogic) {
        this.aiGameSurface = aiGameSurface;
        this.surfaceHolder = surfaceHolder;
        this.aiGameLogic = aiGameLogic;
    }

    public void run() {
        while(running) {
            try {
                AIGameThread.sleep(speed);
            } catch (Exception e) {
                // nothing
            }
            try {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    aiGameSurface.draw(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    aiGameLogic.drop();
            } catch (Exception e) {
                // nothing
            }
        }
    }
}

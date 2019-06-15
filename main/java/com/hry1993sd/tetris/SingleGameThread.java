package com.hry1993sd.tetris;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class SingleGameThread extends Thread {

    public boolean running;

    private SingleGameSurface singleGameSurface;
    private SurfaceHolder surfaceHolder;
    private SingleGameLogic singleGameLogic;

    public SingleGameThread(SingleGameSurface singleGameSurface, SurfaceHolder surfaceHolder, SingleGameLogic singleGameLogic) {
        this.singleGameSurface = singleGameSurface;
        this.surfaceHolder = surfaceHolder;
        this.singleGameLogic = singleGameLogic;
    }

    public void run() {
        while(running) {
            try {
                SingleGameThread.sleep(1000);
            } catch (Exception e) {
                // nothing
            }
            try {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    singleGameSurface.draw(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    singleGameLogic.drop();
            } catch (Exception e) {
                // nothing
            }
        }
    }
}

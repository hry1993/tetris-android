package com.hry1993sd.tetris;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class AILogicThread extends Thread {

    public boolean running;
    public int speed = 250;
    private int count = 0;

    private AIGameLogic aiGameLogic;

    public AILogicThread(AIGameLogic aiGameLogic) {
        this.aiGameLogic = aiGameLogic;
    }

    public void run() {
        while(running) {
            try {
                AILogicThread.sleep(speed);
            } catch (Exception e) {
                // nothing
            }
            try {
                count++;
                if (count >= 8) {
                    aiGameLogic.attackedCount += 1;
                    count -= 8;
                    aiGameLogic.handler.sendEmptyMessage(4);
                }
                if (aiGameLogic.aiScore < 25) {
                    aiGameLogic.aiScore+=1;
                } else {
                    aiGameLogic.aiEffect();
                }
                aiGameLogic.handler.sendEmptyMessage(5);
            } catch (Exception e) {
                // nothing
            }
        }
    }
}

package com.hry1993sd.tetris;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class PlayWithAIActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AIGameSurface aiGameSurface;
    private ConstraintLayout pauseLayout,restartLayout;
    private Button pauseButton,resumeButton,rotateButton,leftButton,rightButton,fallButton,restartButton,holdButton,slowButton;
    private Handler handler;
    private NextAISquare nextSquare;
    private HoldAISquare holdSquare;
    private Canvas canvas;
    private ProgressBar aiBar,bar;
    private RatingBar rateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_play_with_ai);

        aiGameSurface = findViewById(R.id.aigamecontainer);
        aiGameSurface.aiGameLogic = new AIGameLogic(aiGameSurface);
        nextSquare = findViewById(R.id.nextAISquare);
        nextSquare.drawSquare = aiGameSurface.aiGameLogic;
        holdSquare = findViewById(R.id.holdAISquare);
        holdSquare.drawSquare = aiGameSurface.aiGameLogic;
        sharedPreferences = getSharedPreferences("Skill", 0);
        for (int i = 0;i < 15; i++) {
            aiGameSurface.aiGameLogic.skillEffect[i] = sharedPreferences.getBoolean("skill" + i,false);
        }
        aiGameSurface.aiGameLogic.handler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case 0:
                        gameOver();
                        break;
                    case 1:
                        bar.setProgress(aiGameSurface.aiGameLogic.score);
                        break;
                    case 2:
                        canvas = nextSquare.getHolder().lockCanvas();
                        nextSquare.draw(canvas);
                        nextSquare.getHolder().unlockCanvasAndPost(canvas);
                        break;
                    case 3:
                        canvas = holdSquare.getHolder().lockCanvas();
                        holdSquare.draw(canvas);
                        holdSquare.getHolder().unlockCanvasAndPost(canvas);
                        break;
                    case 4:
                        switch (aiGameSurface.aiGameLogic.attackedCount) {
                            case 0:
                               rateBar.setRating(0);
                               break;
                            case 1:
                                rateBar.setRating(1);
                                break;
                            case 2:
                                rateBar.setRating(2);
                                break;
                            default:
                                rateBar.setRating(3);
                                break;
                        }
                        break;
                    case 5:
                        aiBar.setProgress(aiGameSurface.aiGameLogic.aiScore);
                        break;
                }
                return true;
            }
        });
        aiBar = findViewById(R.id.aiBar);
        aiBar.setMax(25);
        bar = findViewById(R.id.bar);
        bar.setMax(25);
        rateBar = findViewById(R.id.ratingBar);
        rateBar.setNumStars(3);
        aiGameSurface.aiGameThread = new AIGameThread(aiGameSurface, aiGameSurface.getHolder(), aiGameSurface.aiGameLogic);
        aiGameSurface.aiGameLogic.init();
        aiGameSurface.aiGameThread.start();
        aiGameSurface.aiLogicThread = new AILogicThread(aiGameSurface.aiGameLogic);
        aiGameSurface.aiLogicThread.running = true;
        aiGameSurface.aiLogicThread.start();
        restartLayout = findViewById(R.id.restartcontainer);
        pauseLayout = findViewById(R.id.pausecontainer);
        pauseButton = findViewById(R.id.pause);
        resumeButton = findViewById(R.id.resume);
        restartButton = findViewById(R.id.restart);
        rotateButton = findViewById(R.id.rotate);
        leftButton = findViewById(R.id.left);
        rightButton = findViewById(R.id.right);
        fallButton = findViewById(R.id.fall);
        holdButton =findViewById(R.id.holdButton);
        slowButton = findViewById(R.id.slow);
    }

    @Override
    protected void onPause() {
        super.onPause();
        aiGameSurface.aiGameThread.running = false;
        aiGameSurface.aiGameThread.interrupt();
        aiGameSurface.aiLogicThread.running = false;
        aiGameSurface.aiLogicThread.interrupt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!aiGameSurface.aiGameThread.running) {
            aiGameSurface.aiGameThread = new AIGameThread(aiGameSurface, aiGameSurface.getHolder(), aiGameSurface.aiGameLogic);
            aiGameSurface.aiGameThread.running = true;
            aiGameSurface.aiGameThread.start();
        }
        if (!aiGameSurface.aiLogicThread.running) {
            aiGameSurface.aiLogicThread = new AILogicThread(aiGameSurface.aiGameLogic);
            aiGameSurface.aiLogicThread.running = true;
            aiGameSurface.aiLogicThread.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aiGameSurface.aiGameThread.interrupt();
        aiGameSurface.aiLogicThread.interrupt();
    }

    public void restart(View view) {
        this.recreate();
    }

    public void gameOver() {
        aiGameSurface.aiGameThread.running = false;
        aiGameSurface.aiGameThread.interrupt();
        aiGameSurface.aiLogicThread.running = false;
        aiGameSurface.aiLogicThread.interrupt();
        fallButton.setEnabled(false);
        pauseButton.setEnabled(false);
        rotateButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        holdButton.setEnabled(false);
        slowButton.setEnabled(false);
        restartButton.setEnabled(true);
        restartLayout.setVisibility(View.VISIBLE);
    }

    public void rotate(View view) {
        aiGameSurface.aiGameLogic.rotate(1);
        Canvas canvas = aiGameSurface.getHolder().lockCanvas();
        aiGameSurface.draw(canvas);
        aiGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void left(View view) {
        aiGameSurface.aiGameLogic.move(-1);
        Canvas canvas = aiGameSurface.getHolder().lockCanvas();
        aiGameSurface.draw(canvas);
        aiGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void right(View view) {
        aiGameSurface.aiGameLogic.move(1);
        Canvas canvas = aiGameSurface.getHolder().lockCanvas();
        aiGameSurface.draw(canvas);
        aiGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void fall(View view) {
        aiGameSurface.aiGameLogic.fall();
        Canvas canvas = aiGameSurface.getHolder().lockCanvas();
        aiGameSurface.draw(canvas);
        aiGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void pause(View view) {
        aiGameSurface.aiGameThread.running = false;
        aiGameSurface.aiGameThread.interrupt();
        aiGameSurface.aiLogicThread.running = false;
        aiGameSurface.aiLogicThread.interrupt();
        fallButton.setEnabled(false);
        pauseButton.setEnabled(false);
        rotateButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        holdButton.setEnabled(false);
        slowButton.setEnabled(false);
        resumeButton.setEnabled(true);
        pauseLayout.setVisibility(View.VISIBLE);
    }

    public void resume(View view) {
        fallButton.setEnabled(true);
        pauseButton.setEnabled(true);
        rotateButton.setEnabled(true);
        leftButton.setEnabled(true);
        rightButton.setEnabled(true);
        holdButton.setEnabled(true);
        slowButton.setEnabled(true);
        resumeButton.setEnabled(false);
        pauseLayout.setVisibility(View.INVISIBLE);
        if (!aiGameSurface.aiGameThread.running) {
            aiGameSurface.aiGameThread = new AIGameThread(aiGameSurface, aiGameSurface.getHolder(), aiGameSurface.aiGameLogic);
            aiGameSurface.aiGameThread.running = true;
            aiGameSurface.aiGameThread.start();
        }
        if (!aiGameSurface.aiLogicThread.running) {
            aiGameSurface.aiLogicThread = new AILogicThread(aiGameSurface.aiGameLogic);
            aiGameSurface.aiLogicThread.running = true;
            aiGameSurface.aiLogicThread.start();
        }
    }

    public void hold(View view) {
        aiGameSurface.aiGameLogic.hold();
        Canvas canvas = holdSquare.getHolder().lockCanvas();
        holdSquare.draw(canvas);
        holdSquare.getHolder().unlockCanvasAndPost(canvas);
        canvas = aiGameSurface.getHolder().lockCanvas();
        aiGameSurface.draw(canvas);
        aiGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void slow(View view) {
        if(aiGameSurface.aiGameLogic.score == 25) {
            aiGameSurface.aiGameLogic.score -= 25;
            aiGameSurface.aiGameLogic.aiScore -= 12;
            bar.setProgress(aiGameSurface.aiGameLogic.score);
            bar.setProgress(aiGameSurface.aiGameLogic.score);
        }
    }

}
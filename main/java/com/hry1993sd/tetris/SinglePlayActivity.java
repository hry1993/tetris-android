package com.hry1993sd.tetris;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SinglePlayActivity extends AppCompatActivity{

    private SharedPreferences sharedPreferences;
    private int highestScore;
    private SingleGameSurface singleGameSurface;
    private ConstraintLayout pauseLayout,restartLayout;
    private Button pauseButton,resumeButton,rotateButton,leftButton,rightButton,fallButton,restartButton,holdButton;
    private TextView scoreView,highestScoreView;
    private Handler handler;
    private NextSquare nextSquare;
    private HoldSquare holdSquare;
    private Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_single_play);

        singleGameSurface = findViewById(R.id.gamecontainer);
        singleGameSurface.singleGameLogic = new SingleGameLogic(singleGameSurface);
        nextSquare = findViewById(R.id.nextSquare);
        nextSquare.drawSquare = new DrawSquare();
        holdSquare = findViewById(R.id.holdSquare);
        holdSquare.drawSquare = new DrawSquare();
        sharedPreferences = getSharedPreferences("score", 0);
        highestScore = sharedPreferences.getInt("score", 0);
        scoreView = findViewById(R.id.score);
        highestScoreView = findViewById(R.id.highestscore);
        String highestScoreText = "highest: " + String.valueOf(highestScore);
        highestScoreView.setText(highestScoreText);
        handler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case 0:
                        gameOver();
                        break;
                    case 1:
                        String scoreText = "current: " + String.valueOf(singleGameSurface.singleGameLogic.getScore());
                        scoreView.setText(scoreText);
                        if (singleGameSurface.singleGameLogic.getScore() > highestScore) {
                            highestScore = singleGameSurface.singleGameLogic.getScore();
                            String highestScoreText = "highest: " + String.valueOf(singleGameSurface.singleGameLogic.getScore());
                            highestScoreView.setText(highestScoreText);
                        }
                        break;
                    case 2:
                        nextSquare.drawSquare.shape = singleGameSurface.singleGameLogic.shapeNext;
                        canvas = nextSquare.getHolder().lockCanvas();
                        nextSquare.draw(canvas);
                        nextSquare.getHolder().unlockCanvasAndPost(canvas);
                        break;
                    case 3:
                        canvas = holdSquare.getHolder().lockCanvas();
                        holdSquare.draw(canvas);
                        holdSquare.getHolder().unlockCanvasAndPost(canvas);
                        break;
                }
                return true;
            }
        });
        singleGameSurface.singleGameLogic.setHandler(handler);
        singleGameSurface.singleGameThread = new SingleGameThread(singleGameSurface, singleGameSurface.getHolder(), singleGameSurface.singleGameLogic);
        singleGameSurface.singleGameLogic.init();
        singleGameSurface.singleGameThread.start();
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        singleGameSurface.singleGameThread.running = false;
        singleGameSurface.singleGameThread.interrupt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!singleGameSurface.singleGameThread.running) {
            singleGameSurface.singleGameThread = new SingleGameThread(singleGameSurface, singleGameSurface.getHolder(), singleGameSurface.singleGameLogic);
            singleGameSurface.singleGameThread.running = true;
            singleGameSurface.singleGameThread.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putInt("score",highestScore).apply();
        singleGameSurface.singleGameThread.interrupt();
    }

    public void restart(View view) {
        this.recreate();
    }

    public void gameOver() {
        singleGameSurface.singleGameThread.interrupt();
        fallButton.setEnabled(false);
        pauseButton.setEnabled(false);
        rotateButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        holdButton.setEnabled(false);
        restartButton.setEnabled(true);
        restartLayout.setVisibility(View.VISIBLE);
    }

    public void rotate(View view) {
        singleGameSurface.singleGameLogic.rotate(1);
        Canvas canvas = singleGameSurface.getHolder().lockCanvas();
        singleGameSurface.draw(canvas);
        singleGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void left(View view) {
        singleGameSurface.singleGameLogic.move(-1);
        Canvas canvas = singleGameSurface.getHolder().lockCanvas();
        singleGameSurface.draw(canvas);
        singleGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void right(View view) {
        singleGameSurface.singleGameLogic.move(1);
        Canvas canvas = singleGameSurface.getHolder().lockCanvas();
        singleGameSurface.draw(canvas);
        singleGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void fall(View view) {
        singleGameSurface.singleGameLogic.fall();
        Canvas canvas = singleGameSurface.getHolder().lockCanvas();
        singleGameSurface.draw(canvas);
        singleGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }

    public void pause(View view) {
        singleGameSurface.singleGameThread.running = false;
        singleGameSurface.singleGameThread.interrupt();
        fallButton.setEnabled(false);
        pauseButton.setEnabled(false);
        rotateButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        holdButton.setEnabled(false);
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
        resumeButton.setEnabled(false);
        pauseLayout.setVisibility(View.INVISIBLE);
        if (!singleGameSurface.singleGameThread.running) {
            singleGameSurface.singleGameThread = new SingleGameThread(singleGameSurface, singleGameSurface.getHolder(), singleGameSurface.singleGameLogic);
            singleGameSurface.singleGameThread.running = true;
            singleGameSurface.singleGameThread.start();
        }
    }

    public void hold(View view) {
        singleGameSurface.singleGameLogic.hold();
        holdSquare.drawSquare.shape = singleGameSurface.singleGameLogic.shapeHold;
        Canvas canvas = holdSquare.getHolder().lockCanvas();
        holdSquare.draw(canvas);
        holdSquare.getHolder().unlockCanvasAndPost(canvas);
        canvas = singleGameSurface.getHolder().lockCanvas();
        singleGameSurface.draw(canvas);
        singleGameSurface.getHolder().unlockCanvasAndPost(canvas);
    }
}

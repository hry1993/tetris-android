package com.hry1993sd.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;

import java.util.Random;

import static com.hry1993sd.tetris.MainActivity.gridSpace;

public class SingleGameLogic {

    private final Point[][][] squares = {
            // I-Piece
            {
                    { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
                    { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) }
            },

            // J-Piece
            {
                    { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(0, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2) }

            },

            // L-Piece
            {
                    { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(2, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0) },

            },

            // O-Piece
            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },

            // S-Piece
            {
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            },

            // T-Piece
            {
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            },

            // Z-Piece
            {
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
            }
    };

    private final int[] squareColors = {
            Color.CYAN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.DKGRAY, Color.RED
    };

    private SingleGameSurface singleGameSurface;

    private Point position;
    private int rotation;
    private int shape;
    public int shapeNext;
    public int shapeHold = -1;
    private Handler handler;

    private Paint painter = new Paint();
    private int[][] checker = new int[12][22];

    private int score = 0;

    private float width = gridSpace * 10;
    private float height = 20 * gridSpace;

    public SingleGameLogic(SingleGameSurface singleGameSurface) {
        this.singleGameSurface = singleGameSurface;
    }

    public void hold() {
        if (shapeHold == -1) {
            shapeHold = shape;
            newSquare();
        } else if (!unHoldable(position.x,position.y)) {
            int temp = shape;
            shape = shapeHold;
            shapeHold = temp;
        }
    }

    public void nextSquare() {
        Random r = new Random();
        shapeNext = r.nextInt(7);
        handler.sendEmptyMessage(2);
    }

    public void newSquare() {
        position = new Point(5,0);
        rotation = 0;
        shape = shapeNext;
        nextSquare();
    }

    private boolean unHoldable(int x, int y) {
        for (Point p : squares[shapeHold][0]) {
            if (checker[p.x + x][p.y + y] != Color.WHITE) {
                return true;
            }
        }
        return false;
    }

    private boolean unMovable(int x, int y, int rotation) {
        for (Point p : squares[shape][rotation]) {
            if (checker[p.x + x][p.y + y] != Color.WHITE) {
                return true;
            }
        }
        return false;
    }

    public void fall() {
        while(!unMovable(position.x,position.y + 1,rotation)) {
            position.y += 1;
        }
        insert();
    }

    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if(!unMovable(position.x,position.y,newRotation)) {
            rotation = newRotation;
        }
    }

    public void move(int i) {
        if (!unMovable(position.x + i,position.y,rotation)) {
            position.x += i;
        }
    }

    public void drop() {
        if (!unMovable(position.x,position.y + 1,rotation)) {
            position.y += 1;
        } else {
            insert();
        }
    }

    private void clearRow(int row) {
        for (int j = row; j > 1; j-- ) {
            for (int i = 1; i < 11; i++) {
                checker[i][j] = checker[i][j - 1];
            }
        }
    }

    private void clearRows() {
        boolean full;
        int number = 0;

        for (int j = 20; j >= 1; j--) {
            full = false;
            for (int i = 1; i < 11; i++) {
                if (checker[i][j] == Color.WHITE) {
                    full = true;
                    break;
                }
            }
            if (!full) {
                clearRow(j);
                j += 1;
                number += 1;
            }
        }

        switch (number) {
            case 1:score += 1;break;
            case 2:score += 2;break;
            case 3:score += 4;break;
            case 4:score += 8;break;
        }
        handler.sendEmptyMessage(1);
    }

    private void gameOver() {
        for (int i = 1; i < 11; i++) {
            if (checker[i][1] != Color.WHITE) {
                singleGameSurface.singleGameThread.running = false;
                handler.sendEmptyMessage(0);
            }
        }
    }

    private void insert() {
        for(Point p : squares[shape][rotation]) {
            checker[position.x + p.x][position.y + p.y] = squareColors[shape];
        }
        clearRows();
        gameOver();
        newSquare();
    }

    public void draw(Canvas canvas) {

        RectF rect;
        painter.setColor(Color.WHITE);

        for(int i = 1; i < 11; i++) {
            for(int j = 1; j < 21; j++) {
                painter.setColor(checker[i][j]);
                rect = new RectF((i-1) * gridSpace,(j-1) * gridSpace,i * gridSpace,j * gridSpace);
                canvas.drawRect(rect,painter);
            }
        }

        painter.setColor(squareColors[shape]);

        for(Point p : squares[shape][rotation]) {
            rect = new RectF((p.x+position.x-1) * gridSpace,(p.y+position.y - 1) * gridSpace,
                    (p.x + position.x) * gridSpace, (p.y + position.y) * gridSpace);
            canvas.drawRect(rect,painter);
        }

        float startX;
        float stopX;
        float startY;
        float stopY;

        painter.setColor(Color.LTGRAY);
        painter.setStrokeWidth(4);

        //Vertical Grid-lines
        for (int i = 0; i < 21; i++) {

            startX = 0;
            startY = i* gridSpace;

            stopX = width;
            stopY = i* gridSpace;

            canvas.drawLine(startX, startY, stopX, stopY, painter);

        }

        //Horizontal Grid-lines
        for (int i = 0; i < 11; i++) {

            startX = i* gridSpace;
            startY = 0;

            stopX = i* gridSpace;
            stopY = height;

            canvas.drawLine(startX, startY, stopX, stopY, painter);
        }
    }

    public void init() {
        for (int i = 0;i < 12;i++) {
            for (int j = 0;j < 22;j++) {
                if (i == 0 ||i == 11||j == 21) {
                    checker[i][j] = Color.BLACK;
                } else {
                    checker[i][j] = Color.WHITE;
                }
            }
        }
        nextSquare();
        newSquare();
        singleGameSurface.singleGameThread.running = true;
        handler.sendEmptyMessage(3);
    }

    public int getScore() {
        return score;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}

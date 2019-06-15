package com.hry1993sd.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;

import java.util.Random;

import static com.hry1993sd.tetris.MainActivity.gridSpace;

public class AIGameLogic {
    private final Point[][][] regularSquares = {
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

    private final Point[][][] chaosSquare = {
            {
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(1,1), new Point(2,1)},
                    {new Point(1,0), new Point(0,1), new Point(1,1), new Point(0,2), new Point(1,2)},
                    {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1)},
                    {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1), new Point(0,2)}
            },
            {
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(0,1), new Point(1,1)},
                    {new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1), new Point(1,2)},
                    {new Point(0,1), new Point(1,0), new Point(1,1), new Point(2,0), new Point(2,1)},
                    {new Point(0,0), new Point(0,1), new Point(0,2), new Point(1,1), new Point(1,2)}
            },
            {
                    {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2)},
                    {new Point(2,0), new Point(2,1), new Point(1,1), new Point(1,2), new Point(0,2)},
                    {new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,2)},
                    {new Point(1,0), new Point(2,0), new Point(1,1), new Point(0,1), new Point(0,2)}
            },
            {
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0), new Point(3,1)},
                    {new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3), new Point(0,3)},
                    {new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1)},
                    {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3), new Point(1,0)}
            },
            {
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0), new Point(0,1)},
                    {new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3)},
                    {new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1), new Point(3,0)},
                    {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3), new Point(1,3)}
            },
            {
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(0,1), new Point(2,1)},
                    {new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2), new Point(0,2)},
                    {new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1), new Point(2,0)},
                    {new Point(0,0), new Point(1,0), new Point(0,1), new Point(0,2), new Point(1,2)}
            },
            {
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(0,1), new Point(0,2)},
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(2,1), new Point(2,2)},
                    {new Point(2,0), new Point(2,1), new Point(2,2), new Point(1,2), new Point(0,2)},
                    {new Point(0,0), new Point(0,1), new Point(0,2), new Point(1,2), new Point(2,2)}
            },
    };

    private Point[][][] squares = regularSquares;
    private Point[][][] squaresNext = regularSquares;
    private Point[][][] squaresHold = regularSquares;

    private final int[] squareColors = {
            Color.CYAN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.DKGRAY, Color.RED
    };

    private AIGameSurface aiGameSurface;
    public boolean[] skillEffect = new boolean[15];

    private Point position;
    private int rotation;
    private int shape;
    private int chaosCount = 0;
    private int speedCount = 0;
    private int shapeNext;
    private int shapeHold = -1;
    private int holdStatue = 0;
    private int dropStatue = 0;
    private int nextStatue = 0;
    public int attackedCount = 0;
    private int speedEffect = 0;
    private int rowEffect = 0;
    private int cancelEffect = 0;
    private int boostEffect = 0;
    private int chaosEffect = 0;
    public int aiScore = 0;
    public Handler handler;

    private Paint painter = new Paint();
    private int[][] checker = new int[12][22];

    public int score = 24;

    private float width = gridSpace * 10;
    private float height = 20 * gridSpace;

    public AIGameLogic(AIGameSurface aiGameSurface) {
        this.aiGameSurface = aiGameSurface;
    }

    public void hold() {
        if (dropStatue == 1 && holdStatue == 0) {
            squares = regularSquares;
            squaresHold = chaosSquare;
            dropStatue = 0;
            holdStatue = 1;
        } else if (dropStatue == 0 && holdStatue == 1) {
            squaresHold = regularSquares;
            squares = chaosSquare;
            dropStatue = 1;
            holdStatue = 0;
        }

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
        if (chaosCount > 0) {
            squaresNext = chaosSquare;
            nextStatue = 1;
            chaosCount--;
        } else {
            squaresNext = regularSquares;
            nextStatue = 0;
        }
        if (speedCount > 0) {
            aiGameSurface.aiGameThread.speed = 250;
            speedCount--;
        } else {
            aiGameSurface.aiGameThread.speed = 1000;
        }
        shapeNext = r.nextInt(7);
        handler.sendEmptyMessage(2);
    }

    public void newSquare() {
        squares = squaresNext;
        dropStatue = nextStatue;
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

        if (score >= 25) {
            score = 25;
        }

        handler.sendEmptyMessage(1);
    }

    private void addRow() {
        boolean addable = true;
        Random r = new Random();
        for (int i = 1; i < 11; i++) {
            if (checker[i][0] != Color.WHITE) {
                addable = false;
            }
        }
        if (addable) {
            for (int j = 1; j < 20; j++ ) {
                for (int i = 1; i < 11; i++) {
                    checker[i][j] = checker[i][j + 1];
                }
            }
            for (int i = 1; i < 11; i++) {
                checker[i][20] = Color.GRAY;
            }
        }
        checker[r.nextInt(10) + 1][20] = Color.WHITE;
    }

    private void gameOver() {
        for (int i = 1; i < 11; i++) {
            if (checker[i][1] != Color.WHITE) {
                aiGameSurface.aiGameThread.running = false;
                handler.sendEmptyMessage(0);
            }
        }
    }

    private void insert() {
        for(Point p : squares[shape][rotation]) {
            checker[position.x + p.x][position.y + p.y] = squareColors[shape];
        }
        clearRows();
        while (attackedCount >= 3) {
            attackedCount -= 3;
            addRow();
            handler.sendEmptyMessage(4);
        }
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
        aiGameSurface.aiGameThread.running = true;
        handler.sendEmptyMessage(3);
        if (skillEffect[0]) {
            speedEffect += 3;
        }
        if (skillEffect[1]) {
            speedEffect += 4;
        }
        if (skillEffect[2]) {
            speedEffect += 5;
        }
        if (skillEffect[3]) {
            rowEffect += 3;
        }
        if (skillEffect[4]) {
            rowEffect += 4;
        }
        if (skillEffect[5]) {
            rowEffect += 5;
        }
        if (skillEffect[6]) {
            cancelEffect += 3;
        }
        if (skillEffect[7]) {
            cancelEffect += 4;
        }
        if (skillEffect[8]) {
            cancelEffect += 5;
        }
        if (skillEffect[9]) {
            boostEffect += 3;
        }
        if (skillEffect[10]) {
            boostEffect += 4;
        }
        if (skillEffect[11]) {
            boostEffect += 5;
        }
        if (skillEffect[12]) {
            chaosEffect += 1;
        }
        if (skillEffect[13]) {
            chaosEffect += 2;
        }
        if (skillEffect[14]) {
            chaosEffect += 3;
        }
    }

    public void aiEffect() {
        aiScore-=25;
        speedCount += speedEffect;
        aiScore += boostEffect;
        score -= cancelEffect;
        attackedCount += rowEffect;
        chaosCount += chaosEffect;
    }

    public int getScore() {
        return score;
    }

    public void drawSquareHold(Canvas canvas) {

        RectF rect;
        Paint painter = new Paint();
        painter.setColor(Color.WHITE);

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                rect = new RectF(i * gridSpace,j * gridSpace,(i + 1) * gridSpace,(j + 1) * gridSpace);
                canvas.drawRect(rect,painter);
            }
        }

        if (shapeHold >= 0) {
            painter.setColor(squareColors[shapeHold]);

            for (Point p : squaresHold[shapeHold][0]) {
                rect = new RectF((p.x) * gridSpace, (p.y) * gridSpace,
                        (p.x + 1) * gridSpace, (p.y + 1) * gridSpace);
                canvas.drawRect(rect, painter);
            }
        }

        float startX;
        float stopX;
        float startY;
        float stopY;

        painter.setColor(Color.LTGRAY);
        painter.setStrokeWidth(4);

        //Vertical Grid-lines
        for (int i = 0; i < 4; i++) {

            startX = 0;
            startY = i* gridSpace;

            stopX = gridSpace * 4;
            stopY = i* gridSpace;

            canvas.drawLine(startX, startY, stopX, stopY, painter);

        }

        //Horizontal Grid-lines
        for (int i = 0; i < 5; i++) {

            startX = i* gridSpace;
            startY = 0;

            stopX = i* gridSpace;
            stopY = gridSpace * 3;

            canvas.drawLine(startX, startY, stopX, stopY, painter);
        }
    }

    public void drawSquareNext(Canvas canvas) {

        RectF rect;
        Paint painter = new Paint();
        painter.setColor(Color.WHITE);

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                rect = new RectF(i * gridSpace,j * gridSpace,(i + 1) * gridSpace,(j + 1) * gridSpace);
                canvas.drawRect(rect,painter);
            }
        }

        if (shapeNext >= 0) {
            painter.setColor(squareColors[shapeNext]);

            for (Point p : squaresNext[shapeNext][0]) {
                rect = new RectF((p.x) * gridSpace, (p.y) * gridSpace,
                        (p.x + 1) * gridSpace, (p.y + 1) * gridSpace);
                canvas.drawRect(rect, painter);
            }
        }

        float startX;
        float stopX;
        float startY;
        float stopY;

        painter.setColor(Color.LTGRAY);
        painter.setStrokeWidth(4);

        //Vertical Grid-lines
        for (int i = 0; i < 4; i++) {

            startX = 0;
            startY = i* gridSpace;

            stopX = gridSpace * 4;
            stopY = i* gridSpace;

            canvas.drawLine(startX, startY, stopX, stopY, painter);

        }

        //Horizontal Grid-lines
        for (int i = 0; i < 5; i++) {

            startX = i* gridSpace;
            startY = 0;

            stopX = i* gridSpace;
            stopY = gridSpace * 3;

            canvas.drawLine(startX, startY, stopX, stopY, painter);
        }
    }
}

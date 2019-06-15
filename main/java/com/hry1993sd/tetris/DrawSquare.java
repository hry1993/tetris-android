package com.hry1993sd.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import static com.hry1993sd.tetris.MainActivity.gridSpace;

public class DrawSquare {
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

    public int shape = -1;

    public void draw(Canvas canvas) {

        RectF rect;
        Paint painter = new Paint();
        painter.setColor(Color.WHITE);

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 2; j++) {
                rect = new RectF(i * gridSpace,j * gridSpace,(i + 1) * gridSpace,(j + 1) * gridSpace);
                canvas.drawRect(rect,painter);
            }
        }

        if (shape >= 0) {
            painter.setColor(squareColors[shape]);

            for (Point p : squares[shape][0]) {
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
        for (int i = 0; i < 3; i++) {

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
            stopY = gridSpace * 2;

            canvas.drawLine(startX, startY, stopX, stopY, painter);
        }
    }
}

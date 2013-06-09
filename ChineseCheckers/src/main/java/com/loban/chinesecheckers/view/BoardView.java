package com.loban.chinesecheckers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.loban.chinesecheckers.enums.PlayerColor;

import com.loban.chinesecheckers.model.Board;
import com.loban.chinesecheckers.model.BoardHole;
import com.loban.chinesecheckers.model.PlayerPiece;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class BoardView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    private Board board;

    private int canvasWidth;
    private int canvasHeight;
    private float tileSize;

    private Thread thread;
    private boolean isRunning;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Random random = new Random();

    public BoardView(Context context, Board board) {
        super(context);
        getHolder().addCallback(this);

        this.board = board;
    }

    // SurfaceHolder.Callback overrides

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isRunning = true;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        synchronized (surfaceHolder) {
            canvasWidth = width;
            canvasHeight = height;
            tileSize = Math.min(width, height) / (float)(Board.SIZE);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning = false;
        boolean isStopping = true;
        while (isStopping) {
            try {
                thread.join();
                isStopping = false;
            }
            catch (InterruptedException e) {
            }
        }
    }

    // Runnable overrides

    @Override
    public void run() {
        while (isRunning) {
            // Draw the frame
            SurfaceHolder surfaceHolder = getHolder();
            Canvas canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                drawBoard(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);

            // Max 25 frames/sec
            try {
                Thread.sleep(40);
            }
            catch (InterruptedException e) {
            }
        }
    }

    // Drawing methods

    private void drawBoard(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.BLACK);

        Iterator<BoardHole> holeIter = board.getBoardHoleIterator();
        while (holeIter.hasNext()) {
            BoardHole boardHole = holeIter.next();
            drawBoardHole(canvas, boardHole);

            PlayerPiece playerPiece = boardHole.getPlayerPiece();
            if (playerPiece != null)
                drawPlayerPiece(canvas, playerPiece);
        }
    }

    private void drawBoardHole(Canvas canvas, BoardHole boardHole) {
        float x = getX(boardHole.getRow(), boardHole.getCol()) + (tileSize * 0.5f);
        float y = getY(boardHole.getRow(), boardHole.getCol()) + (tileSize * 0.5f);
        float outerR = tileSize * 0.5f;
        float innerR = tileSize * 0.4f;

//        if (boardHole.getPlayerColor() != null) {
//            x += getNoise();
//            y += getNoise();
//        }

        paint.setColor(Color.WHITE);
        if (boardHole.getPlayerColor() != null)
            paint.setColor(getColor(boardHole.getPlayerColor()));
        canvas.drawCircle(x, y, outerR, paint);

        paint.setColor(Color.BLACK);
        canvas.drawCircle(x, y, innerR, paint);
    }

    private void drawPlayerPiece(Canvas canvas, PlayerPiece playerPiece) {
        float x = getX(playerPiece.getBoardHole().getRow(), playerPiece.getBoardHole().getCol()) + (tileSize * 0.5f);
        float y = getY(playerPiece.getBoardHole().getRow(), playerPiece.getBoardHole().getCol()) + (tileSize * 0.5f);
        float r = tileSize * 0.3f;

//        x += getNoise();
//        y += getNoise();

        paint.setColor(getColor(playerPiece.getPlayer().getPlayerColor()));
        canvas.drawCircle(x, y, r, paint);
    }

    // Utility methods

    private float getX(int row, int col) {
        int mid = (Board.SIZE - 1) / 2;
        return getRawX(row, col) - getRawX(mid, mid) - (tileSize / 2) + (canvasWidth / 2);
    }

    private float getY(int row, int col) {
        int mid = (Board.SIZE - 1) / 2;
        return getRawY(row, col) - getRawY(mid, mid) - (tileSize / 2) + (canvasHeight / 2);
    }

    private float getRawX(int row, int col) {
        return tileSize * (col + (row * 0.5f)) / (float)(Math.sqrt(0.75));
    }

    private float getRawY(int row, int col) {
        return tileSize * row;
    }

//    private float getNoise() {
//        return (random.nextBoolean() ? 1 : -1) * random.nextFloat() * tileSize / 8.0f;
//    }

    private int getColor(PlayerColor playerColor) {
        switch (playerColor) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            case MAGENTA:
                return Color.MAGENTA;
            case YELLOW:
                return Color.YELLOW;
            case CYAN:
                return Color.CYAN;
        }

        return Color.TRANSPARENT;
    }
}

package com.loban.chinesecheckers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.loban.android.view.MoveGestureDetector;
import com.loban.android.view.RotateGestureDetector;

import com.loban.chinesecheckers.enums.PlayerColor;
import com.loban.chinesecheckers.model.Board;
import com.loban.chinesecheckers.model.BoardHole;
import com.loban.chinesecheckers.model.Game;
import com.loban.chinesecheckers.model.PlayerPiece;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by loban on 5/26/13.
 * TODO: Use inner classes for gestures, threads, and others
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback,
                                                      View.OnTouchListener,
                                                      MoveGestureDetector.OnMoveGestureListener,
                                                      ScaleGestureDetector.OnScaleGestureListener,
                                                      RotateGestureDetector.OnRotateGestureListener,
                                                      Runnable
{
    private float mWidth;
    private float mHeight;

    private float mTile;

    private float mScale;
    private float mRotate;
    private float mTranslateX;
    private float mTranslateY;

    private MoveGestureDetector mMoveGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private RotateGestureDetector mRotateGestureDetector;

    private Thread mThread;
    private boolean mIsRunning;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Random mRandom = new Random();

    private Game mGame;

    public GameView(Context context, Game game) {
        super(context);

        getHolder().addCallback(this);
        setOnTouchListener(this);

        mMoveGestureDetector = new MoveGestureDetector(context, this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mRotateGestureDetector = new RotateGestureDetector(context, this);

        mGame = game;
    }

    // SurfaceHolder.Callback overrides

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mScale = 1f;
        mRotate = 0f;

        mIsRunning = true;
        mThread = new Thread(this);
        mThread.start();

        Log.d("scooby", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        synchronized (surfaceHolder) {
            mWidth = width;
            mHeight = height;

            mTile = (Math.min(mWidth, mHeight) / (float)(Board.SIZE)) * mScale;

            mTranslateX = (mWidth / 2) - (mTile / 2);
            mTranslateY = (mHeight / 2) - (mTile / 2);
        }

        Log.d("scooby", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsRunning = false;
        boolean isStopping = true;
        while (isStopping) {
            try {
                mThread.join();
                isStopping = false;
            }
            catch (InterruptedException e) {
            }
        }

        Log.d("scooby", "surfaceDestroyed");
    }

    // View.OnTouchListener overrides

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("scooby", "onTouch");

        mMoveGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        mRotateGestureDetector.onTouchEvent(event);

        return true;
    }

    // MoveGestureDetector.OnMoveGestureListener overrides

    @Override
    public boolean onMove(MoveGestureDetector detector) {
        mTranslateX += detector.getFocusDelta().x;
        mTranslateY += detector.getFocusDelta().y;

        float maxX = mTile * Board.SIZE * mScale / 2;
        float maxY = mTile * Board.SIZE * mScale / 2;

        mTranslateX = Math.min(mTranslateX, maxX);
        mTranslateY = Math.min(mTranslateY, maxY);

        Log.d("scooby", "onMove " + mTranslateX + ", " + mTranslateY);

        return true;
    }

    @Override
    public boolean onMoveBegin(MoveGestureDetector detector) {
        return true;
    }

    @Override
    public void onMoveEnd(MoveGestureDetector detector) {
    }

    // ScaleGestureDetector.OnScaleGestureListener overrides

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mScale *= detector.getScaleFactor();
        mScale = Math.max(0.1f, Math.min(mScale, 5.0f));

        Log.d("scooby", "onScale " + detector.getScaleFactor() + " -> " + mScale);

        mTile = (Math.min(mWidth, mHeight) / (float)(Board.SIZE)) * mScale;

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    // RotateGestureDetector.OnRotateGestureListener overrides

    @Override
    public boolean onRotate(RotateGestureDetector detector) {
        mRotate -= detector.getRotationDegreesDelta() * Math.PI / 180;

        Log.d("scooby", "onRotate " + detector.getRotationDegreesDelta() + " -> " + mRotate);

        return true;
    }

    @Override
    public boolean onRotateBegin(RotateGestureDetector detector) {
        return true;
    }

    @Override
    public void onRotateEnd(RotateGestureDetector detector) {
    }

    // Runnable overrides

    @Override
    public void run() {
        while (mIsRunning) {
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
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.BLACK);

        Iterator<BoardHole> holeIter = mGame.getBoard().getBoardHoleIterator();
        while (holeIter.hasNext()) {
            BoardHole boardHole = holeIter.next();
            PlayerPiece playerPiece = boardHole.getPlayerPiece();

            drawBoardHole(canvas, boardHole);
            if (playerPiece != null)
                drawPlayerPiece(canvas, playerPiece);
        }
    }

    private void drawBoardHole(Canvas canvas, BoardHole boardHole) {
        float x = getX(boardHole.getRow(), boardHole.getCol()) + (mTile * 0.5f);
        float y = getY(boardHole.getRow(), boardHole.getCol()) + (mTile * 0.5f);
        float outerR = mTile * 0.5f;
        float innerR = mTile * 0.4f;

//        if (boardHole.getPlayerColor() != null) {
//            x += getNoise();
//            y += getNoise();
//        }

        mPaint.setColor(Color.WHITE);
        if (boardHole.getPlayerColor() != null)
            mPaint.setColor(getColor(boardHole.getPlayerColor()));
        canvas.drawCircle(x, y, outerR, mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(x, y, innerR, mPaint);
    }

    private void drawPlayerPiece(Canvas canvas, PlayerPiece playerPiece) {
        float x = getX(playerPiece.getBoardHole().getRow(), playerPiece.getBoardHole().getCol()) + (mTile * 0.5f);
        float y = getY(playerPiece.getBoardHole().getRow(), playerPiece.getBoardHole().getCol()) + (mTile * 0.5f);
        float r = mTile * 0.3f;

//        x += getNoise();
//        y += getNoise();

        mPaint.setColor(getColor(playerPiece.getPlayer().getPlayerColor()));
        canvas.drawCircle(x, y, r, mPaint);
    }

    // Utility methods

    private float getX(int row, int col) {
        int mid = (Board.SIZE - 1) / 2;

        double x = getRawX(row, col) - getRawX(mid, mid);
        double y = getRawY(row, col) - getRawY(mid, mid);

        double s = Math.sin(mRotate);
        double c = Math.cos(mRotate);

        x = (x * c) - (y * s) + mTranslateX;

        return (float)x;
    }

    private float getY(int row, int col) {
        int mid = (Board.SIZE - 1) / 2;

        double x = getRawX(row, col) - getRawX(mid, mid);
        double y = getRawY(row, col) - getRawY(mid, mid);

        double s = Math.sin(mRotate);
        double c = Math.cos(mRotate);

        y = (x * s) + (y * c) + mTranslateY;

        return (float)y;
    }

    private float getRawX(int row, int col) {
        return mTile * (col + (row * 0.5f)) / (float)(Math.sqrt(0.75));
    }

    private float getRawY(int row, int col) {
        return mTile * row;
    }

    private float getNoise() {
        return (mRandom.nextBoolean() ? 1 : -1) * mRandom.nextFloat() * mTile / 8.0f;
    }

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

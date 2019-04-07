package com.example.chris.assignment03_csci_4020;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class EditImage extends View {
    private static final int BALL_SIZE_DPI = 50;
    private static final int BALL_SIZE_STEP = 5;

    private int currentWidth;
    private int currentHeight;

    private Paint backgroundPaint;
    private Paint ballPaint;

    private AnimateThread animateThread;
    private ArrayList<Ball> balls;

    private Random random;

    private Bitmap smileBitmap;

    public EditImage(Context context) {
        super(context);
        setup(null);
    }

    public EditImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public EditImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xffffcccc);
        backgroundPaint.setStyle(Paint.Style.FILL);

        ballPaint = new Paint();
        ballPaint.setStyle(Paint.Style.FILL);

        random = new Random();
        balls = new ArrayList<>();

        loadBitmap();

    }

    private void loadBitmap() {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int sizePixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BALL_SIZE_DPI, dm);
        smileBitmap = Bitmap.createBitmap(sizePixels, sizePixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(smileBitmap);

    }

    public void addBall() {
        Ball ball = new Ball(BALL_SIZE_DPI, BALL_SIZE_STEP);
        balls.add(ball);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(backgroundPaint);

        int size = balls.size();
        for (int i = 0; i < size; i++) {
            Ball ball = balls.get(i);

            // ----- Uncomment if you want to see the
            // ----- bouncing balls
            /*
            ballPaint.setColor(ball.color);

            canvas.drawOval(ball.x, ball.y,
                    ball.x + ball.sizePixels, ball.y + ball.sizePixels,
                    ballPaint);
            */

            // only needed if drawing the bitmap
            canvas.drawBitmap(smileBitmap, ball.x, ball.y, ballPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        currentWidth = w;
        currentHeight = h;
    }

    public void start() {
        if (animateThread == null) {
            animateThread = new AnimateThread();
            animateThread.start();
        }
    }

    public void stop() {
        if (animateThread != null) {
            animateThread.running = false;
            animateThread = null;
        }
    }

    private class AnimateThread extends Thread {
        boolean running = true;

        @Override
        public void run() {
            while (running) {
                int size = balls.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        Ball ball = balls.get(i);

                        ball.x += ball.xStep;
                        ball.y += ball.yStep;

                        if (ball.x < 0) {
                            ball.xStep = -ball.xStep;
                        } else if (ball.x + ball.sizePixels > currentWidth) {
                            ball.xStep = -ball.xStep;
                        }

                        if (ball.y < 0) {
                            ball.yStep = -ball.yStep;
                        } else if (ball.y + ball.sizePixels > currentHeight) {
                            ball.yStep = -ball.yStep;
                        }

                    }
                    postInvalidate();
                }
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    running = false;
                }
            }
        }
    }

    private class Ball {
        int x;
        int y;
        float sizePixels;
        float xStep;
        float yStep;
        int color;

        Ball(int ballSizeDpi, int stepDpi) {
            color = random.nextInt(0x1000000) + 0xff000000;

            DisplayMetrics dm = getResources().getDisplayMetrics();
            sizePixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ballSizeDpi, dm);

            xStep = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, stepDpi, dm);
            yStep = xStep;

            x = random.nextInt((int) (currentWidth - sizePixels));
            y = random.nextInt((int) (currentHeight - sizePixels));
        }
    }
}

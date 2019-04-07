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
    private Paint userPaint;

    private AnimateThread animateThread;
    private ArrayList<Pen> pens;

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

        userPaint = new Paint();
        userPaint.setStyle(Paint.Style.FILL);

        random = new Random();
        pens = new ArrayList<>();

        loadBitmap();

    }

    private void loadBitmap() {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int sizePixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BALL_SIZE_DPI, dm);
        smileBitmap = Bitmap.createBitmap(sizePixels, sizePixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(smileBitmap);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(backgroundPaint);

        int size = pens.size();
        for (int i = 0; i < size; i++) {
            Pen pen = pens.get(i);

            // ----- Uncomment if you want to see the
            // ----- bouncing pens
            /*
            userPaint.setColor(pen.color);

            canvas.drawOval(pen.x, pen.y,
                    pen.x + pen.sizePixels, pen.y + pen.sizePixels,
                    userPaint);
            */

            // only needed if drawing the bitmap
            canvas.drawBitmap(smileBitmap, pen.x, pen.y, userPaint);
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
                int size = pens.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        Pen pen = pens.get(i);

                        pen.x += pen.xStep;
                        pen.y += pen.yStep;

                        if (pen.x < 0) {
                            pen.xStep = -pen.xStep;
                        } else if (pen.x + pen.sizePixels > currentWidth) {
                            pen.xStep = -pen.xStep;
                        }

                        if (pen.y < 0) {
                            pen.yStep = -pen.yStep;
                        } else if (pen.y + pen.sizePixels > currentHeight) {
                            pen.yStep = -pen.yStep;
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

    private class Pen {
        int x;
        int y;
        float sizePixels;
        float xStep;
        float yStep;
        int color;

        Pen(int ballSizeDpi, int stepDpi) {
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

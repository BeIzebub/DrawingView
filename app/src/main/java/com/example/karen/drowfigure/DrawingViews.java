package com.example.karen.drowfigure;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingViews extends View {

    Params params = new Params();
    private PointF[] points = new PointF[params.numberOfPoints];
    private Path path = new Path();

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Boolean isCordTaked = false;
    private int i = 0;

    public DrawingViews(Context context) {
        super(context);
        init();
    }

    public DrawingViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs);
    }

    private void init() {
        initPaint();
    }

    private void initPaint() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(params.strokeWidth);
    }

    private void initWithAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DrawingViews);
        params.setNumberOfPoints(a.getInt(R.styleable.DrawingViews_numberOfPoints, 3));
        params.setStrokeWidth(a.getInt(R.styleable.DrawingViews_strokeWidth, 5));
        initWithParams();
        a.recycle();
    }

    private void initWithParams() {
        initPaint();
        points = new PointF[params.numberOfPoints];
    }

    public void setParams(Params params) {
        this.params = params;
        initWithParams();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if(isCordTaked) {
            paint.setColor(Color.rgb((int)(Math.random()*254+1),(int)(Math.random()*254+1),(int)(Math.random()*254+1)));
            path.moveTo(points[0].x, points[0].y);
            for (int k = 1; k < points.length; k++) {
                path.lineTo(points[k].x, points[k].y);
//                canvas.drawLine(points[k].x, points[k].y, points[(k+1) % points.length].x, points[(k+1) % points.length].y, paint);
            }
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
            isCordTaked = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isCordTaked = false;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                points[i] = new PointF(event.getX(), event.getY());
                i++;
                if(i > params.numberOfPoints - 1) {
                    isCordTaked = true;
                    i = 0;
                    invalidate();
                    return true;
                }
                break;
        }
        invalidate();
        return true;
    }

    public static class Params {

        private int numberOfPoints = 3;
        private int strokeWidth = 5;

        public int getNumberOfPoints() {
            return numberOfPoints;
        }

        public void setNumberOfPoints(int numberOfPoints) {
            this.numberOfPoints = numberOfPoints;
        }

        public int getStrokeWidth() {
            return strokeWidth;
        }

        public void setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
        }
    }
}

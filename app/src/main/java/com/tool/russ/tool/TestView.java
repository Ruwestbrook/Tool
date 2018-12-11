package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TestView  extends View {
    private static final String TAG = "TestView";
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
         Paint paint=new Paint();
        paint.setTextSize(32);
        paint.setColor(Color.RED);
        String text="这是一个测试";
        Rect rect=new Rect();

        paint.getTextBounds(text,0,text.length(),rect);
        Log.d(TAG, "onDraw: right="+rect.right);
        Log.d(TAG, "onDraw: left="+rect.left);
        Log.d(TAG, "onDraw: top="+rect.top);
        Log.d(TAG, "onDraw: bottom="+rect.bottom);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("这是一个测试",190,rect.bottom-rect.top,paint);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("这是一个测试",190,2*(rect.bottom-rect.top),paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("这是一个测试",190,3*(rect.bottom-rect.top),paint);
    }
}

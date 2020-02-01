package com.tool.russ.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tool.russ.view.R;

/**
 * author: russell
 * time: 2020-02-01:20:22
 * describe：宽度确定时  根据宽度自动调整字体大小  只有一行
 */
public class AutoSizeView extends View {

    public AutoSizeView(Context context) {
        this(context,null);
    }

    public AutoSizeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public String mText;
    public int mColor;
    public Paint mPaint;
    public Rect mRect;

    public int normalWidth;
    public int normalHeight;
    public int gravityType;
    public int heightType;

    public boolean isSize=false;


    public AutoSizeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array =context.obtainStyledAttributes(attrs, R.styleable.AutoSizeView);

        mText=array.getString(R.styleable.AutoSizeView_text);
        mColor=array.getColor(R.styleable.AutoSizeView_textColor,Color.parseColor("#333333"));
        gravityType=array.getInt(R.styleable.AutoSizeView_gravity,0);
        array.recycle();

        mPaint=new Paint();
        mPaint.setColor(mColor);
        mRect=new Rect();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        normalWidth=MeasureSpec.getSize(widthMeasureSpec);
        getTextSize();
        int heightType=MeasureSpec.getMode(heightMeasureSpec);

        if(heightType==MeasureSpec.AT_MOST || heightType==MeasureSpec.UNSPECIFIED){
            normalHeight=mRect.height();
        }else {
            normalHeight=MeasureSpec.getSize(heightMeasureSpec);
        }

        setMeasuredDimension(normalWidth,normalHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(heightType==MeasureSpec.AT_MOST || heightType==MeasureSpec.UNSPECIFIED){

            switch (gravityType){
                case 1:
                    canvas.drawText(mText,0,mRect.height(),mPaint);
                    break;
                case 2:
                    canvas.drawText(mText,0,normalHeight,mPaint);
                    break;
                default:
                    canvas.drawText(mText,0,(normalHeight+mRect.height())/2,mPaint);

            }

        }else {
            canvas.drawText(mText,0,getMeasuredHeight(),mPaint);
        }



    }

    private static final String TAG = "AutoSizeView";

    private  void  getTextSize(){

        if(isSize){
            return;
        }
        isSize=true;
        int size=(normalWidth/mText.length())-1;
        Log.d(TAG, "getTextSize: 初始值"+size+"px");
        while (mRect.width()<normalWidth){
            size++;
            mPaint.setTextSize(size);
            mPaint.getTextBounds(mText,0,mText.length(),mRect);
        }

       mPaint.setTextSize(size-1);

        Log.d(TAG, "getTextSize: size="+size+"px");
        Log.d(TAG, "getTextSize: "+mRect.height());
        Log.d(TAG, "getTextSize: "+mRect.width());

    }
}

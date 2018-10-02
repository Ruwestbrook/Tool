package com.tool.russ.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class RefreshView extends RecyclerView {
    public RefreshView(Context context) {
        super(context);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {

        ViewGroup.MarginLayoutParams layoutParams= (MarginLayoutParams) getLayoutParams();
        layoutParams.topMargin=-150;
        setLayoutParams(layoutParams);
        super.onMeasure(widthSpec, heightSpec);
    }

    private float downY=0;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_UP:
                //抬起
                ViewGroup.MarginLayoutParams layoutParams1= (MarginLayoutParams) getLayoutParams();
                layoutParams1.topMargin= -150;
                setLayoutParams(layoutParams1);
                break;
            case MotionEvent.ACTION_DOWN:
                //按下
                downY=e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                float y=e.getY();
                ViewGroup.MarginLayoutParams layoutParams= (MarginLayoutParams) getLayoutParams();
                layoutParams.topMargin= (int) (-150+y-downY);
                setLayoutParams(layoutParams);
        }


        return super.onTouchEvent(e);
    }
}

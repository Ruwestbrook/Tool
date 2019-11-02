package com.tool.russ.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class FlowLayout extends LinearLayout {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int count=getChildCount();
        int width=0;
        int height=0;
        int lineHeight=0;
        for (int i = 0; i < count; i++) {
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if(childWidth+width>measureWidth){
                //需要换行
                height+=lineHeight;
                lineHeight=childHeight;
                width=childWidth;
            }else {
                width+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }
            if(i==count-1)
                height+=lineHeight;
        }
        setMeasuredDimension(measureWidth,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int count=getChildCount();
        int left=0,top=0,height=0;
        for (int i = 0; i < count; i++) {
            View child=getChildAt(i);
            LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if(left+childWidth>=getMeasuredWidth()){
                left=0;
                top+=height;
                height=0;
            }else {
                height=Math.max(height,childHeight);
            }
            child.layout(left+layoutParams.leftMargin,top+layoutParams.topMargin,left+child.getMeasuredWidth()+layoutParams.leftMargin,top+child.getMeasuredHeight()+layoutParams.topMargin);
            left+=childWidth;
        }
    }
}

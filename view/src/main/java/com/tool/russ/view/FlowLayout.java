package com.tool.russ.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

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
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return  new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureWidthMode=MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
        int measureHeightMode=MeasureSpec.getMode(heightMeasureSpec);
        int count=getChildCount();
        int lineWidth=0;
        int lineHeight=0;
        int height=0;
        int width=0;
        for (int i = 0; i < count; i++) {
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams layoutParams= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if(lineWidth+childWidth>measureWidth){
                //需要换行
                width=Math.max(lineWidth,childWidth);
                height+=lineHeight;
                lineHeight=childHeight;
                lineWidth=childWidth;
            }else {
                lineHeight=Math.max(lineHeight,childHeight);
                lineWidth+=childWidth;
            }
            if(i==count-1){
                height+=lineHeight;
                width=Math.max(width,lineWidth);
            }
        }
        setMeasuredDimension((measureWidthMode==MeasureSpec.EXACTLY?measureWidth:width),
                (measureHeightMode==MeasureSpec.EXACTLY?measureHeight:height));
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int count=getChildCount();
        int lineWidth=0;
        int lineHeight=0;
        int top=0,left=0;
        for (int k = 0; k < count; k++) {
            View child=getChildAt(k);

            MarginLayoutParams layoutParams= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if(lineWidth+childWidth>getMeasuredWidth()){
                top+=lineHeight;
                left=0;
                lineHeight=childHeight;
                lineWidth=childWidth;
            }else {
                lineHeight=Math.max(lineHeight,childHeight);
                lineWidth+=childWidth;
            }

            int lc=left+layoutParams.leftMargin;
            int tc=top+layoutParams.topMargin;
            int rc=lc+child.getMeasuredWidth();
            int bc=top+child.getMeasuredHeight();
            child.layout(lc,tc,rc,bc);
            left+=childWidth;
        }
    }
}

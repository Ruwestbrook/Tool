package com.tool.russ.view.custom.barrage;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: russell
 * time: 2020-02-01:19:49
 * describe：弹幕控件
 */


/*
    需求分析:文字滑动 文字可以设置大小,颜色,滚动速率
 */

public class BarrageView extends ViewGroup {
    int widthMeasureSpec;
    int heightMeasureSpec;
    List<BarrageItem> mItems;
    public BarrageView(Context context) {
        this(context,null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItems=new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private static final String TAG = "BarrageView";

   public void addBarrage(BarrageItem item){


       if(widthMeasureSpec==0 || heightMeasureSpec==0){
           mItems.add(item);
       }else {
           addTextView(item);
       }




   }

    private void addTextView(BarrageItem item) {
        final TextView textView=new TextView(getContext());
        textView.setTextColor(item.getColor());
        textView.setText(item.getText());
        textView.setTextSize(item.getSize());
        super.addView(textView,generateDefaultLayoutParams());
        Log.d(TAG, "addBarrage: widthMeasureSpec="+widthMeasureSpec);
        Log.d(TAG, "addBarrage: heightMeasureSpec="+heightMeasureSpec);
        measureChild(textView,widthMeasureSpec,heightMeasureSpec);
        BarrageLayoutParams layoutParams= (BarrageLayoutParams) textView.getLayoutParams();

        // layoutParams.leftMargin=MeasureSpec.getSize(widthMeasureSpec);
        textView.layout(100,100,100+textView.getMeasuredWidth(),100+textView.getMeasuredHeight());

//       ValueAnimator animator=ValueAnimator.ofInt(MeasureSpec.getSize(widthMeasureSpec),-layoutParams.width);
//
//       animator.addListener(new Animator.AnimatorListener() {
//           @Override
//           public void onAnimationStart(Animator animation) {
//
//
//           }
//
//           @Override
//           public void onAnimationEnd(Animator animation) {
//                    BarrageView.super.removeView(textView);
//           }
//
//           @Override
//           public void onAnimationCancel(Animator animation) {
//
//           }
//
//           @Override
//           public void onAnimationRepeat(Animator animation) {
//
//           }
//       });
    }



    public  void addBarrages(List<BarrageItem> itemList){
        for (BarrageItem item:
                itemList) {
            addBarrage(item);
        }

    }


    public void addBarrage(BarrageItem[] items){

        for (BarrageItem item:
                items) {
            addBarrage(item);
        }
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(this.widthMeasureSpec==0 || this.heightMeasureSpec==0){
            this.widthMeasureSpec=widthMeasureSpec;
            this.heightMeasureSpec=heightMeasureSpec;
            Log.d(TAG, "onMeasure: widthMeasureSpec="+widthMeasureSpec);
            Log.d(TAG, "onMeasure: heightMeasureSpec="+heightMeasureSpec);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            for (BarrageItem item:
                    mItems) {
                addTextView(item);
            }
        }

    }

    @Override
    public void addView(View child, int index) {
       // super.addView(child, index);
    }

    @Override
    public void addView(View child) {
        //super.addView(child);
    }


    @Override
    public void addView(View child, int width, int height) {
        //super.addView(child, width, height);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        //super.addView(child, params);
    }


    @Override
    public void addView(View child, int index, LayoutParams params) {
        //super.addView(child, index, params);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new BarrageLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new BarrageLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BarrageLayoutParams(getContext(),attrs);
    }
    public class BarrageLayoutParams extends MarginLayoutParams{

        public BarrageLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public BarrageLayoutParams(int width, int height) {
            super(width, height);
        }

        public BarrageLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public BarrageLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}

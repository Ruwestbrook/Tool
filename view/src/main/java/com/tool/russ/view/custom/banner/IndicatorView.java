package com.tool.russ.view.custom.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.tool.russ.view.Tools.DisplayUtil;

/**
 * author: russell
 * time: 2020/5/21:20:40
 * describe：banner 指示器  自定义view 方便之后添加动画
 */
@SuppressLint("ViewConstructor")
class IndicatorView extends View {

    //banner数量
    int size;

    int nowPosition=0;

    int spacing;

    int indicatorWidth=0;

    int indicatorHeight=0;

    int normalColor;

    int chooseColor;

    Paint mPaint;


    public IndicatorView(Context context,int size) {
        super(context);
        this.size = size;
        spacing= DisplayUtil.dp2Px(4);
        indicatorWidth=DisplayUtil.dp2Px(6);
        indicatorHeight=DisplayUtil.dp2Px(6);

        normalColor= Color.RED;


        chooseColor=Color.BLUE;

        mPaint=new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=indicatorWidth*size+spacing*(size-1);
        setMeasuredDimension(width,indicatorHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        for (int i = 0; i < size; i++) {
            int width=(spacing+indicatorWidth)*i;
            if(i==nowPosition){
                mPaint.setColor(chooseColor);
            }else {
                mPaint.setColor(normalColor);
            }

            canvas.drawCircle((float) (width+indicatorWidth/2), indicatorWidth >> 1, indicatorHeight >> 1,mPaint);

        }


    }

    public void setSize(int size) {
        this.size = size;
    }


    public void setNowPosition(int position){

        nowPosition=position;
        invalidate();

    }


}

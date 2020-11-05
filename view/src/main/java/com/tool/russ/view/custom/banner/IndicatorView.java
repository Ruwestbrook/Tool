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

    int indicatorSize=0;

    public void setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;

    }

    public void setType(int type) {
        this.type = type;
    }

    int type=0;

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }



    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setChooseColor(int chooseColor) {
        this.chooseColor = chooseColor;
    }


    int normalColor;

    int chooseColor;

    Paint mPaint;

    public  IndicatorView(Context context){
        super(context);
    }

    public IndicatorView(Context context,int size) {
        super(context);
        this.size = size;
        spacing= DisplayUtil.dp2Px(4);
        normalColor=Color.WHITE;
        chooseColor=Color.RED;
        indicatorSize=DisplayUtil.dp2Px(6);
        mPaint=new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=indicatorSize*size+spacing*(size-1);
        setMeasuredDimension(width,indicatorSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        for (int i = 0; i < size; i++) {
            int width=(spacing+indicatorSize)*i;
            if(i==nowPosition){
                mPaint.setColor(chooseColor);
            }else {
                mPaint.setColor(normalColor);
            }
            if(type==0){
                canvas.drawCircle((float) (width+indicatorSize/2), indicatorSize >> 1, indicatorSize >> 1,mPaint);
            }else {
                canvas.drawRect(width,0,width+indicatorSize,indicatorSize,mPaint);
            }


        }


    }

    public void setSize(int size) {
        this.size = size;
        invalidate();
    }


    public void setNowPosition(int position){

        nowPosition=position;
        invalidate();

    }


}

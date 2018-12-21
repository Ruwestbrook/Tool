package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DataView extends View {
    private final Paint mPaint;
    private List<Item> mItemList;
    private int[] priceStep;
    public DataView(Context context) {
        this(context,null);
    }

    public DataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemList=new ArrayList<>();
        mPaint=new Paint();
        for (int i = 0; i < 4; i++) {
            Item item=new Item();
            mItemList.add(item);
            item.setIssue("2018121700"+i+"轮");
            initView();
        }
        priceStep=new int[]{1000,1200,1400,1600,1800};
    }

    private void initView() {

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        //可以画图的宽度
        int width=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
        //可以画图高度
        int height=getMeasuredHeight()-getPaddingBottom()-getPaddingTop();
        //横坐标的和纵坐标的预留值画值
        int coorX=150,coorY=100;
        //平均分的宽度
        int step=(width-coorY-mItemList.size()*100)/(mItemList.size()+1);
        int a=step;


        for (int i = 0; i < mItemList.size(); i++) {
            canvas.save();
            Item item=mItemList.get(i);
            TextPaint tp = new TextPaint();
            tp.setColor(Color.BLUE);
            tp.setStyle(Paint.Style.FILL);
            tp.setTextSize(26);
            StaticLayout layout=new StaticLayout(item.getIssue(),tp,100,Layout.Alignment.ALIGN_CENTER,1,1,false);
            //canvas.drawText(item.getIssue(),coorX+a,height-100,new Paint());
            canvas.translate(coorX+a,height-100);
            layout.draw(canvas);
            canvas.drawLine(50,50-height,50,-50,mPaint);
            a+=(step+100);
            canvas.restore();
        }
        mPaint.setTextSize(26);
        String text=priceStep[0]+"";
        Rect rect=new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        int textWidth=rect.right-rect.left;
        int textHeight=rect.bottom-rect.top;


        step=(height-coorX-priceStep.length*textHeight)/(priceStep.length-1);
        a=0;
        canvas.translate(0,height-150);
        int x=(100-textWidth)/2;
        for (int i = 0; i < priceStep.length; i++) {
            canvas.drawText(priceStep[i]+"",x,a,mPaint);
            canvas.drawLine(100,a-textHeight/2,width,a-textHeight/2,mPaint);
            a-=(step+textHeight);
        }
        
        
    }
}

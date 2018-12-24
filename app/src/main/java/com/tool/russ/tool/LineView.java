package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {
    /*
        yAxisLineNum = 0; //y轴刻度数
         yAxisMax = 0; //y轴最大值
         yAxisBaseNum = 0; //y轴基准值
     */

    double yAxisLineNum,yAxisBaseNum,yAxisMax;
    Paint mPaint;

    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        double[] data=new double[]{1200.0005,2000.562,52.12,315.95,15,6.95,1562.9658};
        draw(data);
        mPaint=new Paint();
        mPaint.setTextSize(26);
        mPaint.setTextAlign(Paint.Align.RIGHT);
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //可绘制的宽度左右留出100空白  右边80用于绘制坐标值
        int width=getMeasuredWidth()-getPaddingRight()-getPaddingLeft()-100;
        //可绘制的高度 上下留出20空白
        int height=getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-40;

        Rect rect=new Rect();
        String s=String.valueOf(yAxisMax);
        mPaint.getTextBounds(s,0,s.length(),rect);
        int textHeight=Math.abs(rect.bottom-rect.top);
        int textWidth=Math.abs(rect.left-rect.right);
        double stepY=(height-textHeight*yAxisLineNum)/yAxisLineNum;

        int startY=0;

        canvas.translate(100,getMeasuredHeight()-20);

        for (int i = 0; i <= yAxisLineNum; i++) {
            String text=subZeroAndDot(String.valueOf(yAxisBaseNum*i));
            canvas.drawText(text,0, startY,mPaint);
            startY-=stepY+textHeight;
        }
    }


      String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    void draw(double[] dataArray){
        //int[] dataArray = new int[10];//[1200.0005,2000.562,52.12,315.95,15,6.95,1562.9658]
        double dataMax = 0;  //最大数值
        double dataMaxR = 0; //替代最大数值用来获得位数
         yAxisLineNum = 0; //y轴刻度数
         yAxisMax = 0; //y轴最大值

         yAxisBaseNum = 0; //y轴基准值
        double yAxisMaxNum = 0; //y轴最大值个数位
        boolean isDecimal = false;
        int figure = 0; //最大数值位数
        // 获得最大值
        for (int i = 0; i < dataArray.length; i++) {
            if (dataMax < dataArray[i]) {
                dataMax = dataArray[i];
            }
        }
        dataMaxR = dataMax;
        if (dataMaxR >= 1) {//最大值大于1的情况
            while (dataMaxR >= 1) {
                dataMaxR = dataMaxR / 10;
                figure++;
            }
        } else {//最大值小于1的情况
            isDecimal = true;
            dataMax = Math.round(dataMax * Math.pow(10, 4));
            dataMaxR = dataMax;
            while (dataMaxR >= 1) {
                dataMaxR = dataMaxR / 10;
                figure++;
            }
        }
        double bit = dataMax / Math.pow(10, figure - 1);//除到个位数
        int pointPlace = String.valueOf(bit).indexOf(".") + 1;//获得小数点位置
        if (bit == 0) {
            yAxisMaxNum = 1;
            yAxisLineNum = 5;
        }else if (bit == 1) {
            yAxisMaxNum = 1;
            yAxisLineNum = 5;
        } else if (bit > 1 && bit <= 1.2) {
            yAxisMaxNum = 1.2;
            yAxisLineNum = 6;
        } else if (bit > 1.2 && bit <= 1.4) {
            yAxisMaxNum = 1.4;
            yAxisLineNum = 7;
        } else if (bit > 1.4 && bit <= 1.5) {
            yAxisMaxNum = 1.5;
            yAxisLineNum = 5;
        } else if (bit > 1.5 && bit <= 1.8) {
            yAxisMaxNum = 1.8;
            yAxisLineNum = 6;
        } else if (bit > 1.8 && bit <= 2) {
            yAxisMaxNum = 2;
            yAxisLineNum = 4;
        } else if (bit > 2 && bit <= 2.5) {
            yAxisMaxNum = 2.5;
            yAxisLineNum = 5;
        } else if (bit > 2.5 && bit <= 3) {
            yAxisMaxNum = 3;
            yAxisLineNum = 6;
        } else if (bit > 3 && bit <= 3.5) {
            yAxisMaxNum = 3.5;
            yAxisLineNum = 7;
        } else if (bit > 3.5 && bit <= 4) {
            yAxisMaxNum = 4;
            yAxisLineNum = 4;
        } else if (bit > 4 && bit <= 5) {
            yAxisMaxNum = 5;
            yAxisLineNum = 5;
        } else if (bit > 5 && bit <= 6) {
            yAxisMaxNum = 6;
            yAxisLineNum = 6;
        } else if (bit > 6 && bit <= 7) {
            yAxisMaxNum = 7;
            yAxisLineNum = 7;
        } else if (bit > 7 && bit <= 8) {
            yAxisMaxNum = 8;
            yAxisLineNum = 4;
        } else if (bit > 8) {
            yAxisMaxNum = 10;
            yAxisLineNum = 5;
        }


        // 看y轴最大值是否小于1
        if (isDecimal) {//小于1的情况
            yAxisMax = yAxisMaxNum * Math.pow(10, figure - 1) / Math.pow(10, 4);//获得y轴最大值
            yAxisBaseNum = Math.round(yAxisMax / yAxisLineNum * Math.pow(10, 5)) / Math.pow(10, 5);//获得y轴基准值
        } else {//大于1的情况
            yAxisMax = yAxisMaxNum * Math.pow(10, figure - 1);//获得y轴最大值
            yAxisBaseNum = yAxisMax / yAxisLineNum;//获得y轴基准值
        }
        //yAxisLineNum = 0; //y轴刻度数
        //  yAxisBaseNum = 0; //y轴基准值
        //yAxisMax最大值
        if (bit == 0) {
            yAxisMax = 1;
            yAxisBaseNum = 0.2;
        }
    }
}

package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataView extends View {
    private static final String TAG = "DataView";
    private final Paint mPaint;
    private List<Item > mItemList;
    private int[] priceStep;
    private int[] prices;
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
            item.setPrice((int) (1000+(Math.random()*800)));
            Log.d(TAG, "DataView: "+item.getPrice());
            initView();
        }
        priceStep=new int[]{1000,1200,1400,1600,1800};
        prices=new int[]{1000,1200,1400,1600,1700};
    }

    private void initView() {

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);

        canvas.translate(0,50);

        //可以画图的宽度
        int width=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
        //可以画图高度
        int height=getMeasuredHeight()-getPaddingBottom()-getPaddingTop();
        //横坐标的和纵坐标的预留值画值
        int coorX=150,coorY=100;


        //纵坐标的高度
        mPaint.setTextSize(26);
        String text=priceStep[0]+"";
        Rect rect=new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        int textWidth=Math.abs(rect.right-rect.left);
        int textHeight=Math.abs(rect.top-rect.bottom);


        canvas.save();
        int step=(height-coorX-priceStep.length*textHeight)/(priceStep.length-1);
        int a=0;
        int drawHeight=0;
        canvas.translate(0,height-150);
        int x=(100-textWidth)/2;
        for (int aPriceStep : priceStep) {
            canvas.drawText(aPriceStep + "", x, a, mPaint);
            drawHeight=a - textHeight / 2;
            canvas.drawLine(100, drawHeight, width, drawHeight, mPaint);
            a -= (step + textHeight);
        }

        canvas.restore();

        //平均分的宽度
         step=(width-coorY-mItemList.size()*100)/(mItemList.size()+1);
         a=step;

         int totalDataHeight=Math.abs(-50-textHeight/2-drawHeight+50);

        mPaint.setTextAlign(Paint.Align.CENTER);

        for (int i = 0; i < mItemList.size(); i++) {
            canvas.save();
            Item item=mItemList.get(i);
            TextPaint tp = new TextPaint();
            tp.setColor(Color.BLUE);
            tp.setStyle(Paint.Style.FILL);
            tp.setTextSize(26);

            StaticLayout layout=new StaticLayout(item.getIssue(),tp,100,Layout.Alignment.ALIGN_CENTER,1,1,false);
            canvas.translate(coorX+a,height-120);
            layout.draw(canvas);
            canvas.translate(0,+20);

            canvas.drawLine(50,drawHeight-50,50,-50-textHeight/2,mPaint);
            Paint paint=new Paint();
            paint.setStrokeWidth(10);
            Log.d(TAG, "onDraw: 高度=");//
            //化点
            int lineHeight=totalDataHeight*(item.getPrice()-priceStep[0])/(priceStep[priceStep.length-1]-priceStep[0]);
            canvas.drawCircle(50, -50-textHeight/2-lineHeight,10, paint);

            //头像 价格
            Log.d(TAG, "onDraw: drawHeight="+drawHeight);
            if(lineHeight>(Math.abs(drawHeight)+textHeight/2)/2){
                //画下面
                canvas.drawText(item.getPrice()+"元",50, -50-textHeight/2-lineHeight+40, mPaint);

                //画头像
                Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
                BitmapShader shader=new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                canvas.translate(50, -50-textHeight/2-lineHeight+100);
                canvas.drawCircle(0,0,40,mPaint);

            }else {
                //画上面

                mPaint.getTextBounds(item.getPrice()+"元",0,(item.getPrice()+"元").length(),rect);
                int y=Math.abs(rect.top-rect.bottom);

                canvas.drawText(item.getPrice()+"元",50, -50-textHeight/2-lineHeight-40+y, mPaint);

                //画头像
                Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
                BitmapShader shader=new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                canvas.translate(50, -50-textHeight/2-lineHeight-100);
                canvas.drawCircle(0,0,40,mPaint);


            }
            a+=(step+100);
            canvas.restore();
        }

    }


    double[] draw(){
        int[] dataArray = new int[10];//[1200.0005,2000.562,52.12,315.95,15,6.95,1562.9658]
        long dataMax = 0;  //最大数值
        long dataMaxR = 0; //替代最大数值用来获得位数
        double yAxisLineNum = 0; //y轴刻度数
        double yAxisMax = 0; //y轴最大值
        double yAxisMaxNum = 0; //y轴最大值个数位
        double yAxisBaseNum = 0; //y轴基准值
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
        double[] x=new double[(int) yAxisLineNum+1];
        for (int i = 0; i <(int) yAxisLineNum+1; i++) {
            x[i]=i*yAxisBaseNum;
        }
        return x;
       // return new double[]{yAxisLineNum,yAxisBaseNum,yAxisMax};

    }
}

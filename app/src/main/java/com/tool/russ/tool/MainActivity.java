package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
// 图表设置

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
        return new double[]{yAxisLineNum,yAxisBaseNum,yAxisMax};
    }
}

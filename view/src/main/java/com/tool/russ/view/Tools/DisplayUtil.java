package com.tool.russ.view.Tools;

import android.content.Context;

import com.tool.russ.view.ToolView;

/**
 * author: russell
 * time: 2020-02-01:20:11
 * describe：屏幕工具类
 */
public final class DisplayUtil {


    /*
     dp 转 px
     */
    public static int  dp2Px(int dp){

        return dp2Px(ToolView.context,dp);
    }


    public static int  dp2Px(Context context,int dp){

        float scale= context.getResources().getDisplayMetrics().density;

        return (int) (dp*scale+0.5);
    }

    /*
     px 转 dp
     */
    public static  int px2Dp(int px){

        return px2Dp(ToolView.context,px);
    }

    public static  int px2Dp(Context context,int px){

        float scale= ToolView.context.getResources().getDisplayMetrics().density;

        return (int) (px/scale+0.5);
    }

    /*
     sp 转 px
     */
    public static int sp2Px(int sp){
        return sp2Px(ToolView.context,sp);
    }

    public static int sp2Px(Context context,int sp){
        float scale= ToolView.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp*scale+0.5);
    }


    /*
    px 转 sp
    */
    public static int px2Sp(int px){
        return px2Dp(ToolView.context,px);

    }

    public static int px2Sp(Context context,int px){
        float scale= ToolView.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px/scale+0.5);

    }

    /*
     屏幕高度
     */
    public static int screenHeight(){
        return screenHeight(ToolView.context);
    }

    public static int screenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /*
    屏幕宽度
     */
    public static int screenWidth(){
        return screenWidth(ToolView.context);
    }


    public static int screenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /*
    状态栏高度
     */
    public static  int statusBarHeight(){
        return  statusBarHeight(ToolView.context);
    }


    public static  int statusBarHeight(Context context){
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return  height;
    }
}

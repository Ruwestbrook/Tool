package com.tool.russ.view.Tools;

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

        float scale= ToolView.context.getResources().getDisplayMetrics().density;

        return (int) (dp*scale+0.5);
    }

    /*
     px 转 dp
     */
    public static  int px2Dp(int px){

        float scale= ToolView.context.getResources().getDisplayMetrics().density;

        return (int) (px/scale+0.5);
    }

    /*
     sp 转 px
     */
    public static int sp2Px(int sp){
        float scale= ToolView.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp*scale+0.5);

    }


    /*
    px 转 sp
    */
    public static int px2Px(int px){
        float scale= ToolView.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px/scale+0.5);

    }

    /*
     屏幕高度
     */
    public static int screenHeight(){
        return ToolView.context.getResources().getDisplayMetrics().heightPixels;
    }


    /*
    屏幕宽度
     */
    public static int screenWidthHeight(){
        return ToolView.context.getResources().getDisplayMetrics().widthPixels;
    }

    /*
    状态栏高度
     */
    public static  int StatusBarHeight(){
        int height = 0;
        int resourceId = ToolView.context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = ToolView.context.getResources().getDimensionPixelSize(resourceId);
        }
        return  height;
    }
}

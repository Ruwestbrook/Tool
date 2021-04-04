package com.tool.russ.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author russell
 * @description:
 * @date : 2021/1/17 11:33
 */
public class AnyWhereTip extends View {

    private Activity activity;

    //距离顶部高度
    private int pageTop;
    private Color bgColor;

    private GradientDrawable gradientDrawable;

    public AnyWhereTip(Context context) {
        super(context);
        if(context instanceof Activity){
            activity= (Activity) context;
            gradientDrawable=new GradientDrawable();


        }
    }

    public AnyWhereTip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnyWhereTip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

package com.tool.russ.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;


public class TxView extends android.support.v7.widget.AppCompatTextView {
    private GradientDrawable bgDrawable;
    private GradientDrawable selectBgDrawable;
    private GradientDrawable pressBgDrawable;
    private StateListDrawable mListDrawable;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TxView(Context context) {
        this(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TxView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bgDrawable=new GradientDrawable();
        selectBgDrawable=new GradientDrawable();
        pressBgDrawable=new GradientDrawable();
        mListDrawable=new StateListDrawable();
        @SuppressLint("Recycle")
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.TxView,defStyleAttr,0);
        float radius=array.getDimension(R.styleable.TxView_radius,0);
        float topLeftRadius=array.getDimension(R.styleable.TxView_TopLeftRadius,0);
        float topRightRadius=array.getDimension(R.styleable.TxView_TopRightRadius,0);

        float bottomLeftRadius=array.getDimension(R.styleable.TxView_BottomLeftRadius,0);
        float bottomRightRadius=array.getDimension(R.styleable.TxView_BottomRightRadius,0);
        int white=context.getResources().getColor(R.color.white);
        int startColor=array.getColor(R.styleable.TxView_startColor,white);
        int endColor=array.getColor(R.styleable.TxView_endColor,white);
        int type=array.getInteger(R.styleable.TxView_type,1);
        int backColor=array.getColor(R.styleable.TxView_backColor,white);
        if(startColor == white && endColor == white){
            bgDrawable.setColor(backColor);

        } else {
            GradientDrawable.Orientation flag=GradientDrawable.Orientation.TOP_BOTTOM;
            switch (type){
                case 1:
                    flag= GradientDrawable.Orientation.TOP_BOTTOM;
                    break;
                case 2:
                    flag= GradientDrawable.Orientation.BOTTOM_TOP;
                    break;
                case 3:
                    flag= GradientDrawable.Orientation.LEFT_RIGHT;
                    break;
                case 4:
                    flag= GradientDrawable.Orientation.RIGHT_LEFT;
                    break;
                case 5:
                    flag= GradientDrawable.Orientation.TL_BR;
                    break;
                case 6:
                    flag= GradientDrawable.Orientation.BR_TL;
                    break;
                case 7:
                    flag= GradientDrawable.Orientation.TR_BL;
                    break;
                case 8:
                    flag= GradientDrawable.Orientation.BL_TR;
                    break;
            }

            bgDrawable.setOrientation(flag);
            bgDrawable.setColors(new int[]{startColor,endColor});
            selectBgDrawable.setOrientation(flag);
            pressBgDrawable.setColors(new int[]{startColor,endColor});
            selectBgDrawable.setOrientation(flag);
            pressBgDrawable.setColors(new int[]{startColor,endColor});
        }

        int selectBgColor=array.getColor(R.styleable.TxView_selectBackColor,backColor);
        if(selectBgColor!=backColor){
            selectBgDrawable.setColor(selectBgColor);
        }
        int pressBgColor=array.getColor(R.styleable.TxView_pressBackColor,backColor);
        if(pressBgColor!=backColor){
            pressBgDrawable.setColor(pressBgColor);
        }
        bgDrawable.setStroke(40,Color.BLACK);
        pressBgDrawable.setStroke(40,Color.BLACK);
        selectBgDrawable.setStroke(40,Color.BLACK);
        if(radius>0){
            bgDrawable.setCornerRadius(radius);
            selectBgDrawable.setCornerRadius(radius);
            pressBgDrawable.setCornerRadius(radius);
        }

        if(bottomLeftRadius>0 || bottomRightRadius>0 || topLeftRadius>0|| topRightRadius>0){
            bgDrawable.setCornerRadii(new float[]{topLeftRadius,topLeftRadius,
                    topRightRadius,topRightRadius,
                    bottomRightRadius,bottomRightRadius,
                    bottomLeftRadius,bottomLeftRadius});
            selectBgDrawable.setCornerRadii(new float[]{topLeftRadius,topLeftRadius,
                    topRightRadius,topRightRadius,
                    bottomRightRadius,bottomRightRadius,
                    bottomLeftRadius,bottomLeftRadius});
            pressBgDrawable.setCornerRadii(new float[]{topLeftRadius,topLeftRadius,
                    topRightRadius,topRightRadius,
                    bottomRightRadius,bottomRightRadius,
                    bottomLeftRadius,bottomLeftRadius});
        }

       // setBackground(bgDrawable);
        setClickable(true);

        int pressed = android.R.attr.state_pressed;
        int selected = android.R.attr.state_selected;


        mListDrawable.addState(new int []{pressed},pressBgDrawable);
        mListDrawable.addState(new int []{selected},selectBgDrawable);
        mListDrawable.addState(new int []{},bgDrawable);
        setBackground(mListDrawable);
    }

   void setRadius(int radius){
       bgDrawable.setCornerRadius(radius);
       selectBgDrawable.setCornerRadius(radius);
       pressBgDrawable.setCornerRadius(radius);
    }

}

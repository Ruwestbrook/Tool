package com.tool.russ.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.tool.russ.view.R;


public class TxView extends android.support.v7.widget.AppCompatTextView {
    private GradientDrawable bgDrawable;
    private GradientDrawable selectBgDrawable;
    private GradientDrawable pressBgDrawable;
    private boolean isSelect;
    private boolean isPress;

    public TxView(Context context) {
        this(context,null);
    }

    public TxView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public TxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bgDrawable=new GradientDrawable();
        selectBgDrawable=new GradientDrawable();
        pressBgDrawable=new GradientDrawable();
        setIncludeFontPadding(true);
        @SuppressLint("Recycle")
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.TxView,defStyleAttr,0);
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

        //select为true的时候的背景颜色
        isSelect=array.getBoolean(R.styleable.TxView_select,false);
        if(isSelect){
            int selectBgColor=array.getColor(R.styleable.TxView_selectBackColor,backColor);
            selectBgDrawable.setColor(selectBgColor);
        }
        //按下的时候的背景颜色
        isPress=array.getBoolean(R.styleable.TxView_press,false);
        if(isPress){
            int pressBgColor=array.getColor(R.styleable.TxView_pressBackColor,backColor);
            pressBgDrawable.setColor(pressBgColor);
        }
        //设置圆角
        setRadius(radius);
        //边框
        int stockWidth=array.getDimensionPixelSize(R.styleable.TxView_stockWidth,0);
        float stockDashGap=array.getDimensionPixelSize(R.styleable.TxView_stockDashGap,0);
        float stockDashWidth=array.getDimensionPixelSize(R.styleable.TxView_stockDashWidth,0);

        //默认边框的颜色
        int stockColor=array.getColor(R.styleable.TxView_stockColor,backColor);
        if(stockWidth>0){
            bgDrawable.setStroke(stockWidth,stockColor,stockDashWidth,stockDashGap);
            //如果有select状态 设置select状态下的边框颜色
            if(isSelect){
                int selectStockColor=array.getColor(R.styleable.TxView_selectStockColor,stockColor);
                selectBgDrawable.setStroke(stockWidth,selectStockColor,stockDashWidth,stockDashGap);
            }
            //如果有press状态 设置press状态下的边框颜色
            if(isPress){
                int pressStockColor=array.getColor(R.styleable.TxView_pressStockColor,stockColor);
                pressBgDrawable.setStroke(stockWidth,pressStockColor,stockDashWidth,stockDashGap);
            }
        }


        if(bottomLeftRadius>0 || bottomRightRadius>0 || topLeftRadius>0|| topRightRadius>0){
            setRadius(bottomLeftRadius,bottomRightRadius,topLeftRadius,topRightRadius);
        }


        setClickable(true);
        StateListDrawable listDrawable = new StateListDrawable();
      //按下时的Drawable
        if(isPress)
        listDrawable.addState(new int []{android.R.attr.state_pressed},pressBgDrawable);
        //select时的Drawable
        if(isSelect)
        listDrawable.addState(new int []{android.R.attr.state_selected},selectBgDrawable);
        listDrawable.addState(new int []{},bgDrawable);
        setBackground(listDrawable);


        //设置textColor

        int textColor= Color.parseColor("#343434");
        int normalTextColor=array.getColor(R.styleable.TxView_normalTextColor,textColor);
        int selectTextColor=array.getColor(R.styleable.TxView_selectTextColor,normalTextColor);
        int pressTextColor=array.getColor(R.styleable.TxView_pressTextColor,normalTextColor);
        setTextColor(normalTextColor,(isSelect?selectTextColor:normalTextColor),(isPress?pressTextColor:normalTextColor));
    }

    /**
     *
     * @param radius 统一的圆角度数
     */
   void setRadius(float radius){
       bgDrawable.setCornerRadius(radius);
       if(isSelect)
            selectBgDrawable.setCornerRadius(radius);
       if(isPress)
           pressBgDrawable.setCornerRadius(radius);
    }

    /**
     * 设置不同的圆角
     * @param bottomLeftRadius 左下的圆角度数
     * @param bottomRightRadius 右下的圆角度数
     * @param topLeftRadius 左上的圆角度数
     * @param topRightRadius 右上的圆角度数
     */
    void setRadius(float bottomLeftRadius,float bottomRightRadius, float topLeftRadius ,float topRightRadius){
        float[] data=new float[]{topLeftRadius,topLeftRadius,
                topRightRadius,topRightRadius,
                bottomRightRadius,bottomRightRadius,
                bottomLeftRadius,bottomLeftRadius};

        bgDrawable.setCornerRadii(data);

        if(isSelect)
            selectBgDrawable.setCornerRadii(data);

        if(isPress)
            pressBgDrawable.setCornerRadii(data);
    }


    public void setTextColor(int normalColor,int selectColor,int pressColor) {
        int[][] states=new int[3][1];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        ColorStateList list=new ColorStateList(states,new int[]{selectColor,pressColor,normalColor});
        setTextColor(list);
    }


    /*
        当手指抬起时，如果当前时间和之前记录时间相差
     */

    private long clickTime=0;

    @SuppressWarnings("unused")
    public int getiIntervalTime() {
        return intervalTime;
    }

    @SuppressWarnings("unused")
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    private int intervalTime=1500;

    private static final String TAG = "MainActivity";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_UP){
            if(System.currentTimeMillis()-clickTime<intervalTime){
                Log.d(TAG, "onTouchEvent: ");
                return  true;
            }
            clickTime=System.currentTimeMillis();
        }

        return super.onTouchEvent(event);
    }
}

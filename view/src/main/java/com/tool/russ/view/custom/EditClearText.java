package com.tool.russ.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.tool.russ.view.R;

/**
 * author: russell
 * time: 2019-07-23:09:57
 * describe：自定义的EditText 可以取消输入
 */
public class EditClearText extends androidx.appcompat.widget.AppCompatEditText {
    private int size;
    public EditClearText(Context context) {
        this(context,null);
    }

    public EditClearText(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public EditClearText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.EditClearText);


        int resId=array.getResourceId(R.styleable.EditClearText_drawable,R.drawable.ic_clear);


        drawable= ContextCompat.getDrawable(context,resId);

        array.recycle();
    }

    private static final String TAG = "EditClearText";


    private Drawable drawable;
    private boolean isMeasure=false;
    int drawableLeft;
    int drawablePadding;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!isMeasure){

            int paddingRight=getRight();

            size= (int) ((getMeasuredHeight()-getPaddingTop()-getPaddingBottom())*0.75);

            int flag=paddingRight==0?10:0;

            setPadding(getPaddingLeft(),getTop(),getRight()+size+flag,getBottom());

             drawableLeft = getMeasuredWidth() - paddingRight - size-flag;

            drawablePadding = (getMeasuredHeight() - size) / 2;



            Log.d(TAG, "onMeasure: drawableLeft="+drawableLeft);


            isMeasure=true;

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        canvas.save();
        /*
         * 需要注意的是当文字内容有多行是，View的高度不变，但是会增加scrollY,所以绘制删除键位置时，需要考虑scrollY的值
         */
        int drawableTop = (getMeasuredHeight() +getScrollY()- drawablePadding-size);
        canvas.translate(drawableLeft,drawableTop);



        drawable.setBounds(0,0, size,size);
        drawable.draw(canvas);


        canvas.restore();

        super.onDraw(canvas);

    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_UP){

            if(event.getX()>=drawableLeft && event.getX()<=(drawableLeft+size)&&
                event.getY()>=drawablePadding && event.getY()<=(drawablePadding+size)){

                setText("");
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}

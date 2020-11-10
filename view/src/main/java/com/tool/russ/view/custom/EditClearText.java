package com.tool.russ.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.tool.russ.view.R;
import com.tool.russ.view.Tools.BitmapUtil;
import com.tool.russ.view.Tools.DisplayUtil;

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

        ClearDrawable= ContextCompat.getDrawable(context,resId);
        hideDrawable=ContextCompat.getDrawable(context,R.drawable.ic_edit_hide);
        showDrawable=ContextCompat.getDrawable(context,R.drawable.ic_edit_show);

        closeSize= DisplayUtil.dp2Px(20);
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        array.recycle();
        mPaint=new Paint();
        setBackgroundDrawable(null);
        mPaint.setTextSize(getTextSize());
    }

    private static final String TAG = "EditClearText";

    private Drawable ClearDrawable;
    private Drawable hideDrawable;
    private boolean passwordType=true;
    private Drawable showDrawable;
    private boolean isMeasure=false;
    int drawableLeft;
    int drawablePadding;
    private Paint mPaint;
    int closeSize;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!isMeasure){
            int paddingRight=getPaddingRight();
            size= (int) ((getMeasuredHeight()-getPaddingTop()-getPaddingBottom())*0.7);
            drawableLeft = getMeasuredWidth() - paddingRight - size;
            drawablePadding = (getMeasuredHeight() - size) / 2;
            isMeasure=true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(),null);
        super.onDraw(canvas);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){

            if(event.getX()>=drawableLeft &&
                    event.getX()<=(drawableLeft+size)){

                passwordType=!passwordType;
                if(passwordType){
                    //必须同时设置才能生效
                    setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    setInputType(InputType.TYPE_CLASS_TEXT);
                }
                invalidate();
                return true;
            }

            if(!getText().toString().equals("")){
                if(event.getX()>=(drawableLeft-15-size) &&
                        event.getX()<=(drawableLeft-15)){
                    setText("");
                    invalidate();
                    return true;
                }
            }

        }
        return super.onTouchEvent(event);
    }


    private Drawable getDrawable(){
        ClearDrawable.setBounds(0,0,size,size);
        Bitmap clear= BitmapUtil.scaleBitmap(BitmapUtil.drawableToBitmap(ClearDrawable),size,size);
        Bitmap bitmap=Bitmap.createBitmap(size*2+25,size, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Rect src=new Rect(0,0,size,size);
        Rect dst=new Rect(10,0,size+10,size);
        if(!getText().toString().equals("")){
            canvas.drawBitmap(clear,src,dst,mPaint);
        }
        Bitmap show;
        if(passwordType){
            show=BitmapUtil.scaleBitmap(BitmapUtil.drawableToBitmap(showDrawable),size,size);
        }else {
            show=BitmapUtil.scaleBitmap(BitmapUtil.drawableToBitmap(hideDrawable),size,size);
        }
        Rect dst2=new Rect(size+25,0,size*2+25,size);
        canvas.drawBitmap(show,src,dst2,mPaint);
        show.recycle();
        clear.recycle();
        return BitmapUtil.bitmapToDrawable(bitmap);
    }



}

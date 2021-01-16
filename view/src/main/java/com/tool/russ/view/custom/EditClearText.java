package com.tool.russ.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.tool.russ.view.R;
import com.tool.russ.view.Tools.BitmapUtil;
import com.tool.russ.view.Tools.DisplayUtil;

import java.lang.reflect.Field;
import java.nio.channels.Selector;

/**
 * author: russell
 * time: 2019-07-23:09:57
 * describe：自定义的EditText 可以取消输入
 * todo : 光标颜色用户自己设置,不在提供api
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


        clearDrawable= ContextCompat.getDrawable(context,array.getResourceId(R.styleable.EditClearText_closeDrawable,R.drawable.icon_clear));
        hideDrawable=ContextCompat.getDrawable(context,array.getResourceId(R.styleable.EditClearText_hideDrawable,R.drawable.icon_hide));
        showDrawable=ContextCompat.getDrawable(context,array.getResourceId(R.styleable.EditClearText_showDrawable,R.drawable.icon_show));

        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mPaint=new Paint();
        setBackgroundDrawable(null);
        mPaint.setTextSize(getTextSize());

        setCursorDrawableColor(this,Color.RED);

         focusColor=array.getColor(R.styleable.EditClearText_focusColor,Color.parseColor("#BDC7D8"));
         disFocusColor=array.getColor(R.styleable.EditClearText_disFocusColor,Color.parseColor("#728ea3"));

        backgroundDrawable=new GradientDrawable();
        backgroundDrawable.setCornerRadius(array.getDimension(R.styleable.EditClearText_strokeRadius,DisplayUtil.dp2Px(context,12)));
        focusWidth= (int) array.getDimension(R.styleable.EditClearText_focusWidth,DisplayUtil.dp2Px(context,2));
        backgroundDrawable.setStroke(focusWidth,disFocusColor);
        setBackground(backgroundDrawable);
        setPadding(10,0,10,0);

        array.recycle();
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    backgroundDrawable.setStroke(focusWidth,focusColor);
                }else {
                    backgroundDrawable.setStroke(focusWidth,disFocusColor);
                }
            }
        });


    }

    public  void setCursorDrawableColor(EditText editText, int color) {

        GradientDrawable drawable=new GradientDrawable();
        drawable.setColor(Color.BLUE);
        drawable.setSize(DisplayUtil.dp2Px(getContext(),2),1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setTextCursorDrawable(drawable);
        }
    }


    private static final String TAG = "EditClearText";

    private final Drawable clearDrawable;
    private final Drawable hideDrawable;
    private boolean passwordType=true;
    private final Drawable showDrawable;
    private boolean isMeasure=false;
    int drawableLeft;
    int drawablePadding;
    private final Paint mPaint;
    int focusColor;
    int disFocusColor;
    int focusWidth;


    private final GradientDrawable backgroundDrawable;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!isMeasure){
            int paddingRight=getPaddingRight();
            size= (int) ((getMeasuredHeight()-getPaddingTop()-getPaddingBottom())*0.4);
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
        clearDrawable.setBounds(0,0,size,size);
        Bitmap clear= BitmapUtil.scaleBitmap(BitmapUtil.drawableToBitmap(clearDrawable),size,size);
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

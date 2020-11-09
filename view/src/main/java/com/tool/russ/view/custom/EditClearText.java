package com.tool.russ.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.tool.russ.view.R;
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

        canvas.save();
        /*
         * 需要注意的是当文字内容有多行是，View的高度不变，但是会增加scrollY,所以绘制删除键位置时，需要考虑scrollY的值
         */
        int textWidth=drawableLeft;
        int drawableTop = (getMeasuredHeight() +getScrollY()- drawablePadding-size);
        canvas.translate(drawableLeft,drawableTop);
        Drawable drawable;
        if(passwordType){
            drawable=showDrawable;
        }else {
            drawable=hideDrawable;
        }
        drawable.setBounds(0,0, size,size);

        drawable.draw(canvas);

        if(!getText().toString().equals("")){

            canvas.translate(-size-15,0);
            ClearDrawable.setBounds(0,0, size,size);
            ClearDrawable.draw(canvas);
            textWidth+=size+15;
        }

        canvas.restore();
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
}

package com.tool.russ.view.custom;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tool.russ.view.R;

/**
 * author: russell
 * time: 2019-07-23:09:57
 * describe：自定义的EditText 可以取消输入
 */
public class EditClearText extends android.support.v7.widget.AppCompatEditText {
;
    int closeSize;
    Drawable drawable;

    int closeColor;

    public EditClearText(Context context) {
        this(context,null);
    }

    public EditClearText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditClearText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, android.R.attr.editTextStyle);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.EditClearText);

        closeColor=array.getColor(R.styleable.EditClearText_closeColor,Color.parseColor("#DFDFDF"));

        array.recycle();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                setClose(s.length()>0);

            }
        });


        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setClose(getText().length()>0);
                }else {
                    setClose(false);
                }

            }
        });


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float x=0.8f;

        if (getBackground() instanceof ColorDrawable) {
          x=0.5f;
        }

        closeSize= (int) ((MeasureSpec.getSize(heightMeasureSpec)-getPaddingBottom()-getPaddingTop())*x);


        setRightClose();
    }

    void setRightClose(){

       Bitmap bitmap=Bitmap.createBitmap(closeSize,closeSize, Bitmap.Config.ARGB_8888);
       Canvas canvas=new Canvas(bitmap);
       Paint paint=new Paint();
       paint.setColor(closeColor);


       int circle=closeSize/2;


       canvas.drawCircle(circle,circle,circle,paint);



       canvas.rotate(45,circle,circle);






       if (getBackground() instanceof ColorDrawable) {
           ColorDrawable colorDrawable = (ColorDrawable) getBackground();
           int color = colorDrawable.getColor();
           paint.setColor(color);
       }else {
           paint.setColor(Color.WHITE);
       }






       int closeWidth=closeSize/2;
       int closeHeight=closeSize/8;



       Rect rect;

       rect=new Rect(circle-closeWidth/2,circle-closeHeight/2,
               circle+closeWidth/2,circle+closeHeight/2);

       canvas.drawRect(rect,paint);


       rect=new Rect(circle-closeHeight/2,circle-closeWidth/2,
               circle+closeHeight/2,circle+closeWidth/2);

       canvas.drawRect(rect,paint);




       drawable=new BitmapDrawable(bitmap);



       drawable.setBounds(0,0,closeSize,closeSize);


       //setCompoundDrawables(null,null,drawable, null);

    }


    private static final String TAG = "EditClearText";
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction()==MotionEvent.ACTION_UP){
            int x= (int) event.getX();



            int left=getMeasuredWidth()-closeSize-getPaddingRight();
            int right=left+closeSize;

            if(x>left && x<right ){
                setText("");
                return  true;
            }


        }


        return super.onTouchEvent(event);
    }



    private void  setClose(boolean flag){
        if(flag){
            setCompoundDrawables(null,null,drawable, null);
        }else {

            setCompoundDrawables(null,null,null, null);
        }
    }



}

package com.tool.russ.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class CircleView extends android.support.v7.widget.AppCompatImageView {
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
       // super.onDraw(canvas);
        Bitmap bitmap=getBitmap();
        Paint mPaint=new Paint();
        BitmapShader shader=new BitmapShader(bitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);
        Matrix mMatrix = new Matrix();
        mMatrix.setScale(0.4f, 0.4f);
        shader.setLocalMatrix(mMatrix);
        mPaint.setShader(shader);
        canvas.drawCircle(getWidth()/2, getWidth()/2, getWidth()/2, mPaint);
    }

    private Bitmap getBitmap() {
        Drawable drawable=getDrawable();
        if(drawable instanceof BitmapDrawable)
            return  ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}

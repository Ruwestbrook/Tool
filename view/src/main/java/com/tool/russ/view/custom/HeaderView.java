package com.tool.russ.view.custom;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tool.russ.view.R;
import com.tool.russ.view.Tools.DisplayUtil;
import com.tool.russ.view.Tools.DrawableUtil;


public class HeaderView extends RelativeLayout {

    public HeaderView(Context context) {
        this(context,null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    String title;
    int textSize;
    int backSize;
    int backMargin;
    int textColor;
    int lineHeight;
    boolean hasLine;
    int lineColor;
    int backColor;

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.HeaderView);
        title=array.getString(R.styleable.HeaderView_title);

        textSize= DisplayUtil.px2Sp(context,(int) array.getDimension(R.styleable.HeaderView_title_size,DisplayUtil.sp2Px(context,16)));

        backSize= DisplayUtil.px2Dp(context,(int) array.getDimension(R.styleable.HeaderView_back_size,DisplayUtil.dp2Px(context,30)));
        backMargin= DisplayUtil.px2Dp(context,(int) array.getDimension(R.styleable.HeaderView_back_margin,DisplayUtil.dp2Px(context,15)));

        textColor=array.getColor(R.styleable.HeaderView_text_color, Color.parseColor("#333333"));
        backColor=array.getColor(R.styleable.HeaderView_back_color, 0);
        lineColor=array.getColor(R.styleable.HeaderView_line_color, Color.parseColor("#ebebeb"));
        hasLine=array.getBoolean(R.styleable.HeaderView_has_line,false);
        lineHeight= (int) array.getDimension(R.styleable.HeaderView_line_height, 1);

        array.recycle();
        initTitle();
    }

    private static final String TAG = "HeaderView";
    private void initTitle() {

        LayoutParams textLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        textLayoutParams.addRule(CENTER_IN_PARENT);

        TextView textView=new TextView(getContext());
        textView.setTextSize(textSize);
        textView.setText(title);
        textView.setTextColor(textColor);

        addView(textView,textLayoutParams);


        LayoutParams imageLayoutParams=new LayoutParams(backSize,backSize);

        imageLayoutParams.addRule(CENTER_VERTICAL);

        imageLayoutParams.leftMargin=backMargin;

        imageLayoutParams.addRule(ALIGN_PARENT_LEFT);

        ImageView imageView=new ImageView(getContext());
        Drawable drawable=ContextCompat.getDrawable(getContext(),R.drawable.ic_back);
        if(backColor!=0){
            drawable= DrawableUtil.tintDrawable(drawable,backColor);
        }
        imageView.setImageDrawable(drawable);


        addView(imageView,imageLayoutParams);

        imageView.setOnClickListener(v -> getActivity().finish());


        if(hasLine){
            View view=new View(getContext());
            view.setBackgroundColor(lineColor);

            LayoutParams lineLayoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,lineHeight);

            lineLayoutParams.addRule(ALIGN_PARENT_BOTTOM);

            addView(view,lineLayoutParams);

        }



    }


    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity)getRootView().getContext();
    }
}

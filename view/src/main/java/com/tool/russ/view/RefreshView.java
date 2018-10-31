package com.tool.russ.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RefreshView extends LinearLayout implements View.OnTouchListener {
    private static final String TAG = "RefreshView";
    private View headView;
    private int headHeight;
    private RecyclerView mRecyclerView;
    LinearLayout.LayoutParams layoutParams;
    public RefreshView(Context context) {
        this(context,null);
    }
    private boolean isFirst=false;

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(LinearLayout.VERTICAL);
        setLongClickable(true);
        headView=LayoutInflater.from(context).inflate(R.layout.refresh_title,this,false);
        addView(headView,0);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed && !isFirst){
            isFirst=true;
            headHeight=headView.getMeasuredHeight();
             layoutParams= (LayoutParams) headView.getLayoutParams();
            layoutParams.topMargin=-headHeight;
            headView.setLayoutParams(layoutParams);
            mRecyclerView= (RecyclerView) getChildAt(1);
            mRecyclerView.setOnTouchListener(this);
        }

    }

    private float downY=0;



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(canRefresh()){
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_UP:
                    //抬起
                    downY=0;
                    layoutParams.topMargin=-headHeight;
                    headView.setLayoutParams(layoutParams);
                    break;
                case MotionEvent.ACTION_DOWN:
                    //按下
                    downY=motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //移动
                    float y=motionEvent.getRawY();
                    layoutParams.topMargin= (int) (y-downY);
                    headView. setLayoutParams(layoutParams);
            }
        }
        return false;
    }

    private boolean canRefresh() {
        if(mRecyclerView!=null){
           LinearLayoutManager layoutManager= (LinearLayoutManager) mRecyclerView.getLayoutManager();
            View view=mRecyclerView.getChildAt(0);
            if(view!=null){
                if(view.getTop()==0 && layoutManager.findFirstVisibleItemPosition()==0){
                    return  true;
                }else {
                    if(layoutParams.topMargin!=-headHeight){
                        layoutParams.topMargin=-headHeight;
                        headView.setLayoutParams(layoutParams);
                    }
                }
            }
        }
        return  false;
    }
}

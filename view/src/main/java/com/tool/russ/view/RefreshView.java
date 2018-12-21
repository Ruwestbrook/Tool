package com.tool.russ.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class RefreshView extends LinearLayout implements View.OnTouchListener {
    private static final String TAG = "RefreshView";
    //正常状态
    private static final int STATUS_NORMAL=0;
    //下拉无法刷新状态
    private static final int STATUS_PULL=1;
    //下拉可以刷新
    private static final int STATUS_CAN_REFRESH=2;
    //刷新状态
    private static final int STATUS_REFRESH=3;

    private int currentStatus=0;



    //刷新头部
    private View headView;

    private Context mContext;

    //刷新头部高度
    private int headHeight;
    private int touchSlop;
    //当前是否可以下拉刷新
    private boolean canPull;

    private RefreshListener listener;
    private boolean otherHead=false;

    private int pullHeight;
    private RefreshEvent mRefreshEvent;
    private RecyclerView mRecyclerView;
    LinearLayout.LayoutParams layoutParams;
    public RefreshView(Context context) {
        this(context,null);
    }
    private boolean isFirst=false;
    TextView textView;
    ImageView imageView;
    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    @SuppressLint("HandlerLeak")
    public RefreshView(final Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        setOrientation(LinearLayout.VERTICAL);
        setLongClickable(true);
        headView=LayoutInflater.from(context).inflate(R.layout.refresh_title,this,false);
         textView=headView.findViewById(R.id.text);
        imageView=headView.findViewById(R.id.image);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.load));
        addView(headView,0);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mRefreshEvent=new RefreshEvent() {
            @Override
            public void canRefresh() {
                if(!otherHead){
                    textView.setText("释放更新");
                    Animation animation=AnimationUtils.loadAnimation(mContext,R.anim.rotate_one);
                    imageView.startAnimation(animation);
                }


            }

            @Override
            public void cancelRefresh() {
                if(!otherHead){
                    textView.setText("下拉刷新");

                    Animation animation=AnimationUtils.loadAnimation(mContext,R.anim.rotate_two);
                    imageView.startAnimation(animation);
                }


            }

            //开始刷新
            @Override
            public void startRefresh() {

                currentStatus=STATUS_REFRESH;

                textView.setText("正在刷新");
                //endRefresh();
                if(!otherHead){
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rotate));
                    Animation animation=AnimationUtils.loadAnimation(mContext,R.anim.rotate_three);
                    animation.setInterpolator(new LinearInterpolator());
                    imageView.startAnimation(animation);
                }

                listener.refresh();
            }
            //完成了一次刷新,并且隐藏了头部
            @Override
            public void finishRefresh() {

                if(!otherHead){
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.load));
                }
                currentStatus=STATUS_NORMAL;


            }

            @Override
            public void onRefresh(int distance) {
                if(!otherHead){
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.load));
                }

            }
        };
    }

    public  void setRefreshing(boolean refreshing){
        if(!refreshing && currentStatus==STATUS_REFRESH){
            new HideHeader().start();
        }
    }
    public void setRefreshEvent(RefreshEvent refreshEvent){
        mRefreshEvent=refreshEvent;
    }

    public void setOnRefreshListener(RefreshListener listener){
        this.listener=listener;
    }
    public void setHeadView(View headView) {
        this.headView = headView;
       this.removeViewAt(0);
       addView(headView,0);
        isFirst=false;
        otherHead=true;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed && !isFirst){
            isFirst=true;
            headHeight=headView.getMeasuredHeight();
            layoutParams= (LayoutParams) headView.getLayoutParams();
            if(layoutParams==null){
                layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            pullHeight=headHeight;
            layoutParams.topMargin=-headHeight;
            headView.setLayoutParams(layoutParams);
            mRecyclerView= (RecyclerView) getChildAt(1);
            mRecyclerView.setOnTouchListener(this);
        }

    }

    private float downY=0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(currentStatus==STATUS_REFRESH){
            return  true;
        }
        canRefresh(motionEvent);
        if(canPull){
            switch (motionEvent.getAction()){
                //抬起
                case MotionEvent.ACTION_UP:
                    if(currentStatus==STATUS_CAN_REFRESH){
                        //刷新 调用刷新方法
                        mRefreshEvent.startRefresh();
                    }else if(currentStatus==STATUS_PULL){
                        //无法刷新 但是要隐藏刷新头部
                        downY=0;
                        layoutParams.topMargin=-headHeight;
                        headView.setLayoutParams(layoutParams);
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    //按下
                    downY=motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //移动
                    float y=motionEvent.getRawY();
                    int distance= (int) (y-downY);
                    // 如果手指是下滑状态，并且下拉头是完全隐藏的，就屏蔽下拉事件
                    if (distance < touchSlop) {
                        return false;
                    }
                        if(distance>=pullHeight && currentStatus!=STATUS_CAN_REFRESH){
                            currentStatus=STATUS_CAN_REFRESH;
                            mRefreshEvent.canRefresh();
                        }
                        if(distance<pullHeight && currentStatus!=STATUS_PULL) {
                            currentStatus=STATUS_PULL;
                            mRefreshEvent.cancelRefresh();
                        }
                    layoutParams.topMargin=-headHeight+distance;
                    headView.setLayoutParams(layoutParams);
                    mRefreshEvent.onRefresh(-headHeight+distance);
                    return true;
            }
        }
        return false;
    }

    private void canRefresh(MotionEvent motionEvent) {
        if(mRecyclerView!=null){
           LinearLayoutManager layoutManager= (LinearLayoutManager) mRecyclerView.getLayoutManager();
            View view=mRecyclerView.getChildAt(0);
            if(view!=null){
                if(view.getTop()==0 && layoutManager.findFirstVisibleItemPosition()==0){
                    if(!canPull){
                        downY=motionEvent.getRawY();
                    }
                    canPull=true;
                }else {
                    if(layoutParams.topMargin!=-headHeight){
                            layoutParams.topMargin=-headHeight;
                            headView.setLayoutParams(layoutParams);
                        }
                        canPull=false;



                }
            }else {
                canPull=true;
            }
        }

    }




    class HideHeader{

        void start(){
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(layoutParams.topMargin, -headHeight).setDuration((long) (Math.abs((layoutParams.topMargin+headHeight) * 0.66)));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float nowDistance = (float) animation.getAnimatedValue();
                    layoutParams.topMargin= (int) nowDistance;
                    headView.setLayoutParams(layoutParams);
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    headView.setLayoutParams(layoutParams);
                    mRefreshEvent.finishRefresh();
                }
            });
            valueAnimator.start();
        }


    }



}

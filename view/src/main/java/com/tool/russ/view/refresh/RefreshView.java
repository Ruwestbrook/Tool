package com.tool.russ.view.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
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
import android.widget.TextView;
import com.tool.russ.view.R;


@SuppressWarnings("unused")
public class RefreshView extends ViewGroup implements View.OnTouchListener {
    private static final String TAG = "RefreshView";
    //正常状态
    private static final int STATUS_NORMAL=0;
    //下拉无法刷新状态
    private static final int STATUS_PULL=1;
    //下拉可以刷新
    private static final int STATUS_CAN_REFRESH=2;
    //刷新状态
    private static final int STATUS_REFRESH=3;

    //当前状态
    private int currentStatus=0;



    //刷新头部
    private View headView;

    private View footerView;



    private Context mContext;

    //刷新头部高度
    private int headHeight;

    //加载更多尾部的高度
    private int footerHeight;

    private int touchSlop;
    //当前是否可以下拉刷新
    private boolean canPull;

    private RefreshListener listener;
    private boolean otherHead=false;




    //刷新事件
    private RefreshEvent mRefreshEvent;
    private View targetView;
    //刷新头部的LayoutParams
    RefreshView.LayoutParams pullLayoutParams;
    //加载更多尾部的LayoutParams
    RefreshView.LayoutParams loadLayoutParams;
    public RefreshView(Context context) {
        this(context,null);
    }
    private boolean isFirst=false;
    TextView pullTextView;
    ImageView pullImageView;
    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        setLongClickable(true);
        initView(context);
    }

    private void initView(Context context) {
        headView=LayoutInflater.from(context).inflate(R.layout.refresh_title,this,false);


        if(headView.getParent() != null) {
            ((ViewGroup)headView.getParent()).removeView(headView); // <- fix
        }

        headHeight=headView.getMeasuredHeight();
        pullLayoutParams=new RefreshView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(pullLayoutParams);


        Log.d(TAG, "initView:headHeight= "+headHeight);

        addView(headView);



        pullTextView=headView.findViewById(R.id.text);
        pullImageView=headView.findViewById(R.id.image);
        pullImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.load));


        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mRefreshEvent=new RefreshEvent() {
            @Override
            public void canRefresh() {
                if(!otherHead){
                    pullTextView.setText("释放更新");
                    Animation animation=AnimationUtils.loadAnimation(mContext,R.anim.rotate_one);
                    pullImageView.startAnimation(animation);
                }


            }

            @Override
            public void cancelRefresh() {
                if(!otherHead){
                    pullTextView.setText("下拉刷新");

                    Animation animation=AnimationUtils.loadAnimation(mContext,R.anim.rotate_two);
                    pullImageView.startAnimation(animation);
                }


            }

            //开始刷新
            @Override
            public void startRefresh() {

                currentStatus=STATUS_REFRESH;

                pullTextView.setText("正在刷新");
                //endRefresh();
                if(!otherHead){
                    pullImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rotate));
                    Animation animation=AnimationUtils.loadAnimation(mContext,R.anim.rotate_three);
                    animation.setInterpolator(new LinearInterpolator());
                    pullImageView.startAnimation(animation);
                }

                listener.refresh();
            }
            //完成了一次刷新,并且隐藏了头部
            @Override
            public void finishRefresh() {

                if(!otherHead){
                    pullImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.load));
                }
                currentStatus=STATUS_NORMAL;


            }

            @Override
            public void onRefresh(int distance) {
                if(!otherHead){
                    pullImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.load));
                }

            }
        };

//        //设置尾部
//        footerHeight=footerView.getMeasuredHeight();
//        Log.d(TAG, "onLayout: footerHeight="+footerHeight);
//
//        loadLayoutParams= (LayoutParams) footerView.getLayoutParams();
//        if(loadLayoutParams==null){
//            loadLayoutParams=new RefreshView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//
//        loadLayoutParams.topMargin=getMeasuredHeight()-footerHeight;
//        Log.d(TAG, "onLayout: loadLayoutParams.topMargin="+(getMeasuredHeight()-footerHeight));
//        footerView.setLayoutParams(loadLayoutParams);
//        addView(footerView);



    }

    @SuppressWarnings("unused")
    public  void finishRefresh(){
        if(currentStatus==STATUS_REFRESH){
            new HideHeader().start();
        }
    }
    @SuppressWarnings("unused")
    public void setRefreshEvent(RefreshEvent refreshEvent){
        mRefreshEvent=refreshEvent;
    }
    @SuppressWarnings("unused")
    public void setOnRefreshListener(RefreshListener listener){
        this.listener=listener;
    }

    @SuppressWarnings("unused")
    public void setHeadView(View headView) {
        this.removeView(headView);
        this.headView=headView;
        isFirst=false;
        otherHead=true;
        addView(headView);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View view=getChildAt(i);
            if(view == headView){
                view.layout(0,pullLayoutParams.topMargin,headView.getMeasuredWidth(),pullLayoutParams.topMargin+headView.getMeasuredHeight());
            }else if(view==footerView){

            }else {
                //targetView;
                targetView=view;
                targetView.setOnTouchListener(this);
                int top=pullLayoutParams.topMargin+pullLayoutParams.height;
                if(top<0) top=0;
                view.layout(0,top,view.getMeasuredWidth(),top+view.getMeasuredHeight());

            }
        }

    }

    private boolean measureHeight=false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if(child==headView && !measureHeight){

                measureHeight=true;
                headHeight=headView.getMeasuredHeight();
                Log.d(TAG, "onMeasure: headHeight="+headHeight);

                Log.d(TAG, "onMeasure: headView.getMeasuredWidth()="+headView.getMeasuredWidth());

                pullLayoutParams=new RefreshView.LayoutParams(headView.getMeasuredWidth(),headView.getMeasuredHeight());

                pullLayoutParams.topMargin=-headHeight;

                headView.setLayoutParams(pullLayoutParams);

            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
                    Log.d(TAG, "onTouch: ACTION_UP");
                    if(currentStatus==STATUS_CAN_REFRESH){
                        //刷新 调用刷新方法
                        Log.d(TAG, "onTouch: 调用刷新方法");
                        mRefreshEvent.startRefresh();
                    }else if(currentStatus==STATUS_PULL){
                        //无法刷新 但是要隐藏刷新头部
                        Log.d(TAG, "onTouch: 无法刷新");
                        downY=0;
                        pullLayoutParams.topMargin=-headHeight;
                        headView.setLayoutParams(pullLayoutParams);
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
                    
                        if(distance>=headHeight && currentStatus!=STATUS_CAN_REFRESH){
                            currentStatus=STATUS_CAN_REFRESH;
                            mRefreshEvent.canRefresh();
                        }
                        if(distance<headHeight && currentStatus!=STATUS_PULL) {
                            currentStatus=STATUS_PULL;
                            mRefreshEvent.cancelRefresh();
                        }
                    pullLayoutParams.topMargin=-headHeight+distance;
                    headView.setLayoutParams(pullLayoutParams);
                    mRefreshEvent.onRefresh(-headHeight+distance);
                    return true;
            }
        }
        return false;
    }

    /*
      20200524 修改: 判断是否能滑动的方法更新
     */
    private void canRefresh(MotionEvent motionEvent) {

        //正数表示检查向下滚动 即如果在最顶部 那么这么值为true 可以向下滚动 手指操作是向上

        if(!targetView.canScrollVertically(-1)){
            if(!canPull){
                downY=motionEvent.getRawY();
            }
            canPull=true;
        }else {
            if(pullLayoutParams.topMargin!=-headHeight){
                pullLayoutParams.topMargin=-headHeight;
                headView.setLayoutParams(pullLayoutParams);
            }
            canPull=false;
        }
        Log.d(TAG, "canRefresh: canPull="+canPull);

    }



    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }




    /*
        是否能加载更多
     */
    private void canLoadMore(MotionEvent motionEvent) {

    }



    class HideHeader{

        void start(){
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(pullLayoutParams.topMargin, -headHeight).setDuration((long) (Math.abs((pullLayoutParams.topMargin+headHeight) * 0.66)));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float nowDistance = (float) animation.getAnimatedValue();
                    pullLayoutParams.topMargin= (int) nowDistance;
                    headView.setLayoutParams(pullLayoutParams);
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    headView.setLayoutParams(pullLayoutParams);
                    mRefreshEvent.finishRefresh();
                }
            });
            valueAnimator.start();
        }


    }


    protected boolean canChildScrollUp(View mTargetView) {
        return mTargetView.canScrollVertically( -1);
    }

    protected boolean canChildScrollDown(View mTargetView) {
        return mTargetView.canScrollVertically( 1);
    }



}

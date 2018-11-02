package com.tool.russ.view;

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
    //刷新头部高度
    private int headHeight;
    private int touchSlop;
    //当前是否可以下拉刷新
    private boolean canPull;

    private int pullHeight;
    private RefreshEvent mRefreshEvent;
    private RecyclerView mRecyclerView;
    LinearLayout.LayoutParams layoutParams;
    private Handler mHandler;
    public RefreshView(Context context) {
        this(context,null);
    }
    private boolean isFirst=false;
    TextView textView;
    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    @SuppressLint("HandlerLeak")
    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(LinearLayout.VERTICAL);
        setLongClickable(true);
        headView=LayoutInflater.from(context).inflate(R.layout.refresh_title,this,false);
         textView=headView.findViewById(R.id.text);
        addView(headView,0);
       mHandler=new Handler(){
           @Override
           public void handleMessage(Message msg) {
               switch (msg.what){
                   case 1:
                       textView.setText("正在刷新正在刷新正在刷新正在刷新正在刷新");
                       break;
                   case 2:
                       mRefreshEvent.endRefresh();
                       break;
               }
           }
       };
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mRefreshEvent=new RefreshEvent() {
            @Override
            public void canRefresh() {
                textView.setText("松开即可刷新");
            }

            @Override
            public void cancelRefresh() {
                textView.setText("下来刷新");
            }

            @Override
            public void startRefresh() {
                Log.d(TAG, "startRefresh:正在刷新正在刷新 "+textView.toString());
             mHandler.sendEmptyMessageDelayed(2,1500);
                textView.setText("正在刷新");
               //endRefresh();
            }

            @Override
            public void endRefresh() {

               new HideHeader().execute();
            }

            @Override
            public void onRefresh(int distance) {

            }
        };
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed && !isFirst){
            isFirst=true;
            headHeight=headView.getMeasuredHeight();
             layoutParams= (LayoutParams) headView.getLayoutParams();
            pullHeight=headHeight*3/4;
            layoutParams.topMargin=-headHeight;
            headView.setLayoutParams(layoutParams);
            mRecyclerView= (RecyclerView) getChildAt(1);
            mRecyclerView.setOnTouchListener(this);
        }

    }

    private float downY=0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
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
                        if(distance>=pullHeight){
                            currentStatus=STATUS_CAN_REFRESH;
                            mRefreshEvent.canRefresh();
                        }else {
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

    class HideHeader extends AsyncTask<Void,Integer,Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            int topMargin = layoutParams.topMargin;
            while (true) {
                topMargin = topMargin - 30;
                if (topMargin <= -headHeight) {
                    topMargin = -headHeight;
                    break;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return topMargin;
        }

        //处理异步所得结果
        @Override
        protected void onPostExecute(Integer integer) {
            layoutParams.topMargin=-headHeight;
            headView.setLayoutParams(layoutParams);
           currentStatus=STATUS_NORMAL;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            layoutParams.topMargin=values[0];
            headView.setLayoutParams(layoutParams);
            currentStatus=STATUS_NORMAL;
        }
    }
}

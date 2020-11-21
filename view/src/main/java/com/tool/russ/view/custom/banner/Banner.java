package com.tool.russ.view.custom.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tool.russ.view.R;
import com.tool.russ.view.Tools.DisplayUtil;

import java.util.List;

/**
 * author: russell
 * time: 2020/5/17:00:07
 * describe：
 */
public class Banner<T> extends FrameLayout  {



    //  使用
    ViewPager2 mViewPager2;
    BannerAdapter<T> mBannerAdapter;

    //数据源
    List<T> mList;
    //用于轮播
    Handler mHandler;

    //重复播放  是否轮播
    boolean autoPlay=false;


    IndicatorView mIndicatorView;


    public Banner(@NonNull Context context) {
        this(context,null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewPager2=new ViewPager2(context);

        initView(context,attrs);


    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.Banner);

        //设置Indicator属性
        int normalColor=array.getColor(R.styleable.Banner_indicator_normal_color, Color.WHITE);
        int chooseColor=array.getColor(R.styleable.Banner_indicator_choose_color,Color.RED);
        int type=array.getInt(R.styleable.Banner_indicator_type,0);
        int spacing= (int) array.getDimension(R.styleable.Banner_spacing,DisplayUtil.dp2Px(context,4));
        int size= (int) array.getDimension(R.styleable.Banner_indicator_size,DisplayUtil.dp2Px(context,8));
        mIndicatorView=new IndicatorView(context);
        mIndicatorView.setChooseColor(chooseColor);
        mIndicatorView.setNormalColor(normalColor);
        mIndicatorView.setType(type);
        mIndicatorView.setSpacing(spacing);
        mIndicatorView.setIndicatorSize(size);
        array.recycle();


    }


    public  void start() throws Exception {
        if(mBannerAdapter==null){
            throw new Exception("BannerAdapter can not be null!");
        }
        if(mList==null || mList.size()<1){
            throw new Exception("not data to show!");
        }

        mViewPager2.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=mBannerAdapter.getItemView(parent,viewType);
                RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(layoutParams);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


                if(position==0){
                    mBannerAdapter.setItem(holder.itemView,mList.get(mList.size()-1));
                }else if (position==mList.size()+1){
                    mBannerAdapter.setItem(holder.itemView,mList.get(0));
                }else {
                    mBannerAdapter.setItem(holder.itemView,mList.get(position-1));
                }

            }

            @Override
            public int getItemCount() {
                return mList.size()+2;
            }

            @Override
            public int getItemViewType(int position) {
               return mBannerAdapter.getItemType(position-1);
            }
        });

        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mViewPager2,layoutParams);

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                nowPosition=position;
                if(nowPosition==0){
                    mIndicatorView.setNowPosition(mList.size()-1);
                }else if (nowPosition==mList.size()+1){
                    mIndicatorView.setNowPosition(0);
                }else {
                    mIndicatorView.setNowPosition(nowPosition-1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                if(state==ViewPager2.SCROLL_STATE_IDLE){
                    if(nowPosition==0){
                        mViewPager2.setCurrentItem(mList.size(),false);
                        mIndicatorView.setNowPosition(mList.size()-1);
                    }else if (nowPosition==mList.size()+1){
                        mViewPager2.setCurrentItem(1,false);
                        mIndicatorView.setNowPosition(mList.size()-1);
                    }
                }

            }
        });

        mIndicatorView.setSize(mList.size());

        FrameLayout.LayoutParams indicatorParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        indicatorParams.gravity= 81;


        indicatorParams.bottomMargin= DisplayUtil.dp2Px(getContext(),10);

        addView(mIndicatorView,indicatorParams);

        mViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mViewPager2.setCurrentItem(nowPosition,false);


    }





    int nowPosition=1;

    public  void  stop() throws Exception {
        if(mBannerAdapter==null){
            throw new Exception("BannerAdapter can not be null");
        }
        if(mList==null || mList.size()<1){
            throw new Exception("not data to show!");
        }
    }

    public void setList(List<T> list){
        this.mList=list;
    }


    public void setBannerAdapter(BannerAdapter<T> adapter) throws Exception{
        if(adapter==null){
            throw new Exception("BannerAdapter can not be null");
        }
        this.mBannerAdapter=adapter;
    }

    public ViewPager2 getViewPager2() {
        return mViewPager2;
    }
}

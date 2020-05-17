package com.tool.russ.view.custom.banner;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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



    public Banner(@NonNull Context context) {
        this(context,null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewPager2=new ViewPager2(context);


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
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                if(state==ViewPager2.SCROLL_STATE_IDLE){
                    if(nowPosition==0){
                        mViewPager2.setCurrentItem(mList.size(),false);
                    }else if (nowPosition==mList.size()+1){
                        mViewPager2.setCurrentItem(1,false);
                    }
                }

            }
        });

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

}

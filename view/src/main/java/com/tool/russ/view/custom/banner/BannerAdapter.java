package com.tool.russ.view.custom.banner;

import android.view.View;
import android.view.ViewGroup;

/**
 * author: russell
 * time: 2020/5/17:00:22
 * describe：
 */
public abstract class BannerAdapter<T> {

    protected   abstract View getItemView(ViewGroup parent, int viewType);
    protected  abstract void setItem(View view, T item);
    protected  int getItemType(int position){
         return position;
     }

}


package com.tool.russ.view.custom.layout;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * author: russell
 * time: 2020/7/5:10:09
 * describe：
 */
class ScrollerLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}

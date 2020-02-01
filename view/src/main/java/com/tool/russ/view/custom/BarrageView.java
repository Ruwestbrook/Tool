package com.tool.russ.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * author: russell
 * time: 2020-02-01:19:49
 * describe：弹幕控件
 */


/*
    需求分析:文字滑动 文字可以设置大小,颜色,滚动速率
 */

public class BarrageView extends ViewGroup {
    public BarrageView(Context context) {
        super(context);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}

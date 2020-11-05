package com.tool.russ.tool;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * author: russell
 * time: 2020/7/5:11:16
 * describe：
 */
class DemoItemDecoration extends RecyclerView.ItemDecoration {


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

    }
    /**
     * @param outRect  itemView的外部边框  各边的距离
     *                 LinearLayoutManager 最后一个不需要画
     *                 GridLayoutManager和StaggeredGridLayoutManager最右边不需要话
     *
     *
     *
     *
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        LinearLayoutManager linearLayoutManager;
        GridLayoutManager gridLayoutManager;
        StaggeredGridLayoutManager staggeredGridLayoutManager;




    }
}

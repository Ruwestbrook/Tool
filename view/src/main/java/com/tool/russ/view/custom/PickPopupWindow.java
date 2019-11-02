package com.tool.russ.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tool.russ.view.R;


/**

 */
public class PickPopupWindow extends PopupWindow {
    private LinearLayout layout;

    private TextView firstBtn;
    private TextView secondBtn;

    @SuppressLint("ClickableViewAccessibility")
    public PickPopupWindow(Context context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View mMenuView = inflater.inflate(R.layout.pick_popup_window, null);
        layout = mMenuView.findViewById(R.id.layout);
         firstBtn = mMenuView.findViewById(R.id.first);
         secondBtn = mMenuView.findViewById(R.id.second);
        firstBtn.setOnClickListener(itemsOnClick);
        secondBtn.setOnClickListener(itemsOnClick);
        mMenuView.findViewById(R.id.cancelBtn).setOnClickListener(itemsOnClick);

        layout.setOnClickListener(itemsOnClick);

        // 设置PickPopupWindow的View
        this.setContentView(mMenuView);
        // 设置PickPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置PickPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置PickPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置PickPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupwindow);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PickPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = layout.getTop();
                int y      = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public PickPopupWindow setFirstText(String firstStr) {
        if (firstBtn != null && firstStr != null) {
            firstBtn.setText(firstStr);
        }
        return this;
    }

    public PickPopupWindow setSecondText(String secondStr) {
        if (secondBtn != null && secondStr != null) {
            secondBtn.setText(secondStr);
        }
        return this;
    }
}

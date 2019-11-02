package com.tool.russ.view.custom;
import android.content.Context;
import android.util.AttributeSet;

/**
 * author: russell
 * time: 2019-07-23:09:57
 * describe：自定义的EditText 可以取消输入
 */
public class EditClearText extends android.support.v7.widget.AppCompatEditText {
    private int width;
    private int height;
    public EditClearText(Context context) {
        this(context,null);
    }

    public EditClearText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditClearText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}

package com.tool.russ.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author russell
 * @description:
 * @date : 2021/1/16 16:10
 */
public abstract class BaseActivity extends AppCompatActivity{



    public abstract int contentViewId();


    @UiThread
    public void showLoading(){

    }


    @UiThread
    public void hideLoading(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId());
    }
}

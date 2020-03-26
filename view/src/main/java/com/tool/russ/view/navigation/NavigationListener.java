package com.tool.russ.view.navigation;


import android.support.v4.app.Fragment;

public interface NavigationListener {


    void onPageChange(int nowIndex, Fragment nowFragment, int lastIndex, Fragment lastFragment);

}

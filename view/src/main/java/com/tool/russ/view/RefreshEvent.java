package com.tool.russ.view;

public interface RefreshEvent {
    void canRefresh();
    void cancelRefresh();
    void startRefresh();
    void endRefresh();
    void onRefresh(int distance);
}

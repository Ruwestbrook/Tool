package com.tool.russ.view.refresh

interface RefreshEvent {
    fun canRefresh()
    fun cancelRefresh()
    fun startRefresh()
    fun finishRefresh()
    fun onRefresh(distance: Int)
}

package com.tool.russ.view

import android.annotation.SuppressLint
import android.content.Context

/**
 * author: russell
 * time: 2019-08-06:16:50
 * describe：
 */
class ToolView{
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var  context:Context
        @JvmStatic
        fun init(context: Context){
            this.context=context
        }
    }
}

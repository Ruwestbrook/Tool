package com.tool.russ.view.Tools

import android.widget.Toast
import com.tool.russ.view.ToolView

class ToastUtil {

    companion object{

        private const val interval=3000

        var toast:Toast?=null
        var toastTime=System.currentTimeMillis()

        private fun toast(msg:String?){
            if(msg==null){
                return
            }
            val now=System.currentTimeMillis()
            if(now> toastTime+interval){
                toast?.cancel()
            }

            val nowToast=Toast.makeText(ToolView.context,msg,Toast.LENGTH_LONG)

            nowToast.show()

            toast =nowToast

            toastTime=now
        }

        fun toast(resId:Int){
            toast(ToolView.context.getString(resId))
        }


    }


}
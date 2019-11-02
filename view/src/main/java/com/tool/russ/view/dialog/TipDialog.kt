package com.tool.russ.view.dialog

import android.app.Dialog
import com.tool.russ.view.R
import com.tool.russ.view.ToolView


/**
author: russell
time: 2019-08-06:16:40
describe：
 */
class TipDialog {
    companion object{
        private  var dialog:Dialog?=null
        private  var tipDialog:TipDialog=TipDialog()

        @JvmField
        val NORMAL_TYPE=0

        @JvmField
        val SUCCESS_TYPE = 1

        @JvmField
        val ERROR_TYPE = 2

        @JvmField
        val INFO_TYPE = 3

        @JvmField
        val WARN_TYPE = 4

        @JvmStatic
        fun createNormalTips():TipDialog{
            dialog=Dialog(ToolView.context)
            dialog?.setContentView(R.layout.tips_dialog)
            return tipDialog
        }
    }





    fun show(){
        dialog?.window?.setWindowAnimations(R.style.tip_dialog)
        dialog?.show()
    }

}
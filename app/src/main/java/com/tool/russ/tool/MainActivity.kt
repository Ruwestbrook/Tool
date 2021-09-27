package com.tool.russ.tool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tool.russ.view.ToolView
import com.tool.russ.view.Tools.DisplayUtil
import com.tool.russ.view.base.BaseDecoration
import com.tool.russ.view.custom.GradualChangeTextView


class MainActivity : Activity() {

    var text:GradualChangeTextView?=null
    @SuppressLint("HandlerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToolView.init(this)
        setContentView(R.layout.activity_main)
         text=findViewById<GradualChangeTextView>(R.id.list)
//        val list=findViewById<RecyclerView>(R.id.list)
//        list.layoutManager=LinearLayoutManager(this)
//        val decoration=BaseDecoration(ContextCompat.getDrawable(this,R.drawable.divider))
//        list.addItemDecoration(decoration)
//        list.adapter=object :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//                return object :RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)){}
//            }
//
//            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//                val textView=holder.itemView as TextView
//
//                textView.text="这是第${position+1}个item"
//            }
//
//            override fun getItemCount(): Int {
//                return 20
//            }
//
//        }




    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_MOVE) {
          val  progress:Float = event.x.toFloat() / DisplayUtil.screenWidth().toFloat()
            text?.progress=progress
        }
        return true
    }

}



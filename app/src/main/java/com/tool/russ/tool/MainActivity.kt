package com.tool.russ.tool

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tool.russ.view.ToolView
import com.tool.russ.view.custom.ProgressNumber
import com.tool.russ.view.dialog.TipDialog
import com.tool.russ.view.refresh.RefreshListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    internal var progressNumber: ProgressNumber? = null
    internal var progress: Int = 0
    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1->{
                    if (progress < 101) {
                        progressNumber!!.setProgress(progress++)
                        sendEmptyMessageDelayed(1, 100)
                    }
                }
                2->{
                    //refresh.finishRefresh()
                }
            }

        }
    }

    @SuppressLint("HandlerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        ToolView.init(this)
//        val recyclerView = findViewById<RecyclerView>(R.id.list)
//        recyclerView.adapter=object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
//                return object : RecyclerView.ViewHolder(view) {}
//            }
//
//            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//            }
//
//            override fun getItemCount(): Int {
//                return 20
//            }
//        }
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//
//        refresh.setListener(object:RefreshListener(){
//            override fun refresh() {
//                handler.sendEmptyMessageDelayed(2,3000)
//
//            }
//
//        })

    }

    fun show(view: View) {
        TipDialog.createNormalTips().show()
    }

    companion object {
        private val TAG = "MainActivity"
    }
}

package com.tool.russ.tool

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tool.russ.view.ToolView
import com.tool.russ.view.custom.EditClearText
import com.tool.russ.view.custom.ProgressNumber
import com.tool.russ.view.custom.banner.Banner
import com.tool.russ.view.custom.banner.BannerAdapter
import com.tool.russ.view.dialog.TipDialog
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
        ToolView.init(this)
        setContentView(R.layout.activity_main)

        val list:MutableList<Int> =ArrayList<Int>()

        list.add(R.drawable.pic)
        list.add(R.drawable.auction6_1)
        list.add(R.drawable.auction6_2)

        val test=findViewById<EditClearText>(R.id.test);
        val layoutParams=test.layoutParams as FrameLayout.LayoutParams

        Log.d("test", layoutParams.gravity.toString())



        @Suppress("UNCHECKED_CAST")
        val mBanner=banner as Banner<Int>;



        mBanner.setBannerAdapter(object :BannerAdapter<Int>(){

            override fun getItemView(parent: ViewGroup, viewType: Int): View {
                return ImageView(parent.context)
            }


            override fun setItem(view: View?, item: Int) {
                 if(view!=null){
                     view as ImageView
                     view.setImageDrawable(ContextCompat.getDrawable(view.context,item))
                 }

            }

        })

        mBanner.setList(list)

        mBanner.start()







//        list.adapter=object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
//                return object : RecyclerView.ViewHolder(view) {}
//            }
//
//            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//                holder.itemView.post { Runnable {
//                    Log.d(TAG,"第${holder.adapterPosition}个，width=${holder.itemView.width},height=${holder.itemView.height}")
//                }.run() }
//
//
//            }
//
//            override fun getItemCount(): Int {
//                return 20
//            }
//        }
//
//        list.layoutManager = GridLayoutManager(this,2)
//
//        //添加Android自带的分割线
//        val decoration=DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//
//        decoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider)!!)
//        list.addItemDecoration(decoration)
//
//
//        val decoration1=DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
//
//        decoration1.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider)!!)
//        list.addItemDecoration(decoration1)

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



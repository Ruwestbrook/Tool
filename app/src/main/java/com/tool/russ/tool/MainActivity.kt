package com.tool.russ.tool

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tool.russ.view.ToolView
import com.tool.russ.view.custom.BarChartView
import com.tool.russ.view.custom.ProgressNumber
import com.tool.russ.view.dialog.TipDialog
import com.tool.russ.view.refresh.RefreshListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item.view.text
import java.util.*


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
//
//        val list:MutableList<Int> =ArrayList<Int>()
//
//        list.add(R.drawable.pic)
//        list.add(R.drawable.auction6_1)
//        list.add(R.drawable.auction6_2)
//
//        val test=findViewById<EditClearText>(R.id.test);
//        val layoutParams=test.layoutParams as FrameLayout.LayoutParams
//
//        Log.d("test", layoutParams.gravity.toString())



//        @Suppress("UNCHECKED_CAST")
//        val mBanner=banner as Banner<Int>;
//
//
//
//        mBanner.setBannerAdapter(object :BannerAdapter<Int>(){
//
//            override fun getItemView(parent: ViewGroup, viewType: Int): View {
//                return ImageView(parent.context)
//            }
//
//
//            override fun setItem(view: View?, item: Int) {
//                 if(view!=null){
//                     view as ImageView
//                     view.setImageDrawable(ContextCompat.getDrawable(view.context,item))
//                 }
//
//            }
//
//        })
//
//        mBanner.setList(list)
//
//        mBanner.start()

//        val array = Array(12){ i->"$i"}
//        bar.setDate(floatArrayOf(24f,45f,55f,180f,65f,44f,78f,96f,18f,108f,136f,54f),array)
//



//
        list.adapter=object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                holder.itemView.text.text="第${position}个content"

            }

            override fun getItemCount(): Int {
                return 20
            }
        }


        val layoutManager=GridLayoutManager(this,3);
        list.addItemDecoration(DemoItemDecoration())
        //layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        list.layoutManager=layoutManager

//        list.addOnScrollListener(object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//
//
//
//                Log.d(TAG,"滑动情况-1"+recyclerView.canScrollVertically(-1))
//                Log.d(TAG,"滑动情况1"+recyclerView.canScrollVertically(1))
//
//            }
//        })
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
//
//
//        refresh.setOnRefreshListener(object : RefreshListener() {
//            override fun refresh() {
//                handler.sendEmptyMessageAtTime(2,2500)
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



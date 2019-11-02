@file:Suppress("UnusedImport")

package com.tool.russ.view.refresh

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.tool.russ.view.R
import kotlinx.android.synthetic.main.refresh_title.view.*

/**
author: russell
time: 2019-08-08:15:35
describe：
 */
class RefreshLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr), View.OnTouchListener {

    constructor(context: Context?):this(context,null,0)

    constructor(context: Context?,  attrs: AttributeSet?):this(context,attrs,0)


    companion object {
        /* 正常状态 */
        private const val NORMAL_STATE=0

        /* 下拉状态 */
        private const val PULL_STATE=1

        /* 下拉状态可刷新状态 */
        private const val PULL_COULD_STATE=2

        /* 正在刷新状态 */
        private const val REFRESH_STATE=3

    }

    private var nowState=0


    private var refreshEvent:RefreshEvent?=null
    private var refreshListener:RefreshListener?=null


    fun setListener(listener: RefreshListener?){
        refreshListener=listener
    }

    private var headerView:View?=null
    private var footerView:View?=null

    /* 是否需要计算头部高度 */
    private var needLayout=false

    /* 头部View的LayoutParams */
    private var headerLayoutParams: LayoutParams? = null

    /* 尾部View的LayoutParams */
    private var footerLayoutParams: LayoutParams? = null

    /* 总高度 */
    private var parentHeight=0


    /* 头部高度 */
    private var headerHeight=0

    /* 头部高度 */
    private var footerHeight=0

    /* 滑动时Y坐标 */
    private var refreshY=0f

    private var touchSlop=0

    /* 隐藏头部的动画 */
    private lateinit var hideAnimation:ValueAnimator

    private var targetView:View?=null

    /* 刷新系数 */
    private var refreshCoefficient=0.8f

    private var headerTextView:TextView?=null
    private var headerImageView:ImageView?=null


    init {
        orientation= VERTICAL

        headerView=LayoutInflater.from(context).inflate(R.layout.refresh_title,
                this,false)






        footerView=LayoutInflater.from(context).inflate(R.layout.refresh_title,
                this,false)

        headerTextView=headerView?.findViewById(R.id.text)
        headerImageView=headerView?.findViewById(R.id.image)

        addView(headerView,0)
        addView(footerView,1)





        touchSlop = ViewConfiguration.get(context).scaledTouchSlop

        Log.d("RefreshLayout","touchSlop=$touchSlop")


        refreshEvent=object :RefreshEvent{
            override fun canRefresh() {
                headerTextView?.text="释放更新"
                val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_one)
                headerImageView?.startAnimation(animation)

            }

            override fun cancelRefresh() {
                headerTextView?.text = "下拉刷新"
                val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_two)
                headerImageView?.startAnimation(animation)

            }

            override fun startRefresh() {

                nowState = REFRESH_STATE

                headerTextView?.text = "正在刷新"

                headerImageView?.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.rotate))

                val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_three)

                animation.interpolator = LinearInterpolator()

                headerImageView?.startAnimation(animation)

                refreshListener?.refresh()

            }

            override fun finishRefresh() {
                headerImageView?.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.load))
                headerTextView?.text = "下拉刷新"
                headerImageView?.clearAnimation()

            }

            override fun onRefresh(distance: Int) {
                Log.d("RefreshLayout","distance=$distance")

            }

        }

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if(changed && !needLayout){
            needLayout=true

            parentHeight = measuredHeight

            headerHeight = headerView?.measuredHeight!!

            footerHeight = footerView?.measuredHeight!!

            headerLayoutParams=headerView?.layoutParams as LayoutParams

            headerLayoutParams?.topMargin=-headerHeight


            //尾部高度的设置
            footerLayoutParams=footerView?.layoutParams as LayoutParams

            if(footerLayoutParams==null){
                footerLayoutParams= LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
            }

            footerLayoutParams?.topMargin=parentHeight-footerHeight




            Log.d("RefreshLayout", "parentHeight=$parentHeight")
            Log.d("RefreshLayout", "footerHeight=$footerHeight")


            removeView(footerView)

            if(getChildAt(1) is RecyclerView ){

                Log.d("RefreshLayout", "是")

                targetView= getChildAt(1)

                (targetView as RecyclerView).setOnTouchListener(this)

//                val lp= (targetView as RecyclerView).layoutParams as LayoutParams
//
//                lp.topMargin=-footerHeight
//
//                (targetView as RecyclerView).layoutParams=lp


            }else{
                Log.d("RefreshLayout", "不是")

            }
//            if(getChildAt(1)==ListView::class.java){
//                val recycler = getChildAt(1) as ListView
//                recycler.setOnTouchListener(this)
//            }

            headerView?.layoutParams=headerLayoutParams

            footerView?.layoutParams=footerLayoutParams


            addView(footerView,2)

            requestLayout()
        }

    }


    /**
     * 判断的关键在于滑动时 列表需要在最顶部才能加载
     */
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if(nowState== REFRESH_STATE){
            return true
        }
        if(canRefresh()){
            when(p1?.action){
                /*
                手指按下
                 */
                MotionEvent.ACTION_DOWN->{


                    refreshY= p1.rawY
                }
                /*
                手机滑动
                 */
                MotionEvent.ACTION_MOVE->{
                    val distance=(p1.rawY-refreshY).toInt()
                    if (distance < touchSlop) {
                        return false
                    }
                    if(distance>0){
                        headerLayoutParams?.topMargin =-headerHeight+distance
                        headerView?.layoutParams = headerLayoutParams
                        refreshEvent?.onRefresh(distance)
                        //当前是下拉状态

                        nowState=
                          if(distance>(headerHeight*refreshCoefficient).toInt()){
                            if(nowState!= PULL_COULD_STATE){
                                refreshEvent?.canRefresh()
                            }
                             PULL_COULD_STATE
                            }else{
                                if(nowState== PULL_COULD_STATE){
                                    refreshEvent?.cancelRefresh()
                                }
                                PULL_STATE
                          }
                    }
                    return true
                }
                /*
                手指抬起
                 */
                MotionEvent.ACTION_UP->{
                    refreshY=0f
                    //当前的距离可以下拉刷新
                    if(nowState== PULL_COULD_STATE){
                        refreshEvent?.startRefresh()
                        nowState= REFRESH_STATE
                    }else if(nowState== PULL_STATE){
                        hideHeader()
                        nowState= NORMAL_STATE
                    }

                    return true

                }
            }
        }

        return  false
    }


    /*
        判断是否能下拉刷新
     */
    private fun canRefresh(): Boolean {

        if(targetView is RecyclerView){

            val recyclerView=targetView as RecyclerView
            val manager=recyclerView.layoutManager as LinearLayoutManager

            if(recyclerView.getChildAt(0).top==0 && manager.findFirstVisibleItemPosition()==0){


                return true

            }else{
                if(headerLayoutParams?.topMargin!=-headerHeight){
                    headerLayoutParams?.topMargin=-headerHeight
                    headerView?.layoutParams=headerLayoutParams
                }
            }

        }

        return false
    }



    /*
    调用此方法通知控件 还原状态 刷新完成
     */
    open fun finishRefresh(){
        hideHeader()
    }


    private fun hideHeader(){

       val distance= headerLayoutParams?.topMargin ?: return

        hideAnimation = ValueAnimator.ofInt(distance,-headerHeight)

        hideAnimation.repeatCount = 0
        hideAnimation.duration = 350
        hideAnimation.addUpdateListener {
            val now = it.animatedValue as Int
            headerLayoutParams?.topMargin = now
            headerView?.layoutParams = headerLayoutParams
        }
        hideAnimation.start()
        hideAnimation.addListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                nowState= NORMAL_STATE
                refreshEvent?.finishRefresh()
            }


        })
    }

}
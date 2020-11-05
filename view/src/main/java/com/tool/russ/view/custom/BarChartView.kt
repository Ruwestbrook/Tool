package com.tool.russ.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.roundToInt

/**
author: russell
time: 2020/6/8:22:49
describe：
 */
class BarChartView :View {
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    private var mBarWidth: Int=0

    private var mMax:Int=0

    private var mRadius:Int = 30

    private var mGap:Int = 20

    private val mAxisPaint:Paint= Paint()
    private val mBarPaint:Paint= Paint()

    private val mTextRect:Rect= Rect()

     private var  mDataList:FloatArray = floatArrayOf(0f)
         set(value) {
            field=value
             mMax= value.max()!!.roundToInt()

    }

    lateinit var mHorizontalAxis:Array<String>

    private val mBars:MutableList<Bar> = mutableListOf()
    init {

        mAxisPaint.textAlign= Paint.Align.CENTER
        mAxisPaint.textSize=50f
        mBarWidth=20
    }


    private var enableGrowAnnotation=false

    private val BAR_GROW_STEP=10

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBars(canvas)
    }

    fun setDate(mDataList: FloatArray, mHorizontalAxis:Array<String>){
        this.mHorizontalAxis=mHorizontalAxis;
        this.mDataList=mDataList;
        this.mMax= mDataList.max()!!.roundToInt()
        invalidate()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBars.clear()
        if(mMax==0){
            return
        }

        val width=w-paddingLeft-paddingRight
        val height=h-paddingTop-paddingBottom

        val step=width/mDataList.size

        mRadius=mBarWidth/2

        var barLeft=paddingLeft+step/2-mRadius

        //获取坐标文本宽高
        mAxisPaint.getTextBounds(mHorizontalAxis[0],0,mHorizontalAxis[0].length,mTextRect)

        val maxBarHeight=height-mTextRect.height()-mGap

        val heightRatio:Float=(maxBarHeight/mMax).toFloat()

        for (item in mDataList) {


            val bar = Bar();

            bar.value=item
            bar.transformedValue=bar.value*heightRatio
            bar.left=barLeft
            bar.top=(paddingTop+maxBarHeight-bar.transformedValue).toInt()
            bar.right=barLeft+mBarWidth
            bar.bottom=paddingTop+maxBarHeight
            bar.currentTop=bar.bottom
            Log.d(TAG, "drawBars: bar.bottom=${bar.bottom}")

            mBars.add(bar)

            barLeft+=step

        }

    }
    private  val TAG = "BarChartView"

    private fun drawBars(canvas: Canvas?){
        mBars.forEachIndexed { index, bar ->

            val axis=mHorizontalAxis[index]
            val textX=bar.left+mRadius
            val textY=height-paddingBottom

            canvas?.drawText(axis, textX.toFloat(), textY.toFloat(),mAxisPaint)
            mBarPaint.color=Color.BLUE
            bar.currentTop-=BAR_GROW_STEP
            if(index==2){
                Log.d(TAG, "drawBars: bar.currentTop=${bar.currentTop}")
                Log.d(TAG, "drawBars: enableGrowAnnotation=${enableGrowAnnotation}")
                Log.d(TAG, "drawBars: mMax=${mMax}")
            }
            if(bar.currentTop<=bar.currentTop){
                bar.currentTop=bar.top

                if(bar.currentTop==mMax){
                    Log.d(TAG, "drawBars: bar.currentTop==paddingTop")
                    enableGrowAnnotation=true
                }
            }
            canvas?.drawRoundRect(bar.left.toFloat(), bar.currentTop.toFloat(), bar.right.toFloat(), bar.bottom.toFloat(), mRadius.toFloat(), mRadius.toFloat(),mBarPaint)

            if(enableGrowAnnotation){
                postInvalidateDelayed(1000)
            }

        }
    }
}

data class Bar(var left:Int=0,var right:Int=0,var top:Int=0,var bottom:Int=0,var currentTop:Int=0,var value:Float=0f,var transformedValue: Float=0f)
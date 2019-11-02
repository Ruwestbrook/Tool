package com.tool.russ.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.tool.russ.view.R

/**
author: russell
time: 2019-08-06:11:50
describe：
 */
class ProgressNumber(context: Context, attributes: AttributeSet?, defStyleAttr: Int) :
        View(context,attributes,defStyleAttr) {

    constructor(context: Context):this(context,null,0)

    constructor(context: Context, attributes: AttributeSet?):this(context,attributes,0)

    private var paint:Paint?=null

    private var linePaint:Paint?=null

    private var progress:Int=0

    private var max:Int=0

    private  var normalColor:Int=0

    private  var finishColor:Int=0

    private  var textSize:Int=0

    private var progressHeight=0

    private var progressWidth=0

    private var rect:Rect?=null
    init {
        paint = Paint()

        linePaint = Paint()

        val arrays=context.obtainStyledAttributes(attributes, R.styleable.ProgressNumber)

        normalColor=arrays.getColor(R.styleable.ProgressNumber_normalColor,ContextCompat.getColor(context, R.color.normal))

        finishColor=arrays.getColor(R.styleable.ProgressNumber_finishColor,ContextCompat.getColor(context, R.color.finish))

        progress=arrays.getInteger(R.styleable.ProgressNumber_start,0)

        max=arrays.getInteger(R.styleable.ProgressNumber_maxProgress,100)

        textSize=arrays.getDimensionPixelSize(R.styleable.ProgressNumber_textSize,36)

        paint?.textSize = textSize.toFloat()

        rect = Rect()

        paint?.getTextBounds("$progress%", 0,"$progress%".length, rect)

        progressHeight = rect?.height()!!

        linePaint?.strokeWidth= (progressHeight*0.2).toFloat()

        arrays.recycle()



    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        progressWidth=MeasureSpec.getSize(widthMeasureSpec)

        setMeasuredDimension(progressWidth,progressHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val text= "$progress%"

        rect = Rect()

        paint?.getTextBounds(text, 0,text.length, rect)

        val width = rect?.width()!!

        val selectWidth = (progressWidth-width-10)*progress*0.01

        linePaint?.color = finishColor

        val lineHeight = (progressHeight*0.6).toFloat()

        canvas?.drawLine(0f, lineHeight,selectWidth.toFloat(),lineHeight,linePaint!!)

        paint?.color=if(progress<1) normalColor else finishColor

        canvas?.drawText(text,selectWidth.toFloat()+5,progressHeight.toFloat(),paint!!)

        linePaint?.color = normalColor

        canvas?.drawLine((width+10+selectWidth).toFloat(),lineHeight,progressWidth.toFloat(),
                lineHeight,linePaint!!)
    }

    fun  setProgress(progress:Int){
        checkProgress(progress)
        invalidate()
    }

    private fun checkProgress(progress: Int) {
      this.progress=if(progress>max) max else progress
      this.progress=if(progress<0) 0 else progress

    }


}
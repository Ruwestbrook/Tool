package com.tool.russ.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import android.view.MotionEvent

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import com.tool.russ.view.R


class GradualChangeTextView : AppCompatTextView {
    constructor(context: Context?) : this(context!!,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context!!, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr){
        val typedArray= context.obtainStyledAttributes(attrs,R.styleable.GradualChangeTextView)
        selectColor=typedArray.getColor(R.styleable.GradualChangeTextView_selectTextColor,0)
        unselectColor=typedArray.getColor(R.styleable.GradualChangeTextView_unselectTextColor,0)
        textSize= typedArray.getDimensionPixelSize(R.styleable.GradualChangeTextView_textSize,30)
        typedArray.recycle()
        paint.color=selectColor
        paint.isAntiAlias=true
        paint.textSize= textSize.toFloat()
    }
    var progress = 0.40f
        set(value) {
            Log.d(TAG, ":   set(value)=$value")
            field = value
            invalidate()
        }
    var paint:Paint = Paint()

    private var selectColor: Int = 0
    private var unselectColor: Int = 0
    private var textSize: Int = 0


    private  val TAG = "GradualChangeTextView"

    override fun onDraw(canvas: Canvas?) {
        if(canvas==null){
            return
        }
        val text=text
        if(TextUtils.isEmpty(text)){
            return
        }
        Log.d(TAG, "onDraw: ")
        drawBottomText(canvas,text.toString())
        drawUpText(canvas,text.toString())



    }


    private fun drawUpText(canvas: Canvas,text:String){
        canvas.save()
        paint.color=Color.BLUE
        val textWidth=paint.measureText(text)
        val textHeight=paint.descent()+paint.ascent()
        val drawX=(width-textWidth)/2
        val drawY=(height-textHeight)/2
        canvas.clipRect(drawX.toInt(),0,(drawX+drawX*progress).toInt(),height)
        canvas.drawText(text, drawX, drawY,paint)
        canvas.restore()
    }



    private fun drawBottomText(canvas: Canvas,text:String){
        canvas.save()
        paint.color=Color.RED
        val textWidth=paint.measureText(text)
        val textHeight=paint.descent()+paint.ascent()
        val drawX=(width-textWidth)/2
        val drawY=(height-textHeight)/2
        canvas.drawText(text, drawX, drawY,paint)
        canvas.restore()
    }

}
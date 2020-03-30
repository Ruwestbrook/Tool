package com.tool.russ.view.Tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.tool.russ.view.ToolView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BitmapUtil {



    companion object{


        /*
         最节省内存的方式读取本地资源
         */
        @JvmStatic
        fun readBitmap( resId:Int):Bitmap?{

            val opt=BitmapFactory.Options()

            opt.inPreferredConfig=Bitmap.Config.RGB_565

            val stream=ToolView.context.resources.openRawResource(resId)

            return  BitmapFactory.decodeStream(stream)
        }


        /*
         图片转成Byte数组
         */
        @JvmStatic
        fun bitmapToBytes(bitmap: Bitmap?): ByteArray? {
            val stream=ByteArrayOutputStream();
            bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)
            return stream.toByteArray()
        }

        /*
          质量压缩bitmap
         */
        @JvmStatic
        fun compressBitmap(bitmap: Bitmap?,flagSize:Int): Bitmap? {

            val stream=ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)
            var options=5

            while (stream.toByteArray().size/1024 > flagSize){
                stream.reset()
                options -= 5
                bitmap?.compress(Bitmap.CompressFormat.JPEG,options,stream)
            }

            val ins=ByteArrayInputStream(stream.toByteArray())

            return BitmapFactory.decodeStream(ins,null,null)

        }


        /*
            drawable 转   bitmap
         */
        @JvmStatic
        fun drawableToBitmap(drawable:Drawable):Bitmap{

            val width=drawable.intrinsicWidth

            val height=drawable.intrinsicHeight

            val config=Bitmap.Config.ARGB_8888

            val bitmap=Bitmap.createBitmap(width,height,config)

            val canvas=Canvas(bitmap)

            drawable.setBounds(0,0,width,height)

            drawable.draw(canvas)

            return  bitmap
        }

        /*
         缩放bitmap(暂不确定：只能缩小)
         //java.lang.IllegalArgumentException: x + width must be <= bitmap.width()
         */
        @JvmStatic
        fun zoomBitmap(bitmap: Bitmap,width:Int,height:Int): Bitmap? {
            val w=bitmap.width

            val h=bitmap.height

            val matrix=Matrix()

            val scaleWidth:Float=if(width>w) w.toFloat()/width.toFloat()  else width.toFloat()/w.toFloat()
            val scaleHeight:Float=if(height>h) h.toFloat()/height.toFloat() else height.toFloat()/h.toFloat()
            matrix.setScale(scaleWidth,scaleHeight)

            return Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true)
        }

        /*
         bitmap 转 drawable
         */
        @JvmStatic
        fun bitmapToDrawable(bitmap: Bitmap): Drawable {
            return BitmapDrawable(ToolView.context.resources,bitmap)
        }



    }

}
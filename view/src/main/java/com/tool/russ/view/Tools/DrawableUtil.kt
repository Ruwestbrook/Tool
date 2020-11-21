package com.tool.russ.view.Tools

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat


/**
author: russell
time: 2020/9/20:21:30
describe：
 */

class DrawableUtil{

    companion object{

        @JvmStatic
        fun tintDrawable(drawable: Drawable, color: Int): Drawable? {
            val wrappedDrawable: Drawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(wrappedDrawable, color)
            return wrappedDrawable
        }

    }
}
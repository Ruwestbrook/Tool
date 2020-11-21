package com.tool.russ.tool

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tool.russ.view.ToolView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {


    @SuppressLint("HandlerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToolView.init(this)
        setContentView(R.layout.activity_main)




    }

}



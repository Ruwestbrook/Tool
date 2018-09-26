package com.tool.russ.tool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tool.russ.view.TxView;

public class MainActivity extends AppCompatActivity {
    private boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TxView view=findViewById(R.id.xview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=!flag;
                view.setSelected(flag);
            }
        });
    }
}

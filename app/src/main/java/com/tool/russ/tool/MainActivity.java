package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tool.russ.view.RefreshView;
import com.tool.russ.view.TxView;

public class MainActivity extends AppCompatActivity {
    private boolean flag=false;
    TxView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=findViewById(R.id.all);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=!flag;
                view.setSelected(flag);
            }
        });
    }
}

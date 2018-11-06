package com.tool.russ.tool;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tool.russ.view.RefreshListener;
import com.tool.russ.view.RefreshView;
import com.tool.russ.view.TxView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RefreshView mRefreshView;
    private Handler handler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        break;
                    case 2:
                        mRefreshView.setRefreshing(false);
                }
            }
        };
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.list);
        mRefreshView=findViewById(R.id.refresh);
        mRefreshView.setOnRefreshListener(new RefreshListener() {

            @Override
            public void refresh() {
                handler.sendEmptyMessageDelayed(2,2000);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false)) {};
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView textView=holder.itemView.findViewById(R.id.text);
                textView.setText("这是第"+(position+1)+"个Item");
            }

            @Override
            public int getItemCount() {
                return 20;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}

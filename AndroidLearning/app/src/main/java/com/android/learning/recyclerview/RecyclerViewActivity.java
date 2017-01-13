package com.android.learning.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.learning.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        initData();
        mAdapter = new RecyclerViewAdapter(this,mData);
        initView();

    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        //mRecyclerView.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //设置为垂直布局，默认的就是垂直布局的
        //layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.HORIZONTAL));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(this));

    }

    private void initData(){
        mData = new ArrayList<String>();
        for(int i=0;i<10;i++){
            mData.add("item"+(i+1));
        }
    }
}

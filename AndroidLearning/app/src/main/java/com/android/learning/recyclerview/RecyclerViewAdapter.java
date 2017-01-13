package com.android.learning.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.learning.R;

import java.util.List;

/**
 * Created by liming.zhang on 2017/1/11.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<String> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context,List<String> data){
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_layout,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        if(mData != null && mData.size()>0){
            return mData.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View view){
            super(view);
            tv = (TextView)view.findViewById(R.id.recyclerview_tv);
        }
    }
}

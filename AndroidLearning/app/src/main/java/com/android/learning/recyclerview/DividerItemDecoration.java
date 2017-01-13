package com.android.learning.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by liming.zhang on 2017/1/11.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int []ATTR = new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation){
        mOrientation = orientation;
        TypedArray typedArray = context.obtainStyledAttributes(ATTR);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);

    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(mOrientation == HORIZONTAL_LIST){
            drawHorizontal(c,parent);
        }else if(mOrientation == VERTICAL_LIST){
            drawVertical(c,parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(mOrientation == VERTICAL_LIST){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else if(mOrientation == HORIZONTAL_LIST){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }

    private void setOrientation(int orientation){
        if(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void drawVertical(Canvas canvas,RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        Log.d("XXXXXXXXXXXXXXXXXX","left = "+left+",right = "+right);
        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(canvas);
        }

    }

    public void drawHorizontal(Canvas canvas,RecyclerView parent){
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight()-parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(canvas);
        }
    }
}

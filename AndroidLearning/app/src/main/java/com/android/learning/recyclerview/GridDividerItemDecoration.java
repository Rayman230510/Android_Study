package com.android.learning.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by liming.zhang on 2017/1/12.
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int []ATTR = new int[]{android.R.attr.listDivider};
    private Context mContext ;
    private Drawable mDivider;

    public GridDividerItemDecoration(Context context) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(ATTR);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    /*@Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int spanCount = getSpan(parent);
        int childCount = parent.getChildCount();
        int pos = -1;
        for(int i=0;i<childCount;i++){
            if(view.equals(parent.getChildAt(i))){
                pos = i;
                break;
            }
        }
        if(isLastRaw(parent,pos,spanCount,childCount)){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }else if(isLastColumn(parent,pos,spanCount,childCount)){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
        }
    }*/

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
        int spanCount = getSpan(parent);
        int childCount = parent.getAdapter().getItemCount();
        Log.d("XXXXXXXXXXXXXXX","parent.getChildCount() = "+parent.getChildCount()
                +",parent.getAdapter.getItemCount = "+parent.getAdapter().getItemCount()
                +",spanCount = "+spanCount+);

        if(isLastRaw(parent,itemPosition,spanCount,childCount)){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }else if(isLastColumn(parent,itemPosition,spanCount,childCount)){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Log.d("XXXXXXXXXXXXXXX","onDraw parent.getChildCount() = "+parent.getChildCount());
        drawHorizontalLine(c,parent);
        drawVerticalLine(c,parent);
    }

    private void drawVerticalLine(Canvas c,RecyclerView parent){
        final int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop()- layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin;
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);

        }
    }

    private void drawHorizontalLine(Canvas c,RecyclerView parent){
        final int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getPaddingLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin+mDivider.getIntrinsicWidth();
            int top = child.getBottom()+layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private int getSpan(RecyclerView parent){
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }

        return spanCount;
    }

    private boolean isLastColumn(RecyclerView parent,int pos,int spanCount,int childCount){

        boolean isLastColumn = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            if ((pos+1) % spanCount == 0) {
                isLastColumn = true;
            }
        }else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL )
            {
                if ((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
                {
                    isLastColumn = true;
                }
            } else
            {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) // 如果是最后一列，则不需要绘制右边
                    isLastColumn = true;
            }
        }
        return isLastColumn;
    }

    private boolean isLastRaw(RecyclerView parent,int pos,int spanCount,int childCount){
        boolean isLastRaw = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount){ // 如果是最后一行，则不需要绘制底部
                isLastRaw = true;
            }
            /*int rawCount = childCount/spanCount;
            if((pos+1)/spanCount == rawCount){
                isLastRaw = true;
            }*/
        }else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL )
            {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    isLastRaw = true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0)
                {
                    isLastRaw = true;
                }
            }
        }
        return isLastRaw;
    }

}

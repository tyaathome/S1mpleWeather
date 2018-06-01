package com.tyaathome.s1mpleweather.ui.adapter.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tyaathome on 2017/10/12.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    // 列宽度
    private int column;
    // 行宽度
    private int row;
    private int count;

    /**
     *
     * @param column 列宽度
     * @param row 行宽度
     * @param count 每行个数
     */
    public SpaceItemDecoration(int column, int row, int count) {
        this.column = column;
        this.row = row;
        this.count = count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildAdapterPosition(view);
        int remainder = index% count;
        if(count == 2) {
            int columnWidth = (int) (column /2.0f);
            if(remainder == 0) {
                outRect.right = columnWidth;
            } else if(remainder == 1) {
                outRect.left = columnWidth;
            }
        } else if(count > 2) {
            int columnWidth = (int) (column /3.0f);
            if(remainder == 0) {
                outRect.right = columnWidth*2;
            } else if(remainder == count -1) {
                outRect.left = columnWidth*2;
            } else {
                outRect.left = columnWidth;
                outRect.right = columnWidth;
            }
        }
        outRect.bottom = row;
    }
}

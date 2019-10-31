package com.sochic.sochic.Util;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.State;

public class SpacesItemDecoration extends ItemDecoration {
    private int row;
    private int space;

    public SpacesItemDecoration(int space, int row) {
        this.space = space;
        this.row = row;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position >= 0) {
            if(row == 1) {
                int column = position % this.row;

                if (position < this.row) {
                    outRect.top = this.space;
                }
                outRect.bottom = this.space;
            }else {
                int column = position % this.row;
                outRect.left = this.space - ((this.space * column) / this.row);
                outRect.right = ((column + 1) * this.space) / this.row;
                if (position < this.row) {
                    outRect.top = this.space;
                }
                outRect.bottom = this.space;
            }

            return;
        }
        outRect.left = 0;
        outRect.right = 0;
        outRect.top = 0;
        outRect.bottom = 0;
    }
}

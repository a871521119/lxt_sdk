package com.lxt.mobile.cell;

import android.support.v7.widget.RecyclerView;

public interface ListCell {
    public void setData(Object data, int position, RecyclerView.Adapter mAdapter);
}

package com.lxt.mobile.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.lxt.sdk.util.LogUtil;

import static android.R.transition.fade;

/**
 * Created by LiWenJiang on 2017/6/7.
 */

public class ToolbarHorizontalScrollView extends HorizontalScrollView {

    private ToolbarHorizontalScrollView toolbar;

    public ToolbarHorizontalScrollView(Context context) {
        super(context);
    }

    public ToolbarHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    public ToolbarHorizontalScrollView getToolbar() {
        return toolbar;
    }

    public void setToolbar(ToolbarHorizontalScrollView toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (toolbar!= null){
            toolbar.scrollTo(x,0);
        }
    }
}

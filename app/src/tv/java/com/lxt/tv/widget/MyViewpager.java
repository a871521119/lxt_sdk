package com.lxt.tv.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewpager extends ViewPager {

	private boolean isPagingEnabled = true;
	public MyViewpager(Context context) {
		super(context);
	}
	public MyViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.isPagingEnabled && super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return this.isPagingEnabled && super.onInterceptTouchEvent(event);
	}

	public void setPagingEnabled(boolean b) {
		this.isPagingEnabled = b;
	}

	@Override
	public void scrollTo(int x, int y){
		super.scrollTo(x, y);
	}
}

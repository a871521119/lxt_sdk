package com.lxt.tv.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lxt.R;

import java.util.List;



/**
 * Created by Administrator on 2017/4/26.
 */

public class PopupView extends RadioGroup implements RadioGroup.OnCheckedChangeListener{

    private View parent;//一级标签

    private List<String> childList;//二级子标签的个数

    private int selectPosition;//选中位置

    private NavigationListener onNavListener;

    public PopupView(Context context) {
        super(context);
        init();
    }

    public PopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundResource(R.drawable.xialahei);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(getResources().getDimensionPixelSize(R.dimen.x30),0,0,0);
        setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        selectPosition = checkedId;
        if (onNavListener != null){
            onNavListener.onNavSelected(parent,group.getChildAt(checkedId),checkedId);
        }
    }


    public void setChildView(View parent, List<String> child) {
        this.parent = parent;
        this.childList = child;
        addChildView();
    }

    private void addChildView() {
        for (int i = 0; i < childList.size();i++){
            RadioButton radio = new RadioButton(getContext());
            radio.setGravity(Gravity.CENTER);
            radio.setId(i);
            radio.setText(childList.get(i));
            radio.setTextColor(getResources().getColor(R.color.white));
            radio.setTextSize(getResources().getDimensionPixelOffset(R.dimen.x16));
            radio.setButtonDrawable(new BitmapDrawable());
            radio.setBackgroundResource(R.drawable.time_content);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(getResources().getDimensionPixelSize(R.dimen.x50),0,0,0);
            addView(radio);
        }
    }


    public int getSelectPosition() {
        return selectPosition;
    }

    public void setOnNavListener(NavigationListener onNavListener) {
        this.onNavListener = onNavListener;
    }

   public interface NavigationListener{
       /**
        * 二级导航选中监听
        * @param parent 一级导航标签
        * @param child 二级导航标签
        * @param position 二级导航选中标签位置
        */
        void onNavSelected(View parent, View child, int position);
    }
}

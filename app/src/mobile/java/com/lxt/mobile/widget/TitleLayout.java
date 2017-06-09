package com.lxt.mobile.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxt.R;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 10:15
 * @description :
 */
public class TitleLayout extends RelativeLayout implements View.OnClickListener {
    /**
     * 是否为普通模式（带返回键）
     */
    boolean isNomalMode = true;
    /**
     * 返回键旁边的文字
     */
    TextView title_left_text;
    /**
     * 返回的图标
     */
    ImageView icon_back;

    /**
     * 标题
     */
    TextView title;

    /**
     * 右边的图片
     */
    ImageView title_right_img;

    /**
     * 右边的文字
     */
    TextView title_right_text;
    /**
     * 返回键听
     */
    OnBackPressListener backPressLister;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        icon_back = (ImageView) findViewById(R.id.title_left_image);
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        title = (TextView) findViewById(R.id.title_content_text);
        title_right_img = (ImageView) findViewById(R.id.title_right_img);
        title_right_text = (TextView) findViewById(R.id.title_right_text);
    }

    /**
     * 设置为模式
     *
     * @param isNomalModes true为普通模式 false为自定义
     */
    public TitleLayout setMode(boolean isNomalModes) {
        isNomalMode = isNomalModes;
        initListener();
        return this;
    }

    /**
     * 设置监听
     */
    void initListener() {
        if (isNomalMode) {
            findViewById(R.id.title_left_layout).setOnClickListener(this);
        }
    }

    /**
     * 设置返回键的图标，默认为箭头
     */
    public TitleLayout setLeftIcon(int resId) {
        icon_back.setImageResource(resId);
        return this;
    }

    /**
     * 设置返回键旁边的文字,默认返回
     */
    public TitleLayout setBackText(String text) {
        title_left_text.setText(text);
        return this;
    }

    /**
     * 设置标题
     */
    public TitleLayout setTitle(String text) {
        title.setText(text);
        return this;
    }

    /**
     * 设置右边的图标
     */
    public TitleLayout setRightIcon(int resId) {
        title_right_img.setImageResource(resId);
        return this;
    }

    /**
     * 右边的文字
     */
    public TitleLayout setRightText(String text) {
        title_right_text.setText(text);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_layout:
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
                if (backPressLister != null) {
                    backPressLister.onPressed(v);
                }
                break;
            default:
                break;
        }
    }

    public TitleLayout setOnBackPressLister(OnBackPressListener backPressLister) {
        this.backPressLister = backPressLister;
        return this;
    }

    public interface OnBackPressListener {
        public void onPressed(View v);
    }
}

package com.lxt.mobile.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.mobile.adapter.GuideAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.sdk.cache.SharedPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 11:57
 * @description : 欢迎页面
 */
public class WelcomeActivity extends MBaseActivity {
    private boolean misScrolled;
    private int[] mImgIds = new int[]{
            R.mipmap.viewpagera,
            R.mipmap.viewpagerb,
            R.mipmap.viewpagerc,
            R.mipmap.viewpagerd};

    private List<ImageView> mImages;

    //跳过提示
    private TextView viewpagerSkipText = null;
    //引导页
    private ViewPager mViewPager;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void initView() {
        mImages = new ArrayList<ImageView>();
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewpagerSkipText = (TextView) findViewById(R.id.viewpager_skip_text);
        GuideAdapter mGuideAdapter = new GuideAdapter(mContext, mImages, mImgIds);
        mViewPager.setAdapter(mGuideAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        viewpagerSkipText.setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.viewpager_skip_text:
                SharedPreference.setData("welcomeIsShown", "1");
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:

                break;
        }
    }
}

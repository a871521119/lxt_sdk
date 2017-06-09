package com.lxt.tv.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioGroup;

import com.lxt.R;
import com.lxt.base.BaseFragmentAdapter;
import com.lxt.sdk.util.LogUtil;
import com.lxt.tv.base.MBaseActivity;
import com.lxt.tv.fragment.BookingClassFragment;
import com.lxt.tv.fragment.ClassRecordFragment;
import com.lxt.tv.fragment.MyClassFragment;
import com.lxt.tv.fragment.PersonalCenterFragment;
import com.lxt.tv.widget.CustomDialog;
import com.lxt.tv.widget.MyViewpager;
import com.lxt.tv.widget.NavigationBar;


import java.util.ArrayList;
import java.util.List;



public class HomeActivity extends MBaseActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener,ViewTreeObserver.OnGlobalFocusChangeListener {
    /**
     * 导航条父布局
     */
    private RadioGroup navGroupLayout;
    /**
     * ViewPager的适配器
     */
    BaseFragmentAdapter pagerAdapter;
    /**
     * viewpager的Fragment集合
     */
    private List<Fragment> mFragments;
    /**
     * ViewPager
     */
    private MyViewpager mViewpager;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {
        navGroupLayout = (RadioGroup) findViewById(R.id.navigation_layout);
        navGroupLayout.setOnCheckedChangeListener(this);
        navGroupLayout.getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        mViewpager= (MyViewpager) findViewById(R.id.content_group_viewpager);
        viewPagerInit();
        ((NavigationBar)navGroupLayout.getChildAt(0)).setChecked(true);
    }

    /**
     * viewpager 初始化
     * */
    private void viewPagerInit(){
        //viewpager 要展示的Fragment
        mFragments = new ArrayList<>();
        mFragments.add(new BookingClassFragment());
        mFragments.add(new MyClassFragment());
        mFragments.add(new ClassRecordFragment());
        mFragments.add(new PersonalCenterFragment());
        //viewPager适配器
        pagerAdapter = new BaseFragmentAdapter(getSupportFragmentManager(),mFragments);
        mViewpager.setAdapter(pagerAdapter);
        //预加载
        mViewpager.setOffscreenPageLimit(4);
        //遥控焦点 false
        mViewpager.setFocusable(false);
        //viewpager滑动回调
        mViewpager.addOnPageChangeListener(this);
        mViewpager.setPagingEnabled(false);
    }


    //================ViewPager回调事件=============================================================
    /**
     * ViewPager 点击事件
     * 当在一个页面滚动时，调用此方法
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    /**
     * ViewPager 点击事件
     * 当一个页面即将被加载时，调用此方法
     */
    @Override
    public void onPageSelected(int position) {
        NavigationBar navigationBar =(NavigationBar)navGroupLayout.getChildAt(position);
        if (!navigationBar.isChecked()){
            navigationBar.setChecked(true);//Fragment切换时同时切换导航Item
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        LogUtil.i("HomeActivity onActivityResult");
//    }

    /**
     * ViewPager 点击事件
     * 目标加载完毕，调用此方法
     */
    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new CustomDialog.Builder(mContext).setMessage(getString(R.string.home_dialog_title))
                    .setPositiveButton(getString(R.string.dialog_sureBtn), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    }).setNegativeButton(getString(R.string.dialog_NegBtn),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            }).create().show();
            return false;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int fragmentIndex = checkedId -1;
        if(mViewpager.getCurrentItem() != fragmentIndex){
            mViewpager.setCurrentItem(fragmentIndex);//切换ViewPager显示的Fragment
        }
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        LogUtil.i("oldFocus="+oldFocus +  "   newFocus="+newFocus);
        if(isNavBarFocus(oldFocus) &&
                isNavBarFocus(newFocus)){
            ((NavigationBar)newFocus).setChecked(true);
        }else if(!isNavBarFocus(oldFocus) &&
                isNavBarFocus(newFocus)){ //导航重新获取焦点时，把焦点给选中的子导航
            navGroupLayout.getChildAt(mViewpager.getCurrentItem()).requestFocus();
        }
    }

    /**
     * 是否导航的焦点改变
     * @param view
     * @return
     */
    private boolean isNavBarFocus(View view){
        if (view != null && view instanceof NavigationBar){
            return true;
        }
        return false;
    }
}

package com.lxt.mobile.activity;

import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lxt.R;
import com.lxt.base.AppManager;
import com.lxt.base.BaseFragmentAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.fragment.MyClassFragment;
import com.lxt.mobile.fragment.OrderClassFragment;
import com.lxt.mobile.widget.Home_SlidingMenu;
import com.lxt.mobile.widget.LxtDialog;
import com.lxt.mobile.widget.NoScrollViewPager;
import com.lxt.mobile.widget.TitleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 11:57
 * @description : 主页面
 */
public class HomePageActivity extends MBaseActivity implements RadioGroup.OnCheckedChangeListener {
    NoScrollViewPager pager;
    List<Fragment> mFragments;
    TitleLayout mTitleLayout;
    OrderClassFragment orderClassFragment;
    MyClassFragment myClassFragment;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_home_page);
    }

    @Override
    public void initView() {
        ((RadioButton) findViewById(R.id.home_rd1)).setChecked(true);
        pager = (NoScrollViewPager) findViewById(R.id.home_viewpager);
        pager.setCanScroll(false);
        mFragments = new ArrayList<>();
        mTitleLayout = (TitleLayout) findViewById(R.id.title);
        ((RadioGroup) findViewById(R.id.home_RadioGroup)).setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        initFragments();
        setAdapter();
        mTitleLayout.setMode(false).setBackText("").setLeftIcon(R.mipmap.title_left_menu).setTitle("预约上课");
    }

    @Override
    public void initListener() {
        super.initListener();
        mTitleLayout.findViewById(R.id.title_left_layout).setOnClickListener(this);
    }

    void initFragments() {
        orderClassFragment=OrderClassFragment.newInstance();
        myClassFragment=MyClassFragment.newInstance();
        mFragments.add(orderClassFragment);
        mFragments.add(myClassFragment);
    }

    void setAdapter() {
        pager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mFragments));
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.title_left_layout:
                new Home_SlidingMenu(this, R.style.dialog).show();
                break;
            default:

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.home_rd1) {
            mTitleLayout.setTitle("预约上课");
            pager.setCurrentItem(0);
            orderClassFragment.getData();
        } else {
            mTitleLayout.setTitle("我的课程");
            pager.setCurrentItem(1);
            myClassFragment.getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pager.getCurrentItem()==0){
            orderClassFragment.getData();
        }else {
            myClassFragment.getData();
        }
    }
    /**
     * 返回键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //moveTaskToBack(true);
            new LxtDialog.Builder(mContext).setMessage(getString(R.string.dialog_sureToCancel))
                    .setPositiveButton(getString(R.string.dialog_sureBut), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AppManager.getAppManager().finishAllActivity();
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    }).setNegativeButton(getString(R.string.dialog_cancelBut),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    }).create().show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

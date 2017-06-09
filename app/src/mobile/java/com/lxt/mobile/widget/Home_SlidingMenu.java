package com.lxt.mobile.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

import com.lxt.R;
import com.lxt.mobile.activity.ClassRecordActivity;
import com.lxt.mobile.activity.SelfInfoActivity;
import com.lxt.mobile.activity.SystemSettingActivity;
import com.lxt.mobile.activity.WatchListActivity;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 14:50
 * @description :
 */
public class Home_SlidingMenu extends Dialog implements View.OnClickListener {
    public Home_SlidingMenu(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView();
        initListner();
    }

    void initView() {
        setContentView(R.layout.home_menu_layout);
    }

    void initListner() {
        findViewById(R.id.show_user_course).setOnClickListener(this);//课程记录
        findViewById(R.id.show_user_growth_curve).setOnClickListener(this);//成长曲线
        //我的关注
        findViewById(R.id.show_user_focus_on).setOnClickListener(this);
        //个人信息
        findViewById(R.id.show_user_data).setOnClickListener(this);
        //系统设置
        findViewById(R.id.show_system_set_up).setOnClickListener(this);
        findViewById(R.id.sliding_dissmiss).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_user_course:
                getContext().startActivity(new Intent(getContext(), ClassRecordActivity.class));
                break;
            case R.id.show_user_growth_curve:

                break;
            case R.id.show_user_focus_on:
                getContext().startActivity(new Intent(getContext(), WatchListActivity.class));
                break;
            case R.id.show_user_data:
                getContext().startActivity(new Intent(getContext(), SelfInfoActivity.class));
                break;
            case R.id.show_system_set_up:
                getContext().startActivity(new Intent(getContext(), SystemSettingActivity.class));
                //getContext().startActivity(new Intent(getContext(), TeacherAppraiseActivity.class));
                break;
            default:
                dismiss();
                break;
        }

    }
}

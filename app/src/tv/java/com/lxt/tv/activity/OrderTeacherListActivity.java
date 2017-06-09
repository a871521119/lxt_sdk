package com.lxt.tv.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.TeacherBean;

import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.LogUtil;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.tv.adapter.OrderTeacherListAdapter;
import com.lxt.tv.base.MBaseActivity;
import com.lxt.tv.widget.PopupView;
import com.lxt.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderTeacherListActivity extends MBaseActivity implements
        CheckBox.OnCheckedChangeListener,PopupView.NavigationListener{

    private LinearLayout navGroup;//头部选择标签
    private PopupWindow time_ppw, sex_ppw;
    private ViewPager mViewPager;

    private List<String> dates;//当天-14天以内的日期: yyyy-MM-dd
    private List<String> day;//当天-14天以内的几号: dd
    private int selectedDatePosition;//选中日期的位置
    //参数
    private String Lesson_guid;
    private String book_name;
    private String book_id;
    //时间戳
    private long timestamp;
    //男女 状态
    private int sexState = 0;
    //是否关注
    private int isshoucang = 0;
    //老师集合
    private List<TeacherBean> mTeacherBeanList = new ArrayList<>();
    //adapter
    private OrderTeacherListAdapter mOrderTeacherListAdapter;
    //无数据页面
    private LinearLayout errorToastLayout;
    private ImageView errorToastImage;
    private TextView errorToastText;

    private int nowPage = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 88:
                    Bundle bundle = msg.getData();
                    String newteacher_guid = bundle.getString("teacher_guid");
                    Intent mIntent = new Intent(OrderTeacherListActivity.this,YuYueTimeDialogActivity.class);
                    mIntent.putExtra("teacher_guid",newteacher_guid);
                    mIntent.putExtra("long_time",dates.get(selectedDatePosition));
                    mIntent.putExtra("book_name",book_name);
                    mIntent.putExtra("book_id",book_id);
                    mIntent.putExtra("Lesson_guid",Lesson_guid);
                    startActivity(mIntent);
                    break;
            }

        }
    };


    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_order_teacher_list);
        Intent it = getIntent();
        Lesson_guid = it.getStringExtra(LxtParameters.Key.LESSON_GUID);
        book_name = it.getStringExtra("book_name");
        book_id = it.getStringExtra("book_id");
    }


    @Override
    public void initView() {

        navGroup = (LinearLayout) findViewById(R.id.navGroup);
        for (int i =0; i < navGroup.getChildCount();i++){
            CheckBox box = (CheckBox) navGroup.getChildAt(i);
            box .setId(i);
            box.setOnCheckedChangeListener(this);
            if(i == 0){
                box.setText(TimeUtil.getNextDay(0,TimeUtil.dateFormatYMD));
            }else if(i == 1){
                box.setText("不限");
            }
        }
        mViewPager = (ViewPager) findViewById(R.id.order_list_viewpager);
        // 1.设置幕后item的缓存数目
        mViewPager.setOffscreenPageLimit(5);
        // 2.设置页与页之间的间距
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.x100));


        errorToastLayout = (LinearLayout) findViewById(R.id.loading_error_layout);
        errorToastLayout.setVisibility(View.GONE);
        errorToastImage = (ImageView) findViewById(R.id.error_toast_image);
        errorToastText = (TextView) findViewById(R.id.error_toast_text);
        initDate();

    }

    private void initDate() {
        dates = new ArrayList<>();
        day = new ArrayList<>();
        for(int i =0;i<14;i++){
            dates.add(TimeUtil.getNextDay(i,TimeUtil.dateFormatYMD));
            day.add(TimeUtil.getNextDay(i,TimeUtil.dateFormatD));
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        errorToastImage.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getTeacher();
    }





    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.error_toast_image:
                //刷新
                getTeacher();
                break;
        }
    }


    /**
     * 下拉筛选
     */
    private PopupWindow showTimeView(View view,List<String> list, PopupView.NavigationListener listener) {
        PopupView popupView = new PopupView(this);
        popupView.setChildView(view,list);
        popupView.setOnNavListener(listener);
        PopupWindow ppw = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());
        return ppw;
    }

    /**
     * 关闭PopupWindow
     * @param pop
     */
    private void dismissPopupWindow(PopupWindow pop){
        if(pop != null && pop.isShowing())
            pop.dismiss();
    }


    /**
     * 获取老师数据
     */
    private void getTeacher() {
        showProgressDialog();
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getTeacherList(SharedPreference.getData(LxtParameters.Key.GUID),
                Lesson_guid,SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                String.valueOf(TimeUtil.getTimeStamp(dates.get(selectedDatePosition), TimeUtil.dateFormatYMD)),
                getTimeTag(),
                String.valueOf(sexState),String.valueOf(isshoucang),String.valueOf(nowPage),
                SharedPreference.getData(LxtParameters.Key.TOKEN));
    }


    private String getTimeTag(){
        if(TextUtils.equals(TimeUtil.getCurrentDate(TimeUtil.dateFormatYMD),dates.get(selectedDatePosition))){
            return Utils.getShrottime(Utils.getRealTime(TimeUtil.getCurrentDate(TimeUtil.dateFormatHM)));
        }
        return "1";
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        TeacherBean teacher = (TeacherBean) data.result;
        if (teacher.getCount() <= 0) {
            mViewPager.setVisibility(View.GONE);
            errorToastLayout.setVisibility(View.VISIBLE);
            errorToastImage.setBackgroundResource(R.drawable.no_clock);
            errorToastImage.setFocusable(false);
            errorToastImage.setClickable(false);
            errorToastText.setText(getString(R.string.order_noTeatureAtThisTime));
        }else {
            if (nowPage == 1)
                mTeacherBeanList.clear();
            mTeacherBeanList.addAll(teacher.getTeacher());
            errorToastLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            if(mOrderTeacherListAdapter==null){
                mOrderTeacherListAdapter = new OrderTeacherListAdapter
                        (OrderTeacherListActivity.this, mTeacherBeanList, mHandler);
                mViewPager.setAdapter(mOrderTeacherListAdapter);
            }else {
                mOrderTeacherListAdapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        errorToastLayout.setVisibility(View.VISIBLE);
        errorToastText.setText(getString(R.string.dataFaildText));
        errorToastImage.setBackgroundResource(R.drawable.loading_failure_background);
        errorToastImage.setFocusable(true);
        errorToastImage.setClickable(true);
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        errorToastImage.setBackgroundResource(R.drawable.loading_failure_background);
        errorToastImage.setFocusable(true);
        errorToastImage.setClickable(true);
        errorToastLayout.setVisibility(View.VISIBLE);

    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case 0://时间
                if(time_ppw == null){
                    time_ppw = showTimeView(buttonView,day,this);
                }
                time_ppw.showAsDropDown(buttonView);
                ((PopupView)time_ppw.getContentView()).getChildAt(selectedDatePosition).requestFocus();//选中的获取焦点
                break;
            case 1://性别
                if(sex_ppw == null){
                    sex_ppw = showTimeView(buttonView,getSexList(),this);
                }
                sex_ppw.showAsDropDown(buttonView);
                ((PopupView)sex_ppw.getContentView()).getChildAt(sexState).requestFocus();//选中的获取焦点
                break;
            case 2://关注
                if (isChecked){
                    isshoucang = 1;
                }else {
                    isshoucang = 0;
                }
                nowPage =1;
                getTeacher();
                break;
        }
    }


    /**
     * 性别集合
     * @return
     */
    private List<String> getSexList(){
        List<String> sex = new ArrayList<>();
        sex.add("不限");
        sex.add("男");
        sex.add("女");
        return sex;
    }



    @Override
    public void onNavSelected(View parent, View child,int position) {
        switch (parent.getId()){
            case 0://日期
                ((CheckBox)parent).setText(dates.get(position));
                dismissPopupWindow(time_ppw);
                selectedDatePosition = position;
                nowPage =1;
                getTeacher();
                break;
            case 1://性别
                sexState = position;
                nowPage =1;
                ((CheckBox)parent).setText(((RadioButton)child).getText());
                dismissPopupWindow(sex_ppw);
                getTeacher();
                break;
        }
    }
}

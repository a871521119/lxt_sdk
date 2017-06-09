package com.lxt.mobile.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.BookingClassBeen;
import com.lxt.been.TeacherBean;
import com.lxt.mobile.adapter.OrderTeacherListAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.PullRefreshManager;
import com.lxt.mobile.widget.timePacker.DateObject;
import com.lxt.mobile.widget.timePacker.DatePicker;
import com.lxt.mobile.widget.timePacker.TimePicker;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.LogUtil;
import com.lxt.sdk.util.LxtConfig;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.util.Utils;

import java.util.Date;

import static com.baijiahulian.livecore.context.LPConstants.d;
import static com.lxt.sdk.util.LxtConfig.AURORA_PUSH_ID;
import static com.lxt.sdk.util.TimeUtil.getCurrentDate;
import static com.lxt.sdk.util.TimeUtil.isToday;
import static com.lxt.util.Utils.getRealTime;

/**
 * Created by LiWenJiang on 2017/5/22.
 */

public class OrderTeacherListActivity extends MBaseActivity implements PullRefreshManager.TwinkRefreshLoadListener,
        OrderTeacherListAdapter.BookClassListener,AdapterView.OnItemClickListener{

    private BookingClassBeen bookCourse;

    private OrderTeacherListAdapter orderTeacherListAdapter;

    private ListView teacherListView;

    private PullRefreshManager pullRefreshManager;
    private TwinklingRefreshLayout refreshLayout;

    private TextView dateTv;//时间选择

    private Button sizerBtn;//筛选按钮

    private int page = 1;
    private int pageSize = 10;
    private int count;//总数据

    private PopupWindow sizerWindow;//筛选条件View
    private RadioGroup sexRadioGroup;
    private CheckBox shoucang;

    private PopupWindow dateTimeWindow;

    private ImageView title_right_img;

    private String today = "今天";

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_order_teacher_list);
    }

    @Override
    public void initView() {
        //title
        findViewById(R.id.title_left_layout).setOnClickListener(this);//上面左边
        TextView title_content = (TextView) findViewById(R.id.title_content_text);
        title_content.setText("教师列表");//上面中间
        title_right_img = (ImageView) findViewById(R.id.title_right_img);//右边图片
        title_right_img.setVisibility(View.VISIBLE);
        title_right_img.setImageResource(R.mipmap.white_heart);
        title_right_img.setOnClickListener(this);
        dateTv = (TextView) findViewById(R.id.order_teacher_time);
        dateTv.setOnClickListener(this);

        dateTv.setText("上课时间："+TimeUtil.getCurrentDate(TimeUtil.dateFormatMDofChinese) +
                " ( "+TimeUtil.getWeekNumber(TimeUtil.getCurrentDate(TimeUtil.dateFormatYMD),TimeUtil.dateFormatYMD).replaceFirst("周","星期")+" ) "
                +  getRealTime(TimeUtil.getCurrentDate(TimeUtil.dateFormatHM)));
        sizerBtn = (Button) findViewById(R.id.bt_select);
        sizerBtn.setOnClickListener(this);
        teacherListView = (ListView) findViewById(R.id.ordered_teacher_listview);
        teacherListView.setOnItemClickListener(this);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.ordered_teacher_refresh);
        pullRefreshManager = new PullRefreshManager(refreshLayout,this);
        pullRefreshManager.getDefaultRefreshLayout();
    }

    @Override
    public void initData() {
        super.initData();
        bookCourse = (BookingClassBeen) getIntent().getSerializableExtra("course");
        dateStamp = String.valueOf(TimeUtil.getTimeStamp(TimeUtil.getCurrentDate(TimeUtil.dateFormatYMD), TimeUtil.dateFormatYMD));
        shortTime = Utils.getShrottime(getRealTime(TimeUtil.getCurrentDate(TimeUtil.dateFormatHM)));
        getTeacherList();
    }

    private String dateStamp;//日期0点时间戳
    private String shortTime;//时间段
    private void getTeacherList(){
        showProgressDialog();
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getTeacherList(SharedPreference.getData(LxtParameters.Key.GUID),
                bookCourse.getLesson_guid(),
                SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                dateStamp, shortTime,
                getSex(),isCollect(),String.valueOf(page), SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(data.action,LxtParameters.Action.TEACHERLIST)){
            TeacherBean teacher = (TeacherBean) data.result;
            if (teacher.getCount() > 0){
                count = teacher.getCount();
                if (orderTeacherListAdapter == null){
                    orderTeacherListAdapter = new OrderTeacherListAdapter(this,teacher.getTeacher(),this);
                    teacherListView.setAdapter(orderTeacherListAdapter);
                }else {
                    orderTeacherListAdapter.updateData(teacher.getTeacher());
                }
            }else {
                showToast("当前时间段没有老师，请选择其他时间或筛选条件");
            }
        }else if (TextUtils.equals(data.action, LxtParameters.Action.BOOKINGCOURSE)){
            if (data.result instanceof String){
                showToast((String) data.result);
            }
        }

    }

    @Override
    public void onRefresh() {
        if (page != 1){
            page = 1;
            getTeacherList();
        }else {
            pullRefreshManager.stopRefresh();
        }
    }

    @Override
    public void onLoadMore() {
        if(page * pageSize < count){
            page++;
            getTeacherList();
        }else {
            pullRefreshManager.stopRefresh();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()){
            case R.id.title_left_layout:
                finish();
                break;
            case R.id.order_teacher_time:
                if (dateTimeWindow == null){
                    dateTimeWindow = new PopupWindow(getDateTimeView() ,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    dateTimeWindow.setOutsideTouchable(true);
                }
                dateTimeWindow.showAtLocation(findViewById(R.id.Rl_all),Gravity.BOTTOM,0,0);
                isScrollTime = true;
                break;
            case R.id.bt_select:
                if (sizerWindow == null){
                    sizerWindow = new PopupWindow(getSizerView(), LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    sizerWindow.setOutsideTouchable(true);
                }
                sizerWindow.showAsDropDown(v);
                break;
            case R.id.sizer:
                resetInit();
                getTeacherList();
                break;
            case R.id.title_right_img://关注教师列表
                Intent intent = new Intent(this, WatchListActivity.class);
                intent.putExtra(LxtParameters.Key.NAME, bookCourse.getName());
                intent.putExtra(LxtParameters.Key.LESSON_GUID, bookCourse.getLesson_guid());
                intent.putExtra("fromList", "2");
                startActivity(intent);
                break;
            case R.id.tv_ok://选择日期和时间确认
                DateObject dateObject = datePicker.getSeletedItem();
                if (dateObject != null){
                    String[] dates = (dateObject.getItem().trim().replaceAll("\\s+","#")).split("#");
                    String[] times = getTitleTime();
                    resetInit();
                    dateTv.setText("上课时间："+dates[0] +
                            " ( "+ (TextUtils.equals(dates[1],today)?
                            TimeUtil.getWeekNumber(TimeUtil.getCurrentDate(TimeUtil.dateFormatYMD),TimeUtil.dateFormatYMD).replaceFirst("周","星期") : dates[1])+" ) "
                            +  times[0]);
                    dateStamp = String.valueOf(TimeUtil.getTimeStamp(dateObject.getYear()+"年"+dates[0], TimeUtil.dateFormatYMDofChinese));
                    shortTime = Utils.getShrottime(times[0]);
                    getTeacherList();
                }else {
                    showToast("选择日期或者时间不正确，请您稍后再试");
                }

                break;
        }
    }

    private DatePicker datePicker;
    private TimePicker timePicker;
    /**
     * 日期时间选择控件
     * @return
     */
    private View getDateTimeView() {
        View view = LayoutInflater.from(this).inflate(R.layout.teachers_datetime_select,null);
        datePicker = (DatePicker) view.findViewById(R.id.dp_test);
        datePicker.setDateAdapter(new Date().getTime(),14);
        datePicker.setOnChangeListener(dateOnChangeListener);
        timePicker = (TimePicker) view.findViewById(R.id.tp_test);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(-getResources().getDimensionPixelOffset(R.dimen.x20),
                getResources().getDimensionPixelOffset(R.dimen.y10),0,0);
        timePicker.setLayoutParams(lp);
        timePicker.setOnChangeListener(timeOnChangeListener);
        view.findViewById(R.id.tv_ok).setOnClickListener(this);
        setCurrentDateTime();
        return view;
    }
    /**日期滚动监听*/
    DatePicker.OnChangeListener dateOnChangeListener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            setCurrentDateTime();

        }
    };
    private boolean isScrollTime = false;//是否是手势滑动时间控件
    /**时间滚动监听*/
    TimePicker.OnChangeListener timeOnChangeListener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            if (isScrollTime)
                setCurrentDateTime();
        }
    };



    /**
     * 根据选中的Item获取顶部显示的时间
     * @return
     */
    private String[] getTitleTime(){

        DateObject timeObject = timePicker.getSeletedItem();
        String[] times = new String[1];
        if (timeObject.getItem().contains("不限")){
            times[0] = "不限";
        }else {
            times = timeObject.getItem().split("分");
        }
        return times;
    }

    /**
     *设置今天的可以选择的时间段
     */
    private void setCurrentDateTime(){
        if (datePicker.getSeletedItem().getItem().contains(today)){
            String[] currentTime = Utils.getRealTime(TimeUtil.getCurrentDate(TimeUtil.dateFormatHM)).split(":");
            DateObject time =  timePicker.getSeletedItem();
            if (!time.getItem().contains("不限")){
                if (time.getHour() <= Integer.parseInt(currentTime[0])
                        && time.getMinute() <= Integer.parseInt(currentTime[1])){
                    timePicker.setCurrentItem(Integer.parseInt(currentTime[0]),Integer.parseInt(currentTime[1]));
                }
            }
        }
    }



    /**
     * 重置初始化数据
     */
    private void resetInit(){
        page = 1;
        orderTeacherListAdapter = null;
        if (sizerWindow != null && sizerWindow.isShowing())
            sizerWindow.dismiss();
        if (dateTimeWindow != null && dateTimeWindow.isShowing()){
            dateTimeWindow.dismiss();
            isScrollTime = false;
        }
    }

    /**
     * 筛选条件View
     * @return
     */
    private View getSizerView(){
        View view = LayoutInflater.from(this).inflate(R.layout.pop_teacher_sizer,null);
        sexRadioGroup = (RadioGroup) view.findViewById(R.id.sexRadioGroup);
        shoucang = (CheckBox) view.findViewById(R.id.shoucang);
        view.findViewById(R.id.sizer).setOnClickListener(this);
        return view;
    }
    /**获取性别**/
    private String getSex(){
        if (sexRadioGroup != null){
            if (sexRadioGroup.getCheckedRadioButtonId() != -1)
                return String.valueOf(sexRadioGroup.getCheckedRadioButtonId()-1);
        }
        return "0";
    }

    /**是否收藏*/
    private String isCollect(){
        if (shoucang != null){
            if (shoucang.isChecked())
                return "1";
        }
        return "0";
    }

    @Override
    public void onBookClassListener(TeacherBean teacher) {
        showProgressDialog("正在预约...");
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_bookCourse(SharedPreference.getData(LxtParameters.Key.GUID),
                teacher.getTeacher_guid(),SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                String.valueOf(teacher.getLesson_list_id()),bookCourse.getLesson_guid(),
                String.valueOf(teacher.getBook_id()),
                TimeUtil.getStrTime(teacher.getStartTime()+"", TimeUtil.dateFormatYMD),
                Utils.getShrottime(TimeUtil.getStrTime(teacher.getStartTime()+"", TimeUtil.dateFormatHM)),
                LxtConfig.AURORA_PUSH_ID,"1",SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (orderTeacherListAdapter != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(LxtParameters.Key.TEACHER_GUID,orderTeacherListAdapter.getItem(position));
            startActivity(TeacherDetailActivity.class,bundle);
        }

    }
}

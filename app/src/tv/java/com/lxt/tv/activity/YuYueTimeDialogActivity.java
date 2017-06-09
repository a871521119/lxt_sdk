package com.lxt.tv.activity;


import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.BookTime;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.tv.adapter.YuyueTimeAdapter;
import com.lxt.tv.base.MBaseActivity;
import com.lxt.util.Utils;

import java.util.List;


/**
 * 获取可预约时间
 */
public class YuYueTimeDialogActivity extends MBaseActivity implements View.OnClickListener{

    private ListView listview;
    //参数
    private String teacher_guid;
    private String long_time;
    private String book_name;
    private String book_id;
    private String lession_guid;
   private Dialog bookDialog;
    //adapter
    private YuyueTimeAdapter mYuyueTimeAdapter;

    private BookTime selectedBookTime;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.yuyue_dialog);
    }



    @Override
    public void initView() {
        listview = (ListView) findViewById(R.id.yuyue_dialog_listview);
        //去除下划线
        listview.setDividerHeight(0);
        //Item点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBookTime = (BookTime) adapterView.getAdapter().getItem(i);
                getBookDialog(selectedBookTime.time);
            }
        });

        Intent it = getIntent();
        teacher_guid =it.getStringExtra("teacher_guid");
        lession_guid =it.getStringExtra("Lesson_guid");
        long_time = it.getStringExtra("long_time");
        book_name = it.getStringExtra("book_name");
        book_id = it.getStringExtra("book_id");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getLessonTime(SharedPreference.getData(LxtParameters.Key.GUID),
                teacher_guid,String.valueOf(TimeUtil.getTimeStamp(long_time,TimeUtil.dateFormatYMD)),
                SharedPreference.getData(LxtParameters.Key.TOKEN));
//        1494518400
//        1494604800
    }

    private void getBookDialog(String time){
        bookDialog = new Dialog(this,R.style.bookDialog);
        View bookView = LayoutInflater.from(this).inflate(R.layout.order_dialog,null);
        TextView date = (TextView) bookView.findViewById(R.id.order_time);
        date.setText(long_time.substring(long_time.indexOf("-")+1)+" ("+TimeUtil.getWeekNumber(long_time,TimeUtil.dateFormatYMD)+")  "+time);
        TextView name = (TextView) bookView.findViewById(R.id.order_name);
        name.setText(book_name);
        bookView.findViewById(R.id.order_cancle).setOnClickListener(this);
        bookView.findViewById(R.id.order_confirm).setOnClickListener(this);
        bookView.findViewById(R.id.order_confirm).requestFocus();
        bookDialog.setContentView(bookView);
        bookDialog.show();
    }

    private void dismissBookDialog(){
        if (bookDialog != null && bookDialog.isShowing()){
            bookDialog.dismiss();
            bookDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.order_cancle:
                dismissBookDialog();
                break;
            case R.id.order_confirm:
                LxtHttp.getInstance().setCallBackListener(this);
                LxtHttp.getInstance().lxt_bookCourse(SharedPreference.getData(LxtParameters.Key.GUID),
                        teacher_guid,SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                        String.valueOf(selectedBookTime.lesson_list_id),
                        lession_guid,book_id,String.valueOf(TimeUtil.getTimeStamp(long_time,TimeUtil.dateFormatYMD)),
                        Utils.getShrottime(selectedBookTime.time),"2","1",SharedPreference.getData(LxtParameters.Key.TOKEN));
                break;
        }
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(data.action, LxtParameters.Action.LESSONTIME)){
            List<BookTime> list = (List<BookTime>) data.result;
            if(list.size()<=0){
                showToast("没有可预约的时间");
                finish();
            }else{
                mYuyueTimeAdapter = new YuyueTimeAdapter(YuYueTimeDialogActivity.this,list);
                listview.setAdapter(mYuyueTimeAdapter);
            }
        }else if(TextUtils.equals(data.action, LxtParameters.Action.BOOKINGCOURSE)){
            showToast("预约成功");
            dismissBookDialog();
            finish();
        }
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        finish();
    }
}

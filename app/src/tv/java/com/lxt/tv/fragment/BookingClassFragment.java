package com.lxt.tv.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.lxt.R;
import com.lxt.been.BookingClassBeen;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.tv.activity.OrderTeacherListActivity;
import com.lxt.tv.adapter.YuYuePageAdapter;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseFragment;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 李文江
 * @create-time : 2017/3/29 11:16
 * @description :
 */
public class BookingClassFragment extends MBaseFragment {
    private View view;
    /* 预约上课的数据 */
    List<BookingClassBeen> mBookingClassBeensList = new ArrayList<>();

    /* 预约btn*/
    private Button yuyue_btn;
    /* 时间格式化*/
    private String timeFormat;
    /*适配器*/
    private YuYuePageAdapter mYuYuePageAdapter;

    @Override
    public View setContentLayout(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_booking_class, null);
        return view;
    }

    @Override
    public void initView() {
        yuyue_btn = (Button) view.findViewById(R.id.yuyue_btn);
        timeFormat = TimeUtil.formatData(TimeUtil.dateFormatYMD, System.currentTimeMillis() / 1000);
        initCustomViewPager(R.id.border,R.id.customViewPager,5,-getResources().getDimensionPixelOffset(R.dimen.x160));

    }

    @Override
    protected void onPageSelected(int position) {
        super.onPageSelected(position);
        if (mBookingClassBeensList.size() != 0) {
            setBookingBtn(mBookingClassBeensList.get(position));
        }
    }

    @Override
    public void load() {
        getHttpResult(LxtParameters.Action.MYLESSONLIST,null);
    }

    @Override
    public void initListener() {
        super.initListener();
        yuyue_btn.setOnClickListener(this);
    }

    @Override
    public void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.error_toast_image:
                getHttpResult(LxtParameters.Action.MYLESSONLIST,null);
                break;
            case R.id.customViewPager:
                setIntents(getCurrentItem());
                break;
            case R.id.yuyue_btn:
                setIntents(getCurrentItem());
                break;
        }
    }

    /**
     * 跳转处理
     */
    public void setIntents(int position) {
        if (position != -1){
            Intent it = new Intent(getActivity(), OrderTeacherListActivity.class);
            it.putExtra(LxtParameters.Key.LESSON_GUID, mBookingClassBeensList.get(position).getLesson_guid());
            it.putExtra("book_name", mBookingClassBeensList.get(position).getName());
            it.putExtra("book_id", mBookingClassBeensList.get(position).getBook_id());
            getActivity().startActivity(it);
        }
    }


    @Override
    public void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        mBookingClassBeensList = (List<BookingClassBeen>) data.result;
        if (mBookingClassBeensList.size() == 0) {
            addErrorView(data.action,getString(R.string.bookingClass_state_notBuyClass));
        } else {
            setBookingBtn(mBookingClassBeensList.get(0));
            mYuYuePageAdapter = new YuYuePageAdapter(getActivity(), mBookingClassBeensList);
            setViewPagerAdapter(mYuYuePageAdapter);
        }
    }

    /**
     * 设置预约按钮提示文字(待修改)
     * @param book
     */
    private void setBookingBtn(BookingClassBeen book){
//        //结束时间 小于 当前时间 已过期
//        if (Long.parseLong(book.getEndTime()) <
//                TimeUtil.getTimeStamp(timeFormat, Constants.TIME_TURN_TIMESTAMP)) {
//            yuyue_btn.setText(getResources().getString(R.string.bookingClass_state_outTime));
//        }else if (book.getBespeakCount() <
//                Integer.parseInt(book.getSection())) {
//            yuyue_btn.setText(getResources().getString(R.string.bookingClass_state_booking));
//        } else {
//            yuyue_btn.setText(getResources().getString(R.string.bookingClass_state_complete));
//        }
        if (book.getBespeakCount() < book.getSection()) {
            yuyue_btn.setText(getResources().getString(R.string.bookingClass_state_booking));
        }else{
            yuyue_btn.setVisibility(View.GONE);
        }

    }

}

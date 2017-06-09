package com.lxt.mobile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.BookingClassBeen;
import com.lxt.mobile.base.MBaseFragment;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 李鑫旺
 * @create-time : 2017/3/2 18:35
 * @description :
 */
public class BookingCellFragment extends MBaseFragment {
    View mView;
    /**
     * 有效期
     */
    TextView validityTime;
    /**
     * 套餐的背景
     */
    ImageView image_course;
    /**
     * 套餐的名字
     */
    TextView course_name;
    /**
     * 套餐的星数评级
     */
    TextView sum_star;
    /**
     * 对号 12/50表示一共以后多少集课程 这个是第多少集-
     */
    TextView stu_class_count;
    /**
     * 预约按钮
     */
    TextView order_btn;
    /**
     * 课程状态状态
     */
    ImageView state_img;
    /**
     * 向上进入详情的箭头
     */
    ImageView up_tomore;
    BookingClassBeen mDataBean;
    public static  BookingCellFragment newInstance(BookingClassBeen item) {
        BookingCellFragment f = new BookingCellFragment();
        Bundle bd = new Bundle(2);
        bd.putSerializable("BookingClassBeen", item);
        f.setArguments(bd);
        return f;
    }
    @Override
    public View setContentLayout(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.orderclass_item_layout, null, false);
        return null;
    }

    @Override
    public void initView() {
        validityTime = (TextView) mView.findViewById(R.id.order_validityTime);
        image_course = (ImageView) mView.findViewById(R.id.image_course);
        course_name = (TextView) mView.findViewById(R.id.course_name);
        sum_star = (TextView) mView.findViewById(R.id.order_sum_star);
        stu_class_count = (TextView) mView.findViewById(R.id.order_count_sum);
        order_btn = (TextView) mView.findViewById(R.id.order_yuyuebtn);
        state_img = (ImageView) mView.findViewById(R.id.order_state_img);
        up_tomore = (ImageView) mView.findViewById(R.id.order_up_tomore);
    }

    @Override
    public void load() {
        mDataBean = (BookingClassBeen) getArguments().getSerializable("BookingClassBeen");
    }
}

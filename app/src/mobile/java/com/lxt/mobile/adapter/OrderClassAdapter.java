package com.lxt.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.BookingClassBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.activity.OrderClassDetailActivity;
import com.lxt.mobile.activity.OrderTeacherListActivity;
import com.lxt.mobile.widget.PullDoorView;
import com.lxt.mobile.widget.cardview.CardAdapterHelper;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/6 17:25
 * @description :预约上课的适配器
 */
public class OrderClassAdapter extends RecyclerView.Adapter<OrderClassAdapter.ViewHolder> {
    private List<BookingClassBeen> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    Context context;

    public OrderClassAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<BookingClassBeen> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderclass_item_layout, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        setData(mList.get(position), holder,position);
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    void setData(final BookingClassBeen mDataBean, ViewHolder holder,int position) {
        holder.state_img.setVisibility(View.VISIBLE);
        holder.state_img.setBackgroundResource(R.mipmap.icon_wancheng);
        holder.order_btn.setVisibility(View.VISIBLE);
        holder.validityTime.setTextColor(Color.RED);
        if (!TextUtils.isEmpty(mDataBean.getStartTime()) && !TextUtils.isEmpty(mDataBean.getEndTime()))
            holder.validityTime.setText("有效期: " + TimeUtil.getStrTime(mDataBean.getStartTime(), TimeUtil.dateFormatYMD) + " - " + TimeUtil.getStrTime(mDataBean.getEndTime(), TimeUtil.dateFormatYMD));
        ImageLoaderUtil.getInstence().loadImage(context, mDataBean.getVerticalPic(), holder.image_course);
        holder.course_name.setText(mDataBean.getName());
        holder.sum_star.setText(mDataBean.getScore() + "");
        holder.stu_class_count.setText(mDataBean.getBespeakNo() + "/" + mDataBean.getSection());
        holder.course_name.setText(mDataBean.getName());
        holder.doorView.setIsPullEnadble(false);
        holder.course_package_name.setText(mDataBean.getPackage_name());
        if (mDataBean.getStatus() == 2) {//冻结的情况
            holder.state_img.setVisibility(View.VISIBLE);
            holder.state_img.setBackgroundResource(R.mipmap.icon_dongjie);
            holder.order_btn.setVisibility(View.INVISIBLE);
            holder.doorView.setIsPullEnadble(false);
            return;
        }
        if ((mDataBean.getBespeakCount() - mDataBean.getSection()) == 0) {//不能预约，但是还没完成
            holder.order_btn.setVisibility(View.INVISIBLE);
            holder.order_btn.setVisibility(View.INVISIBLE);
            if (mDataBean.getSection() - mDataBean.getBespeakNo() == 0) {//完成
                holder.state_img.setVisibility(View.VISIBLE);
                holder.state_img.setBackgroundResource(R.mipmap.icon_wancheng);
                holder.validityTime.setTextColor(Color.RED);
            } else {
                holder.state_img.setVisibility(View.INVISIBLE);
                holder.validityTime.setTextColor(Color.parseColor("#7e7e7e"));
            }
            jumToDetail(holder, mDataBean);
            return;
        } else {
            if (Long.parseLong(mDataBean.getEndTime()) < System.currentTimeMillis() / 1000) {
                holder.order_btn.setVisibility(View.INVISIBLE);
                holder.validityTime.setTextColor(Color.RED);
                holder.state_img.setVisibility(View.VISIBLE);
                holder.state_img.setBackgroundResource(R.mipmap.icon_guoqi);
                jumToDetail(holder, mDataBean);
                return;
            }
            if (mDataBean.getYKstatus() == 2) {//枷锁的情况
                holder.state_img.setVisibility(View.VISIBLE);
                holder.state_img.setBackgroundResource(R.mipmap.icon_jiesuo);
                holder.order_btn.setVisibility(View.INVISIBLE);
                holder.doorView.setIsPullEnadble(false);
                return;
            }
            //可以正常预约的
            //结束时间 小于 当前时间 已过期
            if (Long.parseLong(mDataBean.getEndTime()) > System.currentTimeMillis() / 1000) {
                holder.order_btn.setVisibility(View.VISIBLE);//显示order
                holder.validityTime.setTextColor(Color.parseColor("#7e7e7e"));
                holder.state_img.setVisibility(View.INVISIBLE);
                holder.order_btn.setOnClickListener(new View.OnClickListener() {//订购响应
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("course", mDataBean);
//                        intent.putExtra("classType", mDataBean.getType());
//                        intent.putExtra("className", mDataBean.getName());
//                        intent.putExtra("lesson_guid", mDataBean.getLesson_guid());
//                        intent.putExtra("book_id", mDataBean.getBook_id());
                        intent.setClass(context, OrderTeacherListActivity.class);//订购页面
                        context.startActivity(intent);
                    }
                });
                jumToDetail(holder, mDataBean);
                return;
            }

        }
        //结束时间 小于 当前时间 已过期
        if (Long.parseLong(mDataBean.getEndTime()) < System.currentTimeMillis() / 1000) {
            holder.order_btn.setVisibility(View.INVISIBLE);
            holder.validityTime.setTextColor(Color.RED);
            holder.state_img.setVisibility(View.VISIBLE);
            holder.state_img.setBackgroundResource(R.mipmap.icon_guoqi);
            jumToDetail(holder, mDataBean);
            return;
        }
        if (mDataBean.getYKstatus() == 2) {//枷锁的情况
            holder.state_img.setVisibility(View.VISIBLE);
            holder.state_img.setBackgroundResource(R.mipmap.icon_jiesuo);
            holder.order_btn.setVisibility(View.INVISIBLE);
            holder.doorView.setIsPullEnadble(false);
            return;
        }
    }

    /**
     * 跳转详情
     */
    public void jumToDetail(ViewHolder v, final BookingClassBeen mDataBean) {
        v.doorView.setIsPullEnadble(true);
        v.doorView.setOnHalfViewListener(new PullDoorView.OnHalfViewListener() {
            @Override
            public void halfViewListener() {
                //LogUtil.e("------------->即将跳转");
                if (TextUtils.isEmpty(mDataBean.getCourse_guid())) {
                    return;
                }
                Intent intent = new Intent(context, OrderClassDetailActivity.class);
                intent.putExtra(LxtParameters.Key.LESSON_GUID, mDataBean.getLesson_guid());
                intent.putExtra(LxtParameters.Key.COURSE_GUID, mDataBean.getCourse_guid());
                intent.putExtra(LxtParameters.Key.CLASSTYPE, mDataBean.getType());
                intent.putExtra(LxtParameters.Key.CLASSNAME, mDataBean.getName());
                intent.putExtra(LxtParameters.Key.BOOK_ID, mDataBean.getBook_id());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.hold);
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
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

        /**
         * 套餐的英文名称
         */
        TextView course_package_name;
        /**
         * 向上推动View
         */
        PullDoorView doorView;

        public ViewHolder(final View itemView) {
            super(itemView);
            doorView = (PullDoorView) itemView;
            validityTime = (TextView) itemView.findViewById(R.id.order_validityTime);
            image_course = (ImageView) itemView.findViewById(R.id.image_course);
            course_name = (TextView) itemView.findViewById(R.id.course_name);
            sum_star = (TextView) itemView.findViewById(R.id.order_sum_star);
            stu_class_count = (TextView) itemView.findViewById(R.id.order_count_sum);
            order_btn = (TextView) itemView.findViewById(R.id.order_yuyuebtn);
            state_img = (ImageView) itemView.findViewById(R.id.order_state_img);
            up_tomore = (ImageView) itemView.findViewById(R.id.order_up_tomore);
            course_package_name = (TextView) itemView.findViewById(R.id.course_package_name);
        }
    }
}
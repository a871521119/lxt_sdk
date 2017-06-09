package com.lxt.mobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.been.MyClassBeen;
import com.lxt.mobile.widget.cardview.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/6 17:25
 * @description : //我的课程的适配器
 */
public class MyClassClassAdapter extends RecyclerView.Adapter<MyClassClassAdapter.ViewHolder> {
    private List<MyClassBeen> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    Context context;
    public List<ViewHolder> myViewHolderList = new ArrayList<>();
    OnCancelOrderClickLister mClickLister;

    public MyClassClassAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<MyClassBeen> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    /**
     * 清空holder集合
     */
    public void clearHolder() {
        myViewHolderList.clear();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myclass_item_layout, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setDataPosition(position);
        //因为list已经持有holder的引用，所有数据自动会改变
        if (!(myViewHolderList.contains(holder))) {
            myViewHolderList.add(holder);
        }
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        MyClassBeen myClassBeen = mList.get(position);
        holder.className.setText(myClassBeen.getSpecialtyTitle());
        if (TextUtils.isEmpty(myClassBeen.getSpecialtyEnTitle())) {
            holder.englishName.setText(
                    "");
        } else
            holder.englishName.setText(myClassBeen.getSpecialtyEnTitle() + "");
        ImageLoaderUtil.getInstence().loadImage(context, myClassBeen.getBookIngImag(), holder.classImg);
        holder.teatureName.setText(myClassBeen.getTeacherName() + "");
        holder.classOpenTime.setText(myClassBeen.getStartDate() + "");
        holder.classOpenDetailTime.setText(myClassBeen.getTimeRegion() + "");
        holder.class_currentClass.setText("当前课节:" + myClassBeen.getCount() + "/" + myClassBeen.getSumCount());
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    //遍历list，刷新相应holder的TextView
    public void notifyData() {
        for (int i = 0; i < myViewHolderList.size(); i++) {
            if (myViewHolderList.get(i).classLeftTime != null) {
                myViewHolderList.get(i).classLeftTime
                        .setText(mList.get(myViewHolderList.get(i).position).getTime());
                handHourData(myViewHolderList.get(i), mList.get(myViewHolderList.get(i).position).getCountTime());
            }
        }
    }

    public void handHourData(final ViewHolder holder, Long leftTime) {
        int min20 = 60000 * 20;//20分钟以内
        int min2h = 3600000 * 2;//2小时
        if (leftTime < min20) {//小于20分钟可以进入教室
            //按钮变绿色进入教室   时间变红
            //进入教室button
            holder.classLeftTime.setTextColor(Color.RED);
            if (leftTime == 0) {
                holder.classLeftTime.setText("已经开始上课");
            }
            holder.classOpenBtn.setText("进入教室");
            holder.classOpenBtn.setBackgroundResource(R.drawable.btn_blubg);
            holder.classOpenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickLister != null) {
                        mClickLister.onCancelOrderClick(0, holder.position);
                    }
                }
            });
        } else if (min20 <= leftTime && leftTime <= min2h) {//大于20分钟小于两个小时不可以预约
            //按钮变灰不可点击  时间变黑
            holder.classOpenBtn.setText("进入教室");
            holder.classLeftTime.setTextColor(Color.BLACK);
            holder.classOpenBtn.setBackgroundResource(R.drawable.btn_graybg_noclick);
        } else {//大于两个小时可以取消预约
            //按钮变黄色取消预约 时间变黑
            holder.classOpenBtn.setText("取消预约");
            holder.classLeftTime.setTextColor(Color.BLACK);
            holder.classOpenBtn.setBackgroundResource(R.drawable.btn_yellowbg);
            holder.classOpenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickLister != null) {
                        mClickLister.onCancelOrderClick(1, holder.position);
                    }
                }
            });
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 课程名称
         */
        TextView className;

        /**
         * 课程的英文名称
         */
        TextView englishName;
        /**
         * 课程的背景图片
         */
        ImageView classImg;

        /**
         * 教师的名字
         */
        TextView teatureName;

        /**
         * 课程的开始时间
         */
        TextView classOpenTime;

        /**
         * 开始的具体时间
         */
        TextView classOpenDetailTime;

        /**
         * 剩余时间
         */
        TextView classLeftTime;
        /**
         * 当前课节
         */
        TextView class_currentClass;

        /**
         * 操作取消预约进入教室按钮
         */
        TextView classOpenBtn;
        private int position;

        private void setDataPosition(int position) {
            this.position = position;
        }

        public ViewHolder(final View itemView) {
            super(itemView);

            className = (TextView) itemView.findViewById(R.id.myclass_name);
            englishName = (TextView) itemView.findViewById(R.id.myclass_english_name);
            classImg = (ImageView) itemView.findViewById(R.id.myclass_img);
            teatureName = (TextView) itemView.findViewById(R.id.myclass_teacher_name);
            classOpenTime = (TextView) itemView.findViewById(R.id.myclass_opening_date);
            classOpenDetailTime = (TextView) itemView.findViewById(R.id.myclass_opening_time);
            classLeftTime = (TextView) itemView.findViewById(R.id.myclass_lefttime);
            classOpenBtn = (TextView) itemView.findViewById(R.id.class_openBtn);
            class_currentClass = (TextView) itemView.findViewById(R.id.myclass_current_class);
        }
    }

    public void setOnCancelOrderClickLister(OnCancelOrderClickLister clickLister) {
        this.mClickLister = clickLister;
    }

    public interface OnCancelOrderClickLister {
        /**
         * @param type     0表示进入教室1表示取消预约
         * @param posation
         */
        void onCancelOrderClick(int type, int posation);
    }


}
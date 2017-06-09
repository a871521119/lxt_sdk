package com.lxt.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.mobile.activity.TeacherDetailActivity;
import com.lxt.mobile.been.NavDate;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiWenJiang on 2017/6/8.
 */

public class TeacherDetailDateAdapter extends RecyclerView.Adapter{

    private List<NavDate> navDates;

    public TeacherDetailDateAdapter() {
        this.navDates = initNavDate();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher_detail,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        NavDate date = navDates.get(position);
        myHolder.day.setText(date.day);
        myHolder.week.setText(date.week);

    }

    @Override
    public int getItemCount() {
        return navDates.size();
    }

    /**
     * 初始化导航日期数据
     * @return
     */
    private List<NavDate> initNavDate() {
        List<NavDate>  listNavDate = new ArrayList<>();
        for (int i=0;i<14;i++){
            NavDate date = new NavDate();
            date.dates = TimeUtil.getNextDay(i,TimeUtil.dateFormatYMD);
            date.day = TimeUtil.getNextDay(i,TimeUtil.dateFormatD);
            date.week = TimeUtil.getWeek(date.dates,TimeUtil.dateFormatYMD);
            listNavDate.add(date);
        }
        return listNavDate;
    }

    /**
     * 获取position位置的数据
     * @param position
     * @return
     */
    public NavDate getItemObject(int position){
        if (navDates != null && position < navDates.size()){
          return   navDates.get(position);
        }
        return new NavDate();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView day,week;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtil.getScreenWidth(itemView.getContext())/5,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            day = (TextView) itemView.findViewById(R.id.date);
            week = (TextView) itemView.findViewById(R.id.week);
        }
    }

}

package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.sdk.util.LogUtil;
import com.lxt.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by LiWenJiang on 2017/6/8.
 */

public class TeacherDetailTimeTableAdapter extends RecyclerView.Adapter{

    private int col = 14;//列数

    private int row = 28;//行数

    private OnTableItemClickListener onItemClickListener;

    public TeacherDetailTimeTableAdapter(OnTableItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout itemView = new LinearLayout(parent.getContext());
        itemView.setGravity(Gravity.CENTER);
        itemView.setOrientation(LinearLayout.VERTICAL);
        itemView.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtil.getScreenWidth(itemView.getContext())/5,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        if (position == 0){
            myHolder.list.get(0).setBackgroundResource(R.drawable.bg_teacher_detail_time);
            myHolder.list.get(0).setSelected(true);
            myHolder.list.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                        onItemClickListener.onTableItemClickListeren((String) ((TextView)v).getText(),position);
                }
            });

            myHolder.list.get(3).setBackgroundResource(R.drawable.bg_teacher_detail_time);
            myHolder.list.get(4).setBackgroundResource(R.drawable.bg_teacher_detail_time);
            myHolder.list.get(myHolder.list.size()-1).setBackgroundResource(R.drawable.bg_teacher_detail_time);
        }else if (position == 1){
            myHolder.list.get(0).setBackgroundResource(R.drawable.bg_teacher_detail_time);
            myHolder.list.get(3).setBackgroundResource(R.drawable.bg_teacher_detail_time);
            myHolder.list.get(4).setBackgroundResource(R.drawable.bg_teacher_detail_time);
            myHolder.list.get(4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                        onItemClickListener.onTableItemClickListeren((String) ((TextView)v).getText(),position);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return col;
    }


    public interface OnTableItemClickListener{
       void onTableItemClickListeren(String time,int col);
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public List<TextView>  list;

        public MyViewHolder(View itemView) {
            super(itemView);
            createTable((ViewGroup) itemView);
        }

        /**
         *时间表格
         * @param parentView
         */
        private void createTable(ViewGroup parentView) {
            list = new ArrayList<>();
            int startHour = 9;
            for (int i = 0;i < row; i++){
                TextView tv = getTextView(parentView.getContext());
                if (i % 2 == 0){//整点
                    tv.setText(String.format(Locale.CHINESE, "%02d", startHour)+":00");
                }else {//半点
                    tv.setText(String.format(Locale.CHINESE, "%02d", startHour)+":30");
                    startHour++;
                }
                parentView.addView(tv);
                list.add(tv);
            }
        }


        private TextView getTextView(Context context){
            int wh =DisplayUtil.getScreenWidth(itemView.getContext())/5-context.getResources().getDimensionPixelOffset(R.dimen.x50);
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(wh,wh));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,context.getResources().getDimensionPixelOffset(R.dimen.x10));
            textView.setTextColor(context.getResources().getColor(R.color.color_teacherdetail_time));
            textView.setGravity(Gravity.CENTER);
            textView.setClickable(true);
            return textView;
        }
    }

}

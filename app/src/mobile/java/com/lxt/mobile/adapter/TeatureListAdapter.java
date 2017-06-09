package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.TeacherBean;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.widget.baserecycleview.BaseRecyclerViewAdapter;
import com.lxt.mobile.widget.baserecycleview.BaseRecyclerViewHolder;
import com.lxt.sdk.util.TimeUtil;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/17 18:06
 * @description : 我的关注
 */
public class TeatureListAdapter extends BaseRecyclerViewAdapter<TeacherBean> {

    public TeatureListAdapter(Context context, List<TeacherBean> datas) {
        super(context, datas);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, final int position) {
        TeacherBean teacherBean = mDatas.get(position);
        ViewHolder mViewholder = (ViewHolder) holder;
        ImageLoaderUtil.getInstence().loadRoundImage(mContext, teacherBean.getPic(), mViewholder.headView);
        mViewholder.name.setText(teacherBean.getFirstname());
        mViewholder.address.setText("来自:" + teacherBean.getNationality() + "");
        int age = Integer.parseInt(TimeUtil.getCurrentTime("yyyy")) -
                Integer.parseInt(TimeUtil.getStrTime(teacherBean.getBirthtime() + "", "yyyy"));
        mViewholder.age.setText("年龄:" + age + "");
        mViewholder.watchCount.setText(teacherBean.getScore() + "");
        mViewholder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewListener != null) {
                    onRecyclerViewListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_attention_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        /**
         * 头像
         */
        ImageView headView;
        /**
         * 姓名
         */
        TextView name;
        /**
         * 地址
         */
        TextView address;
        /**
         * 关注数量
         */
        TextView watchCount;
        /**
         * 年龄
         */
        TextView age;
        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            headView = (ImageView) view.findViewById(R.id.my_attention_item_titleimage);
            name = (TextView) view.findViewById(R.id.my_attention_item_name);
            address = (TextView) view.findViewById(R.id.my_attention_item_address);
            watchCount = (TextView) view.findViewById(R.id.watch_count);
            age = (TextView) view.findViewById(R.id.my_attention_item_age);
        }
    }
}

package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.CampusChooseBeen;
import com.lxt.mobile.widget.baserecycleview.BaseRecyclerViewAdapter;
import com.lxt.mobile.widget.baserecycleview.BaseRecyclerViewHolder;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/15 18:08
 * @description :选择校区的适配器
 */
public class ChooseSchoolAdapter extends BaseRecyclerViewAdapter<CampusChooseBeen> {
    public ChooseSchoolAdapter(Context context, List<CampusChooseBeen> datas) {
        super(context, datas);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, final int position) {
        SchoolViewHolder schoolViewHolder = (SchoolViewHolder) holder;
        CampusChooseBeen chooseBeen = mDatas.get(position);
        if (!TextUtils.isEmpty(chooseBeen.getSchool_guid())) {
            schoolViewHolder.campus_name_text.setText(chooseBeen.getSchool_name() + "");
        }
        if (!TextUtils.isEmpty(chooseBeen.getBook_name())) {
            schoolViewHolder.lesson_name_text.setText(chooseBeen.getBook_name() + "");
        }
        if (!TextUtils.isEmpty(chooseBeen.getEvolve())) {
            schoolViewHolder.lessons_progress_text.setText(chooseBeen.getEvolve() + "");
        }
        schoolViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
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
        View view = mInflater.inflate(R.layout.campus_choose_item, parent, false);
        SchoolViewHolder schoolViewHolder = new SchoolViewHolder(view);
        return schoolViewHolder;
    }

    class SchoolViewHolder extends BaseRecyclerViewHolder {
        TextView campus_name_text;//机构名称
        TextView lesson_name_text;//课程状态
        TextView lessons_progress_text;//课程进度
        View rootView;

        public SchoolViewHolder(View view) {
            super(view);
            rootView = view;
            campus_name_text = (TextView) view.findViewById(R.id.campus_name_text);
            lesson_name_text = (TextView) view.findViewById(R.id.lesson_name_text);
            lessons_progress_text = (TextView) view.findViewById(R.id.lessons_progress_text);
        }
    }
}

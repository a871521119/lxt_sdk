package com.lxt.tv.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.CampusChooseBeen;


import java.util.List;


/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 高明宇
 * @create-time : 2016/11/1 10:31
 * @description : 登录 校区选择适配器
 */
public class ChooseScoolAdapter extends BaseAdapter {
    private Context context =null;
    private List<CampusChooseBeen> list = null;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ChooseScoolAdapter(List<CampusChooseBeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CampusChoose campusChoose = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.campus_choose_item, null);
            campusChoose = new CampusChoose();
            campusChoose.campusName = (TextView) view.findViewById(R.id.campus_name_text);
            campusChoose.lessonName = (TextView) view.findViewById(R.id.lesson_name_text);
            campusChoose.lessonsProgress = (TextView) view.findViewById(R.id.lessons_progress_text);
            view.setTag(campusChoose);
        } else {
            campusChoose = (CampusChoose) view.getTag();
        }
        CampusChooseBeen data = list.get(position);

        if(!TextUtils.isEmpty(data.getSchool_name())){
            campusChoose.campusName.setText(data.getSchool_name()+"");
        }
        if(!TextUtils.isEmpty(data.getBook_name())){
            campusChoose.lessonName.setText(data.getBook_name()+"");
        }
        if(!TextUtils.isEmpty(data.getEvolve())&&
                !TextUtils.isEmpty(data.getCount())){
            campusChoose.lessonsProgress.setText(data.getEvolve()+""+"/"+data.getCount()+"");
        }
        return view;
    }
    class CampusChoose {
        TextView campusName,lessonName,lessonsProgress;
    }
}

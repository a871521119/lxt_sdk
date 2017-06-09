package com.lxt.tv.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.MyClassBeen;
import com.lxt.imageloader.ImageLoaderUtil;


import java.util.List;


/**
 * lixinwang 2016/12/23
 * 预约上课 viewpagerAdapter
 */
public class MyClassAdapter extends PagerAdapter {
    private List<MyClassBeen> lists;
    private Context context;

    public MyClassAdapter(Context context, List<MyClassBeen> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.iofcourse_item, null);
        itemView.setTag(position);
        MyClassBeen data=lists.get(position);
        ImageView coverPicture = (ImageView) itemView.findViewById(R.id.cover_picture);
        ImageLoaderUtil.getInstence().loadImage(context,data.getBookIngImag(),coverPicture);

        TextView courseName = (TextView) itemView.findViewById(R.id.iof_course_class_name);
        courseName.setText(data.getSpecialtyTitle());
        TextView CourseNameying = (TextView) itemView.findViewById(R.id.iof_course_class_english_name);
        String enTitle = (String) data.getSpecialtyEnTitle();
        if(!TextUtils.isEmpty(enTitle)){
            CourseNameying.setText(enTitle+"");
        }else{
            CourseNameying.setText("");
        }
        TextView effectiveDate = (TextView) itemView.findViewById(R.id.effective_date);
        effectiveDate.setText("当前课节:" + data.getCount() + "/" +data.getSumCount());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

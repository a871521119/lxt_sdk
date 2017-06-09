package com.lxt.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.ClassRecordBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.sdk.util.TimeUtil;
import java.util.List;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/15 18:08
 * @description :课程记录的适配器
 */
public class CourseRecordAdapter extends BaseAdapter {
    public static int height;
    private List<ClassRecordBeen> list;
    private Context context = null;

    public CourseRecordAdapter(Context context, List<ClassRecordBeen> courseRecordData) {
        this.context = context;
        this.list = courseRecordData;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ClassRecordBeen getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CourseRecord course;
        if(view == null){
            course = new CourseRecord();
            view = LayoutInflater.from(context).inflate(R.layout.course_record_item_layout, null, false);
            course.couresRecordImage = (ImageView) view.findViewById(R.id.coures_record_image);
            //封面图
            course.couresRecordTeacherName = (TextView) view.findViewById(R.id.coures_record_teacher_name);
            //日期
            course.couresRecordDate = (TextView) view.findViewById(R.id.coures_record_date);
            //评价
            course.couresRecordEvaluation = (TextView) view.findViewById(R.id.coures_record_evaluation);
            view.setTag(course);
        }else{
            course = (CourseRecord) view.getTag();
        }

        //封面图
        //x.image().bind(course.couresRecordImage, data.getTeachingMaterialPic(), imageOptions);
        ImageLoaderUtil.getInstence().loadImage(context,list.get(position).getTeachingMaterialPic(),course.couresRecordImage);
        //教师姓名
        course.couresRecordTeacherName.setText("任课老师: "+list.get(position).getTeacherName() );
        //时间
        course.couresRecordDate.setText(TimeUtil.getStrTime(String.valueOf(list.get(position).getStartTime()),TimeUtil.dateFormatYMDHMS));
        //评价  2 已评价   1未评价
        String tags ;
        if(list.get(position).getComment() == 2){
            //评价
            tags = "已评价";
            course.couresRecordEvaluation.setTextColor(0x7f000000);
        }else{
            //未评价
            tags = "未评价";
            course.couresRecordEvaluation.setTextColor(0x7f4DCCC7);
        }
        course.couresRecordEvaluation.setText(tags);
        return view;
    }

    /**
     * 加载更多
     * @param message
     */
    public void refresh(List<ClassRecordBeen> message) {
        if(list != null){
            list.addAll(message);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新评价信息
     * @param selectedPosition
     */
    public void updateEvaluate(int selectedPosition) {
        if (list != null && selectedPosition < list.size()){
            list.get(selectedPosition).setComment(2);
            notifyDataSetChanged();
        }
    }

    private static class CourseRecord {
        private ImageView couresRecordImage = null;
        private TextView couresRecordTeacherName = null,
                couresRecordDate = null,
                couresRecordEvaluation = null;

    }
}
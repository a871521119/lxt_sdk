package com.lxt.mobile.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.TeacherBean;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.sdk.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class OrderTeacherListAdapter extends BaseAdapter {

    private Context context;

    //存放老师列表的信息
    private List<TeacherBean> teacherList;

    private BookClassListener bookClassListener;

    public OrderTeacherListAdapter(Context context, List<TeacherBean> teacherList,BookClassListener bookClassListener) {
        this.context = context;
        this.teacherList = teacherList;
        this.bookClassListener = bookClassListener;
    }


    @Override
    public int getCount() {
        return teacherList.size();
    }

    @Override
    public TeacherBean getItem(int position) {
        return teacherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(null == convertView ){
            holder=new ViewHolder();

            convertView= View.inflate(context, R.layout.could_order_teacher_item,null);
            holder.could_order_teacher_head= (ImageView) convertView.findViewById(R.id.could_order_teacher_head);
            holder.red_xin= (ImageView) convertView.findViewById(R.id.red_xin);
            holder.order_teacher_name= (TextView) convertView.findViewById(R.id.order_teacher_name);
            holder.order_teacher_age= (TextView) convertView.findViewById(R.id.order_teacher_age);
            holder.order_teacher_country= (TextView) convertView.findViewById(R.id.order_teacher_country);
            holder.tv_order_time= (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.line=convertView.findViewById(R.id.line);

            holder.tv_order_teacher_grade= (TextView) convertView.findViewById(R.id.tv_order_teacher_grade);
            holder.tv_order= (LinearLayout) convertView.findViewById(R.id.tv_order);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ImageLoaderUtil.getInstence().loadRoundImage(context,teacherList.get(position).getPic(),holder.could_order_teacher_head);

        //是否关注  1关注 0未关注
         if(teacherList.get(position).getFollow() == 1){
            holder.red_xin.setVisibility(View.VISIBLE);
        }else{
            holder.red_xin.setVisibility(View.INVISIBLE);
        }
        holder.order_teacher_name.setText((teacherList.get(position).getFirstname()));//姓名

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);//年龄
        //获取当前时间戳
        long nowtime = TimeUtil.getTimeUnix();
        if(nowtime < teacherList.get(position).getBirthtime()){
            holder.order_teacher_age.setText("0");
        }else{
            Date date = null;
            try {
                date = dateFormat.parse(TimeUtil.getStrTime(String.valueOf(teacherList.get(position).getBirthtime()), TimeUtil.dateFormatYMD));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String age = getAge(date)+"";
            holder.order_teacher_age.setText(age);
        }

        holder.order_teacher_country.setText(teacherList.get(position).getNationality());//国籍
        holder.tv_order_time.setText(TimeUtil.getStrTime(teacherList.get(position).getStartTime()+"", TimeUtil.dateFormat));//开课时间
        holder.tv_order_teacher_grade.setText(teacherList.get(position).getScore()+"");

        //点击预约课程
        holder.tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Message message = new Message();
//                message.what= 6;
//                Bundle bundle = new Bundle();
//                bundle.putInt("listposition",position);
//                bundle.putString("teacher_guid",teacherList.get(position).getTeacher_guid());
//                bundle.putString("lesson_list_id",teacherList.get(position).getLesson_list_id());
//                bundle.putString("time_time_pot", Util.getStrTime(teacherList.get(position).getStartTime()+"", Constants.TIME_TURN_TIMESTAMP3)+"");
//                message.setData(bundle);
//                handler.sendMessage(message);
                if (bookClassListener != null)
                    bookClassListener.onBookClassListener(teacherList.get(position));
            }
        });
        return convertView;
    }

    public interface BookClassListener{
        void onBookClassListener(TeacherBean teacher);
    }

    /**
     * 更新数据
     * @param teacher
     */
    public void updateData(List<TeacherBean> teacher) {
        if(teacherList == null)
            teacherList = new ArrayList<>();
        teacherList.addAll(teacher);
        notifyDataSetChanged();
    }

    /**
     * 静态的内部类
     */
    public class ViewHolder {
        public ImageView could_order_teacher_head;
        public ImageView red_xin;
        public TextView order_teacher_name;
        public TextView order_teacher_age;
        public TextView order_teacher_country;
        public TextView tv_order_teacher_grade;
        public View line;
        public LinearLayout tv_order;
        private TextView tv_order_time;
    }

    //计算年龄
    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException(
                        "Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }
}

package com.lxt.tv.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.TeacherBean;
import com.lxt.imageloader.ImageLoaderUtil;


import java.text.DecimalFormat;
import java.util.List;



/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 李鑫旺
 * @create-time : 2016/12/27 11:34
 * @description :
 */
public class OrderTeacherListAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TeacherBean> lists;
    private Handler mHandler;
    public OrderTeacherListAdapter(Context context, List<TeacherBean> lists, Handler mHandler) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.lists = lists;
        this.mHandler = mHandler;
    }

    /**
     * PagerAdapter管理数据大小
     */
    @Override
    public int getCount() {
        return lists.size();
    }

    /**
     * 关联key 与 obj是否相等，即是否为同一个对象
     */
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj; // key
    }

    /**
     * 销毁当前page的相隔2个及2个以上的item时调用
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object); // 将view 类型 的object熊容器中移除,根据key
    }

    /**
     * 当前的page的前一页和后一页也会被调用，如果还没有调用或者已经调用了destroyItem
     */
    @Override
    public Object instantiateItem(ViewGroup containers, int position) {
        View convertView = mInflater.inflate(R.layout.teacher_list_item,null,false);
            //Start
            ImageView start1 = (ImageView) convertView.findViewById(R.id.start_img1);
            ImageView start2 = (ImageView) convertView.findViewById(R.id.start_img2);
            ImageView start3 = (ImageView) convertView.findViewById(R.id.start_img3);
            ImageView start4 = (ImageView) convertView.findViewById(R.id.start_img4);
            ImageView start5 = (ImageView) convertView.findViewById(R.id.start_img5);
            // 姓名
            TextView teacher_name = (TextView) convertView.findViewById(R.id.teacher_item_name);
            //教师头像
//            ImageView teacher_img = (ImageView) convertView.findViewById(R.id.teacher_item_img);
            ImageView teacher_img = (ImageView) convertView.findViewById(R.id.teacher_item_img);
            //介绍
            TextView teacher_introduce = (TextView) convertView.findViewById(R.id.teacher_item_introduce);
            //国家
            TextView teacher_nation = (TextView) convertView.findViewById(R.id.teacher_item_nation);
            //男女图片
            ImageView teacher_sex_img = (ImageView) convertView.findViewById(R.id.teacher_item_sex_img);
            //预约btn
            final Button yuyue_btn = (Button) convertView.findViewById(R.id.teacher_item_yuyue_btn);
        final TeacherBean teacherBean= lists.get(position);
        ImageLoaderUtil.getInstence().loadRoundImage(mContext,teacherBean.getPic(),teacher_img);
        int start_zhi = Integer.parseInt(totalMoney(teacherBean.getScore()));

        switch (start_zhi){
            case 1:
                start1.setVisibility(View.VISIBLE);
            break;
            case 2:
                start1.setVisibility(View.VISIBLE);
                start2.setVisibility(View.VISIBLE);
                break;
            case 3:
                start1.setVisibility(View.VISIBLE);
                start2.setVisibility(View.VISIBLE);
                start3.setVisibility(View.VISIBLE);
                break;
            case 4:
                start1.setVisibility(View.VISIBLE);
                start2.setVisibility(View.VISIBLE);
                start3.setVisibility(View.VISIBLE);
                start4.setVisibility(View.VISIBLE);
                break;
            case 5:
                start1.setVisibility(View.VISIBLE);
                start2.setVisibility(View.VISIBLE);
                start3.setVisibility(View.VISIBLE);
                start4.setVisibility(View.VISIBLE);
                start5.setVisibility(View.VISIBLE);
                break;
        }


        teacher_name.setText(teacherBean.getFirstname());
        teacher_introduce.setText(teacherBean.getIntroduce());
        teacher_nation.setText(teacherBean.getNationality());
        //1 男  2 女
        if(teacherBean.getGender() == 1){
            teacher_sex_img.setImageResource(R.drawable.man);
        }else{
            teacher_sex_img.setImageResource(R.drawable.girl);
        }


        yuyue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("teacher_guid",teacherBean.getTeacher_guid()+"");
                msg.what =88;
                msg.setData(b);
                mHandler.sendMessage(msg);
            }
        });
        containers.addView(convertView);
        return convertView; // 返回该view对象，作为key
    }


    public static String totalMoney(double money) {
        java.math.BigDecimal bigDec = new java.math.BigDecimal(money);
        double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0");
        return df.format(total);
    }
}

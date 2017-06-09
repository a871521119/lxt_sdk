package com.lxt.tv.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.BookingClassBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.sdk.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * lixinwang 2016/12/23
 * 预约上课 viewpagerAdapter
 */
public class YuYuePageAdapter extends PagerAdapter {
    private List<BookingClassBeen> lists;
    private Context context;
    private boolean isguoqi = false;
 
    public YuYuePageAdapter(Context context, List<BookingClassBeen> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View containerView = LayoutInflater.from(context).inflate(R.layout.yuyue_viewpager_item, null);
        ImageView imageView = (ImageView) containerView.findViewById(R.id.image_course);
        TextView name = (TextView) containerView.findViewById(R.id.course_name);
        TextView expiryDate = (TextView) containerView.findViewById(R.id.expiryDate);
        TextView bespeakNo = (TextView)containerView.findViewById(R.id.yuyue_bespeakno);
        ImageView guoqi = (ImageView) containerView.findViewById(R.id.guoqi);
        containerView.setTag(position);

        final BookingClassBeen resultDataBean = lists.get(position);
       name.setText(resultDataBean.getName());
        expiryDate.setText(String.format(context.getResources().getString(R.string.expiryDate),
                TimeUtil.getStrTime(resultDataBean.getStartTime(), TimeUtil.TIME_TURN_TIMESTAMP6),
                TimeUtil.getStrTime(resultDataBean.getEndTime(), TimeUtil.TIME_TURN_TIMESTAMP6)));
        bespeakNo.setText(resultDataBean.getBespeakNo()+"/"+resultDataBean.getSection());

        //获取当前时间戳
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String demo = formatter.format(curDate);

        if(Long.parseLong(resultDataBean.getEndTime()) < TimeUtil.getTimeStamp(demo,TimeUtil.dateFormatYMD)){
            guoqi.setVisibility(View.VISIBLE);
            guoqi.getBackground().setLevel(2);
            isguoqi = true;
        }
        if(resultDataBean.getBespeakCount() <resultDataBean.getSection() && !isguoqi){
            guoqi.setVisibility(View.GONE);
        }else{
            guoqi.setVisibility(View.VISIBLE);
            if(isguoqi){
                guoqi.getBackground().setLevel(2);
                isguoqi = false;
            }else{
                guoqi.getBackground().setLevel(1);
            }
        }
        ImageLoaderUtil.getInstence().loadImage(context,resultDataBean.getVerticalPic(),imageView);
        if (containerView.getParent() != null){
            ((ViewGroup)containerView.getParent()).removeAllViews();
        }
        container.addView(containerView);
        return containerView;
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

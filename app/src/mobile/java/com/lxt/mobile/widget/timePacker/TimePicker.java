package com.lxt.mobile.widget.timePacker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 自定义的时间选择器
 *
 * @author sxzhang
 */
public class TimePicker extends LinearLayout {

    private Calendar calendar = Calendar.getInstance();
    private WheelView hours, mins; //Wheel picker
    private OnChangeListener onChangeListener; //onChangeListener
    private ArrayList<DateObject> hourList;

    //Constructors
    public TimePicker(Context context) {
        super(context);
        init(context);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化  param是判断是否是当天 0即是当天 ，1即是其他
     *
     * @param context
     */
    public void init(Context context) {
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		int minute = calendar.get(Calendar.MINUTE);
        hourList = new ArrayList<DateObject>();

        for (int i = 16; i <= 44; i++) {
            DateObject dateObject = new DateObject(i, -1, true);
            hourList.add(dateObject);
        }
        //分钟也是一样的
        /*for (int j = 0; j < 48; j++) {
            dateObject = new DateObject(-1,j,false);
			minuteList.add(dateObject);
		}*/

        //小时选择器
        hours = new WheelView(context);
        LayoutParams lparams_hours = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int MARGIN_RIGHT = 5;
        lparams_hours.setMargins(0, 0, MARGIN_RIGHT, 0);
        hours.setLayoutParams(lparams_hours);
        hours.setAdapter(new StringWheelAdapter(hourList, 29));
        hours.setVisibleItems(5);
        hours.setCyclic(true);

        //如果是当天，就显示当前最接近的时间

//		if(minute<30){
//			hour=hour*2+1;
//		}else{
//			hour=hour*2+2;
//		}
        hours.setCurrentItem(0);
        hours.addChangingListener(onHoursChangedListener);
        addView(hours);
    }

    //listeners
    private OnWheelChangedListener onHoursChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView hours, int oldValue, int newValue) {
            calendar.set(Calendar.HOUR_OF_DAY, newValue);
            change();
        }
    };





    /**
     * 滑动改变监听器回调的接口
     */
    public interface OnChangeListener {
        void onChange(int hour, int minute);
    }

    /**
     * 设置滑动改变监听器
     *
     * @param onChangeListener
     */
    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    /**
     * 滑动最终调用的方法
     */
    private void change() {
        if (onChangeListener != null) {
            onChangeListener.onChange(getHourOfDay(), getMinute());
        }
    }

    /**
     * 设定现在的iterm
     */
    public void setCurrentItem(int hour, int min) {
        for (int i = 0; i < hourList.size(); i++) {
            if (hourList.get(i).getHour() == hour && hourList.get(i).getMinute() == min) {
                hours.setCurrentItem(i);
                return;
            }
        }
    }

    public int getCurrentItem(){
        if (hours != null){
          return   hours.getCurrentItem();
        }
        return -1;
    }

    public DateObject getSeletedItem(){
        if (hourList != null){
            return hourList.get(hours.getCurrentItem());
        }
        return null;
    }



    /**
     * 获取小时
     *
     * @return
     */
    public int getHourOfDay() {
        return hourList.get(hours.getCurrentItem()).getHour();
    }

    /**
     * 获取分钟
     *
     * @return
     */
    public int getMinute() {
        return hourList.get(hours.getCurrentItem()).getMinute();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package com.lxt.mobile.widget.timePacker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * 自定义的日期选择器
 * @author sxzhang
 *
 */
public class DatePicker extends LinearLayout {

	private Calendar calendar = Calendar.getInstance(); //������
	private WheelView newDays;
	private ArrayList<DateObject> dateList ;
	private OnChangeListener onChangeListener; //onChangeListener

	//Constructors
	public DatePicker(Context context) {
		super(context);
		initWheelView(context);
	}

	public DatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWheelView(context);
	}

	/**
	 * 初始化
	 * @param context
	 */
	private void initWheelView(Context context){
		newDays = new WheelView(context);
		LayoutParams newDays_param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		newDays_param.setMargins(0, 0, 10, 0);
		newDays.setLayoutParams(newDays_param);
		newDays.setVisibleItems(5);
		newDays.setCyclic(true);
	}

	public void setDateAdapter(long startTimeUnix,int delayDay){
		calendar.setTimeInMillis(startTimeUnix);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		dateList = new ArrayList<DateObject>();
		for (int i = 0; i < delayDay; i++) {
			DateObject dateObject = new DateObject(year, month, day + i, week + i);
			dateList.add(dateObject);
		}
		newDays.setAdapter(new StringWheelAdapter(dateList, delayDay));
		newDays.addChangingListener(onDaysChangedListener);
		//得到当前的时间的额选择
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		if(hour>22&&minute>30){
			newDays.setCurrentItem(1);
		}
		addView(newDays);
	}


	/**
	 * 滑动改变监听器
	 */
	private OnWheelChangedListener onDaysChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView mins, int oldValue, int newValue) {
			calendar.set(Calendar.DAY_OF_MONTH, newValue + 1);
			change();
		}
	};

	/**
	 * 滑动改变监听器回调的接口
	 */
	public interface OnChangeListener {
		void onChange(int year, int month, int day, int day_of_week);
	}

	/**
	 * 设置滑动改变监听器
	 * @param onChangeListener
	 */
	public void setOnChangeListener(OnChangeListener onChangeListener){
		this.onChangeListener = onChangeListener;
	}

	/**
	 * 滑动最终调用的方法
	 */
	private void change(){
		if(onChangeListener!=null){
			onChangeListener.onChange(
					dateList.get(newDays.getCurrentItem()).getYear(),
					dateList.get(newDays.getCurrentItem()).getMonth(),
					dateList.get(newDays.getCurrentItem()).getDay(),
					dateList.get(newDays.getCurrentItem()).getWeek());
		}
	}

	public int getCurrentItem(){
		if (newDays != null)
			return newDays.getCurrentItem();
		return -1;
	}

	public DateObject getSeletedItem(){
		if (dateList != null){
			return dateList.get(newDays.getCurrentItem());
		}
		return null;
	}




	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}

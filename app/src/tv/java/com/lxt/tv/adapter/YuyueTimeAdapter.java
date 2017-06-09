package com.lxt.tv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.BookTime;
import com.lxt.been.YuyueTimebean;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 李鑫旺
 * @create-time : 2017/1/4 17:40
 * @description :
 */
public class YuyueTimeAdapter extends BaseAdapter{
    private Context mContext;
    private List<BookTime> list;

    public YuyueTimeAdapter(Context context, List<BookTime> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.yuyue_dialog_item,null);
            viewHolder.mytime = (TextView) view.findViewById(R.id.yuyue_time_item_mytime);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mytime.setText(list.get(i).time);
        return view;
    }

    class ViewHolder{
        TextView mytime;
    }
}

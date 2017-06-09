package com.lxt.tv.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.ClassRecordBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.sdk.util.TimeUtil;

import java.util.List;



/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 李鑫旺
 * @create-time : 2016/12/27 11:34
 * @description :
 */
public class CourseRecordAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ClassRecordBeen> lists;
    private EvaluationListener evaluationListener;


    public void setLists(List<ClassRecordBeen> lists) {
        this.lists = lists;
    }

    public CourseRecordAdapter(Activity context, List<ClassRecordBeen> lists) {
        this.mContext = context;
        this.lists = lists;
        mInflater = LayoutInflater.from(context);
    }

    public void updateEvaluation(int position, String star) {
        if (lists != null && position < lists.size()) {
            lists.get(position).setGrade(star);
            lists.get(position).setComment(2);
            notifyDataSetChanged();
        }
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
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
        View view = setView(position);
        containers.addView(view);
        view.setTag(position);
        return view; // 返回该view对象，作为key
    }

    /**
     * 未评价界面
     */
    private View setView(final int position) {

        View evaluationView = mInflater.inflate(R.layout.course_record_item, null);
        TextView course_name = (TextView) evaluationView.findViewById(R.id.course_record_name);
        ImageView img = (ImageView) evaluationView.findViewById(R.id.course_record_img);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                mContext.getResources().getDimensionPixelOffset(R.dimen.x200),
                mContext.getResources().getDimensionPixelOffset(R.dimen.x200)
        );
        layoutParams.setMargins(0, -mContext.getResources().getDimensionPixelOffset(R.dimen.x10), 0, 0);
        img.setLayoutParams(layoutParams);
        TextView teacher_name = (TextView) evaluationView.findViewById(R.id.course_record_teacher);
        TextView time = (TextView) evaluationView.findViewById(R.id.course_record_time);
        Button state_btn = (Button) evaluationView.findViewById(R.id.course_state_btn);
        LinearLayout starLayout = (LinearLayout) evaluationView.findViewById(R.id.evaluateStarLayout);
        course_name.setText(lists.get(position).getBookName());
        ImageLoaderUtil.getInstence().loadImage(mContext, lists.get(position).getTeacherPic(), img);
        teacher_name.setText("授课教师: " + lists.get(position).getTeacherName());
        time.setText("上课时间: " + TimeUtil.getStrTime(
                lists.get(position).getStartTime() + "", TimeUtil.TIME_TURN_TIMESTAMP6));
        if (lists.get(position).getComment() == 2) {
            //已评价
            state_btn.setVisibility(View.GONE);
            starLayout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(lists.get(position).getGrade()))
                setStars(starLayout, Integer.parseInt(lists.get(position).getGrade()));//待修改

        } else {
            state_btn.setVisibility(View.VISIBLE);
            starLayout.setVisibility(View.GONE);
        }

        evaluationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evaluationListener != null) {
                    evaluationListener.onEvaluationListener(position);
                }
            }
        });
        return evaluationView;
    }


    private void setStars(LinearLayout starLayout, int star) {
        for (int i = 0; i < 5; i++) {
            ImageView image = createImageView(mContext.getResources().getDimensionPixelSize(R.dimen.x30),
                    R.drawable.xuanzhongxin_2);
            if (i >= star) {
                image.setVisibility(View.INVISIBLE);
            }
            starLayout.addView(image);
        }
        starLayout.addView(createImageView(mContext.getResources().getDimensionPixelSize(R.dimen.x40),
                R.drawable.icons));

    }

    private ImageView createImageView(int wh, int bgId) {
        ImageView image = new ImageView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(wh, wh);
        lp.setMargins(mContext.getResources().getDimensionPixelSize(R.dimen.x20), 0, 0, 0);
        image.setLayoutParams(lp);
        image.setBackgroundResource(bgId);
        return image;
    }

    public interface EvaluationListener {
        public void onEvaluationListener(int position);
    }

    public void setEvaluationListener(EvaluationListener evaluationListener) {
        this.evaluationListener = evaluationListener;
    }


}

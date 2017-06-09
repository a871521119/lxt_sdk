package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.ConmmentBeen;
import com.lxt.been.TeatureAppraiseBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.cell.ListCell;
import com.lxt.mobile.cell.TeatureAppraiseHeadView;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/25 11:09
 * @description : 教师评价适配器
 */
public class TeatureAppraiseAdapter extends RecyclerView.Adapter {
    public final int TeatureAppraiseHeadType = 1;
    public final int TeatureAppraiseType = 2;

    TeatureAppraiseBeen teatureAppraiseBeen;
    Context mContext;
    LayoutInflater inflater;

    public TeatureAppraiseAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(TeatureAppraiseBeen teatureAppraiseBeen) {
        this.teatureAppraiseBeen = teatureAppraiseBeen;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TeatureAppraiseHeadType) {
            View v = inflater.inflate(R.layout.comment_tags, parent, false);
            ViewHolderHeadView viewHolderHeadView = new ViewHolderHeadView(v);
            return viewHolderHeadView;
        } else {
            View v2 = inflater.inflate(R.layout.comment_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(v2);
            return viewHolder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderHeadView) {
            ViewHolderHeadView viewHolderHeadView = (ViewHolderHeadView) holder;
            ListCell listCell = viewHolderHeadView.teatureAppraiseHeadView;
            listCell.setData(teatureAppraiseBeen, 0, this);
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.ll_comment_star.removeAllViews();
            ConmmentBeen conmmentBeen = teatureAppraiseBeen.getData().get(position - 1);
            ImageLoaderUtil.getInstence().loadRoundImage(mContext, conmmentBeen.getStudentImage(), viewHolder.stu_img);
            viewHolder.tv_comment_time.setText(conmmentBeen.getCreateTime());
            viewHolder.stu_name.setText(conmmentBeen.getStudent_name());
            viewHolder.tv_comment_content.setText(conmmentBeen.getRemark());

            // holder.ll_comment_star.addView(Utils.addStar(conmmentBeen.getSum(), context));
            viewHolder.ll_comment_star.addView(addStar(conmmentBeen.getSum(), mContext));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("--position--->", position + "");
        if (position == 0) {
            return TeatureAppraiseHeadType;
        }
        return TeatureAppraiseType;
    }

    @Override
    public int getItemCount() {
        if (teatureAppraiseBeen == null || teatureAppraiseBeen.getData() == null || teatureAppraiseBeen.getData().size() == 0) {
            return 1;
        }
        return teatureAppraiseBeen.getData().size() + 1;
    }

    public class ViewHolderHeadView extends RecyclerView.ViewHolder {
        TeatureAppraiseHeadView teatureAppraiseHeadView;

        public ViewHolderHeadView(View itemView) {
            super(itemView);
            teatureAppraiseHeadView = (TeatureAppraiseHeadView) itemView.findViewById(R.id.coment_parent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView stu_img;
        public TextView stu_name;
        public LinearLayout ll_comment_star;

        public TextView tv_comment_time;
        public TextView tv_comment_content;

        public ViewHolder(View itemView) {
            super(itemView);
            stu_img = (ImageView) itemView.findViewById(R.id.stu_img);
            stu_name = (TextView) itemView.findViewById(R.id.stu_name);
            ll_comment_star = (LinearLayout) itemView.findViewById(R.id.ll_comment_star);
            tv_comment_time = (TextView) itemView.findViewById(R.id.tv_comment_time);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
        }
    }

    //根据返回的结果的数字往linearLayout中动态的添加星星
    public  View addStar(int sum, Context context) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout view = new LinearLayout(context);
        view.setLayoutParams(lp);//设置布局参数
        view.setPadding(0, 0, 0, 0);

        view.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为水平方向布局
        for (int i = 0; i < sum; i++) {
            ImageView allStar = new ImageView(context);
            allStar.setBackgroundResource(R.mipmap.yello_pf_star);
            view.addView(allStar);
        }
        if (sum < 5) {
            float defaultStar = 5 - sum;
            for (int i = 0; i < defaultStar; i++) {
                ImageView allStar = new ImageView(context);
                allStar.setBackgroundResource(R.mipmap.normal_star);
                view.addView(allStar);
            }
        }
        return view;
    }
}

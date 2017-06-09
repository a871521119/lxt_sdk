package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.OrderClassDetailBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.util.ToastUitl;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/6/2 10:43
 * @description : 预约课程详情的适配器
 */
public class OrderClassDetailAdapter extends RecyclerView.Adapter {
    public final int ORDERCLASSHEADVIEW = 1;
    public final int ORDERCLASSCONTENT = 2;
    Context mContext;
    OrderClassDetailBeen orderClassDetailBeen;
    LayoutInflater mInflater;

    public OrderClassDetailAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置数据
     */
    public void setData(OrderClassDetailBeen orderClassDetailBeen) {
        this.orderClassDetailBeen = orderClassDetailBeen;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ORDERCLASSHEADVIEW) {
            View headView = mInflater.inflate(R.layout.course_detail_head, parent, false);
            HeadViweHolder headViweHolder = new HeadViweHolder(headView);
            return headViweHolder;

        } else {
            View contentView = mInflater.inflate(R.layout.orderclassdetails_item, parent, false);
            ContentViweHolder contentViweHolder = new ContentViweHolder(contentView);
            return contentViweHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {


        } else {
            ContentViweHolder contentViweHolder = (ContentViweHolder) holder;
            if (position == 1) {
                contentViweHolder.imvFirstVerticalString.setVisibility(View.GONE);
            } else {
                contentViweHolder.imvFirstVerticalString.setVisibility(View.VISIBLE);
            }

            position = position - 1;
            OrderClassDetailBeen.TitleBean titleBean = orderClassDetailBeen.getTitle().get(position);
            int allscore = titleBean.getPronunciation() + titleBean.getSentence_pattern() + titleBean.getVocabulary() + titleBean.getFluency() + titleBean.getInteractive_quality();
            int average_score = allscore / 5;
            if (orderClassDetailBeen.getEvolve() == 1) {
                contentViweHolder.imvFirstVerticalString.setImageResource(R.mipmap.blue_line);
            }
            contentViweHolder.llStar.setVisibility(View.GONE);
            if (position <= orderClassDetailBeen.getBespeakCount()) {
                ImageLoaderUtil.getInstence().loadRoundImage(mContext, orderClassDetailBeen.getTitle().get(position).getLesson_pic(), contentViweHolder.courseHeadImage);

                contentViweHolder.imvFirstVerticalString.setImageResource(R.mipmap.blue_line);
                if (position == 0 && orderClassDetailBeen.getEvolve() != 0) {
                    contentViweHolder.doneCharacter.setVisibility(View.VISIBLE);
                    contentViweHolder.llStar.setVisibility(View.VISIBLE);
                    contentViweHolder.llStar.removeAllViews();
                    contentViweHolder.llStar.addView(addStar(average_score, mContext));
                } else if ((position + 1) <= orderClassDetailBeen.getEvolve()) {
                    contentViweHolder.doneCharacter.setVisibility(View.VISIBLE);
                    contentViweHolder.llStar.setVisibility(View.VISIBLE);
                    contentViweHolder.llStar.removeAllViews();
                    contentViweHolder.llStar.addView(addStar(average_score, mContext));

                } else if ((position + 1) > orderClassDetailBeen.getEvolve() && (position + 1) <= orderClassDetailBeen.getBespeakCount()) {
                    contentViweHolder.doneCharacter.setVisibility(View.VISIBLE);
                    contentViweHolder.doneCharacter.setImageResource(R.mipmap.icon_yuyue);
                } else if (position == orderClassDetailBeen.getBespeakCount()) {
                    contentViweHolder.doneCharacter.setVisibility(View.INVISIBLE);
                }
            } else {
                contentViweHolder.courseHeadImage.setImageResource(R.mipmap.course_detail_down);
                contentViweHolder.imvFirstVerticalString.setImageResource(R.mipmap.grayline);
                contentViweHolder.doneCharacter.setVisibility(View.INVISIBLE);
            }
            contentViweHolder.character_english_name.setText(orderClassDetailBeen.getTitle().get(position).getPptTitle());
            if (orderClassDetailBeen.getTitle() != null) {
                if (orderClassDetailBeen.getTitle().get(position).getBuke() == 1) {
                    contentViweHolder.doneCharacter.setVisibility(View.VISIBLE);
                    contentViweHolder.doneCharacter.setImageResource(R.mipmap.icon_buke);
                }
            }
            final int finalPosition = position;
            contentViweHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderClassDetailBeen.getBespeakCount() == finalPosition){
                        ToastUitl.showShort("---->点击了");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (orderClassDetailBeen == null) {
            return 0;
        }
        if (orderClassDetailBeen.getTitle() == null || orderClassDetailBeen.getTitle().size() == 0) {
            return 1;
        }
        return orderClassDetailBeen.getTitle().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ORDERCLASSHEADVIEW;
        } else {
            return ORDERCLASSCONTENT;
        }
    }

    class HeadViweHolder extends RecyclerView.ViewHolder {
        public HeadViweHolder(View itemView) {
            super(itemView);
        }
    }

    class ContentViweHolder extends RecyclerView.ViewHolder {
        private ImageView imvFirstVerticalString;
        private ImageView courseHeadImage;
        private TextView character_english_name;
        private LinearLayout llStar;
        private ImageView doneCharacter;
        View rootView;


        public ContentViweHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            imvFirstVerticalString = (ImageView) itemView.findViewById(R.id.imv_first_vertical_string);
            courseHeadImage = (ImageView) itemView.findViewById(R.id.course_head_image);
            character_english_name = (TextView) itemView.findViewById(R.id.character_english_name);
            llStar = (LinearLayout) itemView.findViewById(R.id.ll_star);
            doneCharacter = (ImageView) itemView.findViewById(R.id.done_character);
        }
    }

    //根据返回的评论的数字往linearLayout中动态的添加星星
    public View addStar(int sum, Context context) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout view = new LinearLayout(context);
        view.setLayoutParams(lp);//设置布局参数
        view.setPadding(0, 0, 0, 0);
        view.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为水平方向布局
        for (int i = 0; i < sum; i++) {
            ImageView allStar = new ImageView(context);
            allStar.setBackgroundResource(R.mipmap.icon_star_nub);
            allStar.setPadding(10, 0, 0, 0);
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

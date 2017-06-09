package com.lxt.mobile.widget.baserecycleview;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 12406 on 2016/4/16.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public Context mContext;
    public LayoutInflater mInflater;
    public List<T> mDatas = new LinkedList<>();
    //    public OnItemClickListener<T> mOnItemClickListener;
    private boolean mOpenAnimationEnable = false;
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = -1;

    private boolean isTopAndBottom = false; //是否上下滑动都出现动画

    public void setTopAndBottom(boolean topAndBottom) {
        isTopAndBottom = topAndBottom;
    }

    private BaseAnimation mCustomAnimation;
    /** 条目操作的回调监听 */
    protected OnRecyclerViewListener onRecyclerViewListener;

    /** 删除条目监听 */
    protected OnDeleteListener mOnDeleteListener;
    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if (datas != null) {
            mDatas = datas;
            mLastPosition = 4;
        }
    }

    public void remove(int position) {
        mDatas.remove(position);
    }


    public void setmSelectAnimation(BaseAnimation mCustomAnimation) {
        this.mCustomAnimation = mCustomAnimation;
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBind(holder, position);
        if (isTopAndBottom) {
            addAnimation(holder);
        }
    }

    public abstract void onBind(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType);

    /**
     * 第一次 绑顶 出现动画
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (!isTopAndBottom) {
            addAnimation(holder);
        }
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    /**
     * To open the animation when loading
     */
    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    public void setNotDoAnimationCount(int count) {
        mLastPosition = count;
    }

    private BaseAnimation mSelectAnimation = new SlideInBottomAnimation();

    /**
     * Set the view animation type.
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM}, {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }
//Animation
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int ALPHAIN = 0x00000001;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SCALEIN = 0x00000002;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_LEFT = 0x00000004;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_RIGHT = 0x00000005;
    /**
     * 设置删除监听器
     *
     * @param onDeleteListener
     */
    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }
    /**
     * 根据下标删除对应项
     *
     * @param index
     */
    public void deleteForIndex(int index) {
        if ((mOnDeleteListener != null && !mOnDeleteListener
                .onDeleteItem(index)) || index >= getItemCount()) {
            return;
        }
        mDatas.remove(index);
    }

    /**
     * 删除监听接口
     */
    public interface OnDeleteListener {
        boolean onDeleteItem(int index);
    }
    /**
     * 设置条目操作监听
     *
     * @param l
     */
    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        this.onRecyclerViewListener = l;
    }

    /**
     * 条目操作回调监听接口
     */
    public interface OnRecyclerViewListener {
        /**
         * 条目点击的监听回调
         *
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 长按点击的监听回调
         *
         * @param position
         */
        boolean onItemLongClick(int position);
    }
}

package com.lxt.mobile.utils;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/13 15:32
 * @description :
 */
public class PullRefreshManager {
    /**
     * 业务方实现接口
     */
    TwinkRefreshLoadListener loadListener;
    /**
     * 业务侧所用的TwinklingRefreshLayout
     */
    TwinklingRefreshLayout refreshLayout;

    /**
     * 构造方法
     *
     * @param refreshLayout
     * @param loadListener
     */
    public PullRefreshManager(TwinklingRefreshLayout refreshLayout, TwinkRefreshLoadListener loadListener) {
        this.loadListener = loadListener;
        this.refreshLayout = refreshLayout;
    }


    /**
     * 获取默认的pullRefreshView
     *
     * @param isEnable
     * @return
     */
    public TwinklingRefreshLayout getDefaultRefreshLayout(boolean... isEnable) {
        refreshLayout.setOverScrollRefreshShow(false);

        if (isEnable != null && isEnable.length != 0) {
            refreshLayout.setEnableRefresh(isEnable[0]);//第一个参数为下拉刷新是否支持
            if (isEnable[0]) {
                ProgressLayout headerView = new ProgressLayout(refreshLayout.getContext());
                refreshLayout.setHeaderView(headerView);
                refreshLayout.setFloatRefresh(true);
            } else {
                refreshLayout.setHeaderHeight(0);
            }
            if (isEnable.length > 1) {
                refreshLayout.setEnableLoadmore(isEnable[1]);//第一个参数为加载更多是否支持
                if (isEnable[1]) {
                    refreshLayout.setAutoLoadMore(true);
                    LoadingView loadingView = new LoadingView(refreshLayout.getContext());
                    refreshLayout.setBottomView(loadingView);
                } else {
                    refreshLayout.setBottomHeight(0);
                }
            }
        } else {
            refreshLayout.setFloatRefresh(true);
            refreshLayout.setAutoLoadMore(true);
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadmore(true);
            ProgressLayout headerView = new ProgressLayout(refreshLayout.getContext());
            refreshLayout.setHeaderView(headerView);
            LoadingView loadingView = new LoadingView(refreshLayout.getContext());
            refreshLayout.setBottomView(loadingView);
        }
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                if (loadListener != null) {
                    loadListener.onRefresh();
                }
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (loadListener != null) {
                    loadListener.onLoadMore();
                }
            }
        });
        return refreshLayout;
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
    }

    /**
     * 释放资源
     */
    public void onDestroy() {
        loadListener = null;

        refreshLayout = null;
    }


    public interface TwinkRefreshLoadListener {
        void onRefresh();

        void onLoadMore();
    }

}

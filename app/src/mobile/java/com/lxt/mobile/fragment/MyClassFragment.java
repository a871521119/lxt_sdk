package com.lxt.mobile.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.mobile.adapter.MyClassClassAdapter;
import com.lxt.mobile.base.MBaseFragment;
import com.lxt.mobile.been.MyClassBeen;
import com.lxt.mobile.utils.LxtUtils;
import com.lxt.mobile.videoclass.VideoClassActivity;
import com.lxt.mobile.widget.LxtDialog;
import com.lxt.mobile.widget.StatusView;
import com.lxt.mobile.widget.cardview.CardScaleHelper;
import com.lxt.mobile.widget.cardview.SpeedRecyclerView;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 11:57
 * @description : 我的课程
 */
public class MyClassFragment extends MBaseFragment implements MyClassClassAdapter.OnCancelOrderClickLister {
    View mView;
    SpeedRecyclerView mRecyclerView;
    /**
     * 当前页
     */
    int nowPage = 1;
    List<MyClassBeen> myClassBeens = new ArrayList<>();
    /**
     * 适配器
     */
    private MyClassClassAdapter adapter;
    /**
     * 是否加载
     */
    public boolean isRefresh = true;
    int totalCount;
    Thread thread;
    StatusView statusView;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //刷新适配器
                    //    mRecommendActivitiesAdapter.notifyDataSetChanged();
                    //优化刷新adapter的方法
                    adapter.notifyData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static final MyClassFragment newInstance() {
        MyClassFragment f = new MyClassFragment();
        Bundle bd = new Bundle();
        f.setArguments(bd);
        return f;
    }

    @Override
    public View setContentLayout(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.list_cardview, null);
        statusView = (StatusView) mView;
        return mView;
    }

    @Override
    public void initView() {
        mRecyclerView = (SpeedRecyclerView) mView.findViewById(R.id.order_list);
        MyThread timeThread = new MyThread(myClassBeens);
        thread = new Thread(timeThread);
        initAdapter();
        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        statusView.showContent(mRecyclerView);
    }

    /**
     * 获取数据
     */
    @Override
    public void load() {
    }

    public void getData() {
        if (myClassBeens == null || myClassBeens.size() == 0) {
            showProgressDialog();
        }
        isRefresh = true;
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getStudentCourseList(SharedPreference.getData(LxtParameters.Key.GUID),
                SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID), nowPage + "", 10 + "", SharedPreference.getData(LxtParameters.Key.TOKEN));
    }


    @Override
    public void onSuccessed(String action, String result) {
        super.onSuccessed(action, result);
        dismissProgressDialog();
        isRefresh = false;
        try {
            String data = JsonUtils.getValue(result, "ResultData");
            String json = JsonUtils.getValue(data, "data");
            totalCount = Integer.parseInt(JsonUtils.getValue(data, "totalCount"));
            if (!TextUtils.isEmpty(json)) {
                if (nowPage == 1) {
                    myClassBeens.clear();
                }
                myClassBeens.addAll(JsonUtils.fromJsonArray(json, MyClassBeen.class));
                adapter.clearHolder();
                adapter.setList(myClassBeens);
                chanTime();
            }
            if (myClassBeens.size() == 0) {
                statusView.showEmpty("当前账号无课程,请预约");
            } else {
                statusView.showContent(mRecyclerView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        isRefresh = false;
        dismissProgressDialog();
        statusView.showEmpty("当前账号无课程,请预约");
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        isRefresh = false;
        dismissProgressDialog();
        statusView.showError();
    }

    /**
     * 初始化适配器
     */
    void initAdapter() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyClassClassAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        // mRecyclerView绑定scale效果
        CardScaleHelper mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(0);
        mCardScaleHelper.setScale(1f);
        mCardScaleHelper.attachToRecyclerView(mRecyclerView);
        adapter.setOnCancelOrderClickLister(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        synchronized (MyClassFragment.class) {
                            if (!isRefresh) {
                                if (totalCount > myClassBeens.size()) {
                                    nowPage = nowPage + 1;
                                    getData();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dx > 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }
            }
        });
    }

    /**
     * 改变时间
     */
    public void chanTime() {
        //遍历所有数据，算出时间差并保存在每个商品的counttime属性内
        for (int i = 0; i < myClassBeens.size(); i++) {
            long counttime = LxtUtils.timeDifference(myClassBeens.get(i).getServerTime(), myClassBeens.get(i).getStartTime());
            myClassBeens.get(i).setCountTime(counttime);
        }
        if (!thread.isAlive()) {
            thread.start();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelOrderClick(int type, final int posation) {
        if (type == 0) {
            Intent intent = new Intent(getActivity(), VideoClassActivity.class);
            intent.putExtra("bespeak_guid", myClassBeens.get(posation).getLesson_guid() + "");
            intent.putExtra("teacher_guid", myClassBeens.get(posation).getTeacherId() + "");
            intent.putExtra("lessonId", myClassBeens.get(posation).getLessonId() + "");
            getActivity().startActivity(intent);
            return;
        }
        new LxtDialog.Builder(getActivity()).setMessage(getString(R.string.cancle_dialog_title))
                .setPositiveButton(getString(R.string.dialog_sureBut), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showProgressDialog();
                        String guid = SharedPreference.getData(LxtParameters.Key.GUID);
                        String bespeak_guid = myClassBeens.get(posation).getLessonId();
                        String teacher_guid = myClassBeens.get(posation).getTeacherId();
                        String school_guid = SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID);
                        String classTimeStamp = myClassBeens.get(posation).getStartTime();
                        String token = SharedPreference.getData(LxtParameters.Key.TOKEN);
                        LxtHttp.getInstance().lxt_cancelReservation(guid, bespeak_guid, teacher_guid, school_guid, classTimeStamp, "1", token);
                        dialog.dismiss();
                        dialog.cancel();
                    }
                }).setNegativeButton(getString(R.string.dialog_cancelBut),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                }).create().show();
    }

    @Override
    public void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        dismissProgressDialog();
        if (TextUtils.equals(data.action, LxtParameters.Action.CANCELRESERVATION)) {
            nowPage = 1;
            getData();
        }
    }

    /**
     * 开启线程改变数据
     */
    class MyThread implements Runnable {
        //用来停止线程
        boolean endThread;
        List<MyClassBeen> myClassBeens;

        public MyThread(List<MyClassBeen> myClassBeens) {
            this.myClassBeens = myClassBeens;
        }

        @Override
        public void run() {
            while (!endThread) {
                try {
                    Thread.sleep(1000);
                    for (int i = 0; i < myClassBeens.size(); i++) {
                        //拿到每件商品的时间差，转化为具体的多少天多少小时多少分多少秒
                        //并保存在商品time这个属性内
                        long counttime = myClassBeens.get(i).getCountTime();
                        if (counttime > 0) {
                            long days = counttime / (1000 * 60 * 60 * 24);
                            long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                            long minutes = (counttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                            long second = (counttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
                            //并保存在商品time这个属性内
                            String finaltime = "";

                            if (days > 0) {
                                finaltime = days + ":" + hours + ":" + minutes + ":" + second + ":";
                            } else {
                                finaltime = hours + ":" + minutes + ":" + second;
                            }
                            myClassBeens.get(i).setTime(finaltime);
                        } else {
                            myClassBeens.get(i).setTime(getString(R.string.myclass_noclass));
                        }
                        //如果时间差大于1秒钟，将时间差减去一秒钟，
                        // 并保存在每件商品的counttime属性内
                        if (counttime > 1000) {
                            myClassBeens.get(i).setCountTime(counttime - 1000);
                            Message message = new Message();
                            message.what = 1;
                            //发送信息给handler
                            handler.sendMessage(message);
                        } else {
                            myClassBeens.get(i).setCountTime(0);
                            myClassBeens.get(i).setCountTime(counttime - 1000);
                            Message message = new Message();
                            message.what = 1;
                            //发送信息给handler
                            handler.sendMessage(message);
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
    }


}

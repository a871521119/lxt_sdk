package com.lxt.mobile.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.ClassRecordBeen;
import com.lxt.mobile.adapter.CourseRecordAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.PullRefreshManager;
import com.lxt.mobile.widget.AppraiseWindow;
import com.lxt.mobile.widget.StatusView;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.mobile.widget.swipeMenuListView.SwipeMenuListView;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;

import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 9:42
 * @description : 课程记录
 */
public class ClassRecordActivity extends MBaseActivity implements PullRefreshManager.TwinkRefreshLoadListener,
        AdapterView.OnItemClickListener, AppraiseWindow.EvaluateListener {
    TitleLayout mTitleLayout;
    private int dataCount;//有效数据的总数
    private int page = 1;
    private int pageSize = 10;
    private TwinklingRefreshLayout refreshLayout;
    private SwipeMenuListView swipeMenuListView;
    private CourseRecordAdapter courseRecordAdapter;
    private PullRefreshManager pullRefreshManager;

    private AppraiseWindow appraiseWindow;//评价页面
    private ClassRecordBeen recordEval;//评价
    private int selectedPosition;
    StatusView statusView;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.swipe_list_refreshview);
    }

    @Override
    public void initView() {
        mTitleLayout = (TitleLayout) findViewById(R.id.title);
        mTitleLayout.setMode(true).setTitle("课程记录");

        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.swipe_refresh_view);
        swipeMenuListView.setOnItemClickListener(this);
        pullRefreshManager = new PullRefreshManager(refreshLayout, this);
        pullRefreshManager.getDefaultRefreshLayout();
        statusView = (StatusView) findViewById(R.id.statusview);
        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoursoRecord();
            }
        });
        statusView.showContent(refreshLayout);
    }

    @Override
    public void initData() {
        super.initData();
        getCoursoRecord();
    }

    /**
     * 获取课程记录
     */
    private void getCoursoRecord() {
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getCourseRecord(SharedPreference.getData(LxtParameters.Key.GUID),
                String.valueOf(page), String.valueOf(pageSize), SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(data.action, LxtParameters.Action.LESSONRECORD)) {
            ClassRecordBeen classRecord = (ClassRecordBeen) data.result;
            dataCount = classRecord.getCount();
            if (classRecord != null) {
                if (classRecord.getMessage() != null &&
                        !classRecord.getMessage().isEmpty()) {
                    if (courseRecordAdapter == null || page == 1) {
                        courseRecordAdapter = new CourseRecordAdapter(this, classRecord.getMessage());
                        swipeMenuListView.setAdapter(courseRecordAdapter);
                    } else {
                        courseRecordAdapter.refresh(classRecord.getMessage());
                    }
                }
                if (classRecord.getMessage().size() != 0) {
                    statusView.showContent(refreshLayout);
                } else {
                    statusView.showEmpty("暂无课程记录");
                }
                pullRefreshManager.stopRefresh();
            }

        } else if (TextUtils.equals(data.action, LxtParameters.Action.SETTEACHERCOMMENT)) {
            destroyAppraise();
            showToast((String) data.result);
            courseRecordAdapter.updateEvaluate(selectedPosition);
        }
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        destroyAppraise();
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        statusView.showNoNetwork();
    }

    @Override
    public void onRefresh() {
        if (page != 1) {
            page = 1;
            getCoursoRecord();
        } else {
            pullRefreshManager.stopRefresh();
        }

    }

    @Override
    public void onLoadMore() {
        if (page * pageSize < dataCount) {
            page++;
            getCoursoRecord();
        } else {
            pullRefreshManager.stopRefresh();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        recordEval = (ClassRecordBeen) parent.getAdapter().getItem(position);
        if (recordEval.getComment() != 2) {
            selectedPosition = position;
            appraiseWindow = new AppraiseWindow(this, this);
            appraiseWindow.showAtLocation(findViewById(R.id.parent));
        } else {
            showToast("您已评价");
        }

    }


    @Override
    public void onEvaluate() {
        if (appraiseWindow != null && recordEval != null) {
            if (TextUtils.isEmpty(appraiseWindow.getEvaluateContent())) {
                showToast("请输入你想说的");
                return;
            } else {
                LxtHttp.getInstance().lxt_teacherComment(SharedPreference.getData(LxtParameters.Key.GUID),
                        SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                        recordEval.getBespeak_guid(),
                        recordEval.getTeacher_guid(),
                        appraiseWindow.getEvaluateStarLevel(),
                        appraiseWindow.getEvaluateContent(),
                        appraiseWindow.getLabelTagArray(),
                        SharedPreference.getData(LxtParameters.Key.TOKEN));
            }
        }

    }

    /**
     * 销毁当前评价信息
     */
    private void destroyAppraise() {
        appraiseWindow.dismissWindow();
        appraiseWindow = null;
        recordEval = null;
        statusView.showEmpty("暂无课程记录");
    }
}

package com.lxt.mobile.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.BookingClassBeen;
import com.lxt.mobile.adapter.OrderClassAdapter;
import com.lxt.mobile.base.MBaseFragment;
import com.lxt.mobile.widget.StatusView;
import com.lxt.mobile.widget.cardview.CardScaleHelper;
import com.lxt.mobile.widget.cardview.SpeedRecyclerView;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;

import java.util.List;
import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 11:57
 * @description : 预约上课
 */
public class OrderClassFragment extends MBaseFragment {
    View mView;
    SpeedRecyclerView mRecyclerView;
    String guid, school_guid;
    List<BookingClassBeen> mBookingClassBeenList;
    OrderClassAdapter adapter;
    StatusView statusView;

    public static final OrderClassFragment newInstance() {
        OrderClassFragment f = new OrderClassFragment();
        Bundle bd = new Bundle();
        f.setArguments(bd);
        return f;
    }

    @Override
    public View setContentLayout(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.list_cardview, null, false);
        statusView = (StatusView) mView;
        return mView;
    }

    @Override
    public void initView() {
        mRecyclerView = (SpeedRecyclerView) mView.findViewById(R.id.order_list);
        initAdapter();
        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLessonList();
            }
        });
        statusView.showContent(mRecyclerView);
    }


    @Override
    public void load() {

    }
    /**
     * 获取数据
     */
    public void getData(){
        myLessonList();
    }




    /**
     * 请求预约上课
     */
    void myLessonList() {
        LxtHttp.getInstance().setCallBackListener(this);
        guid = SharedPreference.getData(LxtParameters.Key.GUID);
        school_guid = SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID);
        LxtHttp.getInstance().lxt_getMyCourse(guid, school_guid, SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    public void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(data.action, LxtParameters.Action.MYLESSONLIST)) {
            mBookingClassBeenList = (List<BookingClassBeen>) data.result;
            adapter.setList(mBookingClassBeenList);
        }
        if (mBookingClassBeenList.size() == 0) {
            statusView.showEmpty("暂无数据");
        } else {
            statusView.showContent(mRecyclerView);
        }
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        statusView.showEmpty();
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        statusView.showNoNetwork();
    }

    void initAdapter() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderClassAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        // mRecyclerView绑定scale效果
        CardScaleHelper mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(0);
        mCardScaleHelper.attachToRecyclerView(mRecyclerView);
    }
}

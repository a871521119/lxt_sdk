package com.lxt.tv.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.base.AppManager;
import com.lxt.base.BaseBeen;
import com.lxt.base.BaseFragment;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.tv.activity.LoginActivity;
import com.lxt.tv.widget.CustomViewPager;
import com.lxt.tv.widget.RootView;
import com.lxt.tv.widget.ZoomOutPageTransformer2;
import com.lxt.util.ParseJsonUtil;
import com.lxt.util.ToastUitl;
import com.lxt.widget.LoadingDialog;

import java.util.Map;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 11:19
 * @description :
 */
public abstract class MBaseFragment extends BaseFragment implements View.OnClickListener,CallBackListener,
        RootView.LoadViewListener{

    protected Resources resources = null;

    private RootView rootView;//根布局
    /* viewpager*/
    private CustomViewPager customViewPager;
    //边框
    private View border;

    /**
     * 是否正在刷新
     */
    private boolean isRefreshing = false;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = new RootView(getActivity());
        rootView.setLoadViewListener(this);
        rootView.setSucessView(getSuccessView());
        rootView.setFailView(showErrorView(getString(R.string.dataFaildText)));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
    }

    /**设置布局文件*/
    public abstract View setContentLayout(LayoutInflater inflater);
    /**实例化布局文件/组件*/
    public abstract  void initView();
    /**加载数据*/
    public abstract void load();
    /**初始化监听*/
    protected void initListener() {}
    /**在实例化布局之后处理的逻辑*/
    public void bindViewData(BaseBeen data) {}

    /**
     * 成功页面
     */
    public View getSuccessView(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return setContentLayout(inflater);
    }

    @Override
    public void onSucessView() {
        initView();
        initListener();
    }

    /**
     * 初始化公共ViewPager
     * @param borderId 选中框Id   -1 不需要选中框
     * @param viewpaegerid ViewPagerId
     * @param limit 预加载页面个数
     * @param pageMargin 页面距离
     */
    protected void initCustomViewPager(int borderId,int viewpaegerid,int limit,int pageMargin){
        if(borderId != -1)
            border = rootView.findViewById(borderId);
        customViewPager = (CustomViewPager) rootView.findViewById(viewpaegerid);
        customViewPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                MBaseFragment.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        customViewPager.setOnClickListener(this);
        customViewPager.setOnFocusChangeListener(pagerFocusListener);
        customViewPager.setOffscreenPageLimit(limit);
        customViewPager.setPageTransformer(true, new ZoomOutPageTransformer2(customViewPager));
        // 设置2张图之前的间距。
        customViewPager.setPageMargin(pageMargin);
        customViewPager.setCurrentItem(0);
    }

    /**
     * ViewPager页面选中回调
     * @param position
     */
    protected void onPageSelected(int position){}

    /**
     * ViewPager当前选中页
     * @return
     */
    protected int getCurrentItem(){
        if (customViewPager != null)
            return  customViewPager.getCurrentItem();
        return -1;
    }

    /**
     * 设置CustomViewPager适配器
     * @param pagerAdapter
     */
    protected void setViewPagerAdapter(PagerAdapter pagerAdapter){
        if (customViewPager !=null){
            customViewPager.setAdapter(pagerAdapter);
//            customViewPager.setFocusable(true);
//            customViewPager.requestFocus();
        }
    }

    /**
     * ViewPager是否显示焦点框监听
     */
    View.OnFocusChangeListener pagerFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (border != null){
                if (hasFocus){
                    border.setVisibility(View.VISIBLE);
                }else {
                    border.setVisibility(View.GONE);
                }
            }
        }
    };

    /**
     * 短时间显示Toast
     * @param info 显示的内容
     */
    public void showToast(String info) {
        ToastUitl.showShort(info);
    }

    /**
     * 网络请求统一处理(新版本调用,放在BaseActivity)
     * @param param      参数
     * @param action     请求的动作
     */
    public void getHttpResult(String action,Map<String, String> param) {
        if(!isRefreshing){
            isRefreshing = true;
            LoadingDialog.showDialogForLoading(getActivity());
            post(action,param);
        }

    }

      /**
     * 网络接口数据请求
     * @param action
     */
    private void post(String action,Map<String, String> param) {
        LxtHttp.getInstance().setCallBackListener(this);
        String token = SharedPreference.getData(LxtParameters.Key.TOKEN);
        String guid = SharedPreference.getData(LxtParameters.Key.GUID);
        String school_guid = SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID);
        if (TextUtils.equals(action,LxtParameters.Action.MYLESSONLIST)){//预约上课
            LxtHttp.getInstance().lxt_getMyCourse(guid,school_guid,token);
        }else if(TextUtils.equals(action, LxtParameters.Action.GET_MYLESSIONLIST)){//我的课程
            LxtHttp.getInstance().lxt_getStudentCourseList(guid,school_guid,
                    param.get(LxtParameters.Key.NOWPAGE),
                    param.get(LxtParameters.Key.PAGESIZE),token);
        }else if(TextUtils.equals(action, LxtParameters.Action.LESSONRECORD)){//课程记录
            LxtHttp.getInstance().lxt_getCourseRecord(guid,
                    param.get(LxtParameters.Key.NOWPAGE),
                    param.get(LxtParameters.Key.PAGESIZE),token);
        }else if(TextUtils.equals(action,LxtParameters.Action.MYMESSAGE)){//个人信息
            LxtHttp.getInstance().lxt_getStudentInfo(guid,token);
        }else if(TextUtils.equals(action,LxtParameters.Action.CANCELRESERVATION)){//取消预约
            LxtHttp.getInstance().lxt_cancelReservation(guid,
                    param.get(LxtParameters.Key.BESPEAK_GUID),
                    param.get(LxtParameters.Key.TEACHER_GUID),
                    school_guid,param.get(LxtParameters.Key.CLASSTIMESTAMP),
                    param.get(LxtParameters.Key.TYPE),token);
        }
    }

    @Override
    public void onSuccessed(String action, String result) {
        LoadingDialog.cancelDialogForLoading();
        rootView.setState(RootView.State.SUCCESS);
        isRefreshing = false;
        bindViewData(ParseJsonUtil.parse(action,result));
    }

    /**
     * 请求失败或发生错误调用此方法
     * @param result  返回错误码
     * @param action 主要是为了区分接口，防止Activity中多个网络请求无法区分
     */
    @Override
    public void onFailed(String action, String result) {
        LoadingDialog.cancelDialogForLoading();
        isRefreshing = false;
        BaseBeen base = ParseJsonUtil.parse(action,result);
        ToastUitl.showShort((String)base.result);
        if (base.ServerNo.equals("SN005") || base.ServerNo.equals("SN008") || base.ServerNo.equals("SN009")) {
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE,"exitStyle");
            AppManager.getAppManager().finishAllActivity();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }else{
            addErrorView(action,getString(R.string.dataFaildText));
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        LoadingDialog.cancelDialogForLoading();
        isRefreshing = false;
        ToastUitl.showLong(errormsg);
        addErrorView(action,getString(R.string.dataFaildText));
    }

    /**
     * 设置错误页面
     * @param msg
     */
    protected void addErrorView(String action,String msg){
        rootView.setFailView(showErrorView(msg));
        rootView.setState(RootView.State.FAIL);
    }
    /**
     * 显示错误页面
     * @param msg
     */
    protected View showErrorView(String msg){
        View errorView = LayoutInflater.from(getContext()).inflate(R.layout.loading_error_layout,null);
        ImageView errorToastImage = (ImageView) errorView.findViewById(R.id.error_toast_image);
        errorToastImage.setOnClickListener(this);
        errorToastImage.setFocusable(true);
        errorToastImage.requestFocus();
        TextView  errorToastText = (TextView) errorView.findViewById(R.id.error_toast_text);
        errorToastText.setText(msg);
        return errorView;
    }

}

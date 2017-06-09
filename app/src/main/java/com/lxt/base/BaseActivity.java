package com.lxt.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;

import com.lxt.R;
import com.lxt.permission.AndPermission;
import com.lxt.permission.PermissionListener;
import com.lxt.permission.Rationale;
import com.lxt.permission.RationaleListener;
import com.lxt.util.Utils;
import com.lxt.widget.LoadingDialog;
import com.lxt.widget.StatusBarCompat;

import java.util.List;



/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 13:20
 * @description :
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener ,PermissionListener {
    private static final int REQUEST_CODE_PERMISSION_OTHER = 101;
    private static final int REQUEST_CODE_SETTING = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 默认着色状态栏
        //SetStatusBarColor();

    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_color));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog(String... msg) {
        if (msg != null && msg.length != 0) {
            LoadingDialog.showDialogForLoading(this, msg[0], true);
            return;
        }
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onClick(View v) {
        if (Utils.isFirstClick()) {
            return;
        }
        onClickView(v);
    }

    /**
     * 防误触的点击
     *
     * @param v
     */
    protected void onClickView(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
    /**
     * 6.0以上动态申请权限
     *
     * @param permissionList
     */
    public void requestPermission(String... permissionList) {
        // 申请权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_OTHER)
                .permission(permissionList)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(rationaleListener)
                .send();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            rationale.resume();
        }
    };

    /**
     * 权限申请成功回调
     *
     * @param requestCode
     * @param grantPermissions
     */
    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_OTHER: {
                onPermissionSucceed(requestCode, grantPermissions);
                break;
            }
        }
    }

    /**
     * 权限申请失败回调
     *
     * @param requestCode
     * @param deniedPermissions
     */
    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        onPermissionFailed(requestCode, deniedPermissions);
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();

            // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingHandle = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingHandle.execute();
//            你的dialog点击了取消调用：
//            settingHandle.cancel();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // listener方式，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 权限申请成功之后重写此方法回调使用
     *
     * @param requestCode
     * @param grantPermissions
     */
    public void onPermissionSucceed(int requestCode, List<String> grantPermissions) {
    }

    /**
     * 权限申请失败之后重写此方法回调使用
     *
     * @param requestCode
     * @param deniedPermissions
     */
    public void onPermissionFailed(int requestCode, List<String> deniedPermissions) {


    }
}

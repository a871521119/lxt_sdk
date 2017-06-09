package com.lxt.mobile.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.PersonalBeen;
import com.lxt.been.UpdataInfo;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.LGImgCompressor;
import com.lxt.mobile.utils.UploadManager;
import com.lxt.mobile.widget.TackPhotoPopwindow;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class SelfInfoActivity extends MBaseActivity implements TackPhotoPopwindow.OnPopItemClickListener, LGImgCompressor.CompressListener, UploadManager.OnUploadFinishListener {
    public static final int UPDATA_NAME = 1;//修改名字
    public static final int UPDATA_SEX = 2;//修改性别
    public static final int UPDATA_EMAIL = 3;//修改email
    public static final int UPDATA_BIRTHDAY = 4;//修改生日
    private final static int CAMERA_REQESTCODE = 100;
    ImageView headImg;
    PersonalBeen personalBeen;

    /**
     * 昵称
     */
    String nickName = SharedPreference.getData(LxtParameters.Key.NAME);

    /**
     * 性别
     */
    String sex = SharedPreference.getData(LxtParameters.Key.SEX);

    /**
     * 手机号
     */
    String phoneNo = SharedPreference.getData(LxtParameters.Key.PHONE);

    /**
     * 邮箱地址
     */
    String email = SharedPreference.getData(LxtParameters.Key.EMAIL);

    /**
     * 生日
     */
    String birthday = SharedPreference.getData(LxtParameters.Key.BIRTHTIME);

    /**
     * guid
     */
    String guid = SharedPreference.getData(LxtParameters.Key.GUID);
    /**
     * token
     */
    String token = SharedPreference.getData(LxtParameters.Key.TOKEN);

    /**
     * 修改标识
     */
    int updataState;
    /**
     * 图片的位置
     */
    private File imageFile;
    private String picturePath;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_self_info);
    }

    @Override
    public void initView() {
        ((TitleLayout) findViewById(R.id.title)).setMode(true).setTitle("个人信息");
        headImg = (ImageView) findViewById(R.id.background_modified_image);
    }

    @Override
    public void initListener() {
        super.initListener();
        findViewById(R.id.self_updata_name).setOnClickListener(this);
        findViewById(R.id.self_updata_sex).setOnClickListener(this);
        findViewById(R.id.self_updata_email).setOnClickListener(this);
        findViewById(R.id.self_updata_birthday).setOnClickListener(this);
        headImg.setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.self_updata_name:
                jumToEditPage(UPDATA_NAME, personalBeen.getNickname());
                break;
            case R.id.self_updata_sex:
                jumToEditPage(UPDATA_SEX, personalBeen.getSex() + "");
                break;
            case R.id.self_updata_email:
                jumToEditPage(UPDATA_EMAIL, personalBeen.getEmail());
                break;
            case R.id.self_updata_birthday:
                jumToEditPage(UPDATA_BIRTHDAY, personalBeen.getBirthtime());
                break;
            case R.id.background_modified_image:
                TackPhotoPopwindow tackPhotoPopwindow = new TackPhotoPopwindow(this);
                tackPhotoPopwindow.showWindow();
                tackPhotoPopwindow.setOnPopItemClickListener(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTakePhotoClick() {
        takePictureFormCamera();
    }

    @Override
    public void onTakePhotoFromSdClick() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 0);
    }

    /**
     * 跳转编辑页面
     */
    public void jumToEditPage(int where, String... birthday) {
        Intent intent = new Intent(this, SelfInfoEditActivity.class);
        if (birthday != null && birthday.length != 0 && !TextUtils.isEmpty(birthday[0]))
            intent.putExtra("data", birthday[0]);
        intent.putExtra("where", where);
        startActivityForResult(intent, 2);
    }

    @Override
    public void initData() {
        super.initData();
        getSelfMessage();
    }

    /**
     * 获取个人信息
     */
    public void getSelfMessage() {
        LxtHttp.getInstance().setCallBackListener(this);
        showProgressDialog();
        LxtHttp.getInstance().lxt_getStudentInfo(guid, token);
    }

    /**
     * 修改个人资料
     */
    public void updataSelfInfo() {
        LxtHttp.getInstance().lxt_updateStudentInfo(guid, SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                phoneNo, nickName, sex, birthday, email, token
        );
    }


    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        dismissProgressDialog();
        if (data.action.equals(LxtParameters.Action.MYMESSAGE)) {
            personalBeen = (PersonalBeen) data.result;
            setSelfData();
            nickName = personalBeen.getNickname();
            sex = personalBeen.getSex() + "";
            phoneNo = personalBeen.getTel();
            if (TextUtils.isEmpty(personalBeen.getBirthtime())) {
                birthday = "";
            } else {
                birthday = personalBeen.getBirthtime();
            }
        } else {
            UpdataInfo updataInfo = (UpdataInfo) data.result;
            nickName = updataInfo.getName();
            if (TextUtils.isEmpty(updataInfo.getBirthtime())) {
                birthday = "";
            } else {
                birthday = updataInfo.getBirthtime();
            }
            phoneNo = updataInfo.getTel();
            sex = updataInfo.getSex() + "";
            email = updataInfo.getEmail();
            personalBeen.setNickname(nickName);
            personalBeen.setSex(Integer.parseInt(sex));
            personalBeen.setEmail(email);
            personalBeen.setBirthtime(birthday);
            setSelfData();
            showToast("修改成功");
        }
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        dismissProgressDialog();

        if (action.equals(LxtParameters.Action.MYMESSAGE)) {
            showToast("获取个人资料失败");
        } else {
            showToast("修改失败");
        }

    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
    }

    /**
     * 请求完成之后显示个人信息
     */
    public void setSelfData() {
        ((TextView) findViewById(R.id.self_name)).setText(personalBeen.getNickname());
        if (personalBeen.getSex() == 2) {
            ((TextView) findViewById(R.id.self_sex)).setText("女");
        } else {
            ((TextView) findViewById(R.id.self_sex)).setText("男");
        }
        ((TextView) findViewById(R.id.self_phoneNo)).setText(personalBeen.getTel());
        ((TextView) findViewById(R.id.self_email)).setText(personalBeen.getEmail());
        if (!TextUtils.isEmpty(personalBeen.getBirthtime())) {
            ((TextView) findViewById(R.id.self_birthday)).setText(TimeUtil.getStrTime(personalBeen.getBirthtime(), TimeUtil.dateFormatYMD));
        } else {
            ((TextView) findViewById(R.id.self_birthday)).setText("");
        }
        ImageLoaderUtil.getInstence().loadRoundImage(this, personalBeen.getPic(), headImg);
    }

    //拍照
    private void takePictureFormCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE).format(new Date());
        String fileName = timeStamp + "_";
        File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        imageFile = null;
        try {
            imageFile = File.createTempFile(fileName, ".jpg", fileDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, CAMERA_REQESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //拍照返回
            if (requestCode == CAMERA_REQESTCODE) {
                LGImgCompressor.getInstance(this).withListener(this).
                        starCompress(Uri.fromFile(imageFile).toString(), 600, 800, 100);
            }
            //选择相册返回
            if (requestCode == 0) {
                //调用系统图库
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            LGImgCompressor.getInstance(SelfInfoActivity.this).withListener(SelfInfoActivity.this).
                                    starCompress(picturePath, 600, 800, 100);
                        }
                    }.start();
                }
            }
        }
        if (requestCode == 2) {
            switch (resultCode) {
                case UPDATA_NAME:
                    nickName = data.getStringExtra("name");
                    updataState = UPDATA_NAME;
                    updataSelfInfo();
                    break;
                case UPDATA_SEX:
                    updataState = UPDATA_SEX;
                    sex = data.getStringExtra("sex");
                    updataSelfInfo();
                    break;
                case UPDATA_EMAIL:
                    updataState = UPDATA_EMAIL;
                    email = data.getStringExtra("email");
                    updataSelfInfo();
                    break;
                case UPDATA_BIRTHDAY:
                    updataState = UPDATA_BIRTHDAY;
                    birthday = TimeUtil.getTimeStamp(data.getStringExtra("birthday"), TimeUtil.dateFormatYMD) + "";
                    updataSelfInfo();
                    break;
            }
        }
    }

    @Override
    public void onCompressStart() {

    }

    @Override
    public void onCompressEnd(LGImgCompressor.CompressResult imageOutPath) {
        if (imageOutPath.getStatus() == LGImgCompressor.CompressResult.RESULT_ERROR)//压缩失败
            return;
        File file = new File(imageOutPath.getOutPath());
        String uploadFilePath = file.getAbsolutePath();
        UploadManager.getInstence().setOnUploadFinishListener(this);
        UploadManager.getInstence().init(this).upLoadImage(uploadFilePath);
    }

    @Override
    public void onUpLoadSuc(String url) {
        ImageLoaderUtil.getInstence().loadRoundImage(this, url, headImg);
    }

    @Override
    public void onUploadFaild() {
        showToast("上传头像失败");
    }
}



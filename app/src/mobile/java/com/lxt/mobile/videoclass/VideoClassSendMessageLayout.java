package com.lxt.mobile.videoclass;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.baijiayun.LiveHelper;
import com.lxt.mobile.adapter.TextCastContentAdapter;
import com.lxt.mobile.been.VideoClassMessageBeen;
import com.lxt.mobile.utils.UIUtils;
import com.lxt.util.ToastUitl;

import java.util.ArrayList;
import java.util.List;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/5 17:22
 * @description :
 */
public class VideoClassSendMessageLayout extends RelativeLayout implements View.OnClickListener {
    private ImageView canelTextChatList;
    LiveHelper mLiveHelper;
    private RecyclerView textChatListView;//文字聊天listView 展示布局
    private LinearLayout textChatQuickChooseLayout;//文字聊天 快捷选择ImageView
    private EditText textChatEdit;//文字聊天 输入框
    private LinearLayout textChatShowLayout;//文字聊天 展示/隐藏 聊天内容布局
    private PopupWindow textChatPopup;//快捷提示框
    private LinearLayout bottomLayout;//底部聊天布局
    private TextView toastText1, toastText2,//弹出框提示文字
            toastText3, toastText4, toastText5, toastText6;
    private LinearLayout textChatSubmitLayout;//发送文字信息

    private TextCastContentAdapter mTextCastContentAdapter;//文字聊天适配器
    List<VideoClassMessageBeen> mTextCastContentList;
    private String studentText;

    public VideoClassSendMessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置所要用到的liveVIew
     */
    public void setLiveView(LiveHelper mLiveHelper) {
        this.mLiveHelper = mLiveHelper;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View textChatView = LayoutInflater.from(getContext()).inflate(R.layout.text_chat_popup_layout, null);
        textChatPopup = new PopupWindow(textChatView, 480, 550, true);
        textChatPopup.setBackgroundDrawable(new BitmapDrawable());
        bottomLayout = (LinearLayout) findViewById(R.id.textChatlayout);
        textChatSubmitLayout = (LinearLayout) findViewById(R.id.text_chat_submit_Layout);
        textChatSubmitLayout.setVisibility(View.VISIBLE);
        textChatListView = (RecyclerView) findViewById(R.id.video_text_chat_listview);
        textChatQuickChooseLayout = (LinearLayout) findViewById(R.id.video_text_chat_toast_layout);
        textChatEdit = (EditText) findViewById(R.id.video_text_chat_edit);
        //拦截EditText 输入框完成事件
        textChatEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }

        });
        textChatShowLayout = (LinearLayout) findViewById(R.id.video_text_chat_according_to_layout);
        canelTextChatList = (ImageView) findViewById(R.id.canel_text_chat_list);
        canelTextChatList.setVisibility(View.GONE);
        //弹出框提示文字
        toastText1 = (TextView) textChatView.findViewById(R.id.text_chat_quick_toast1);
        toastText2 = (TextView) textChatView.findViewById(R.id.text_chat_quick_toast2);
        toastText3 = (TextView) textChatView.findViewById(R.id.text_chat_quick_toast3);
        toastText4 = (TextView) textChatView.findViewById(R.id.text_chat_quick_toast4);
        toastText5 = (TextView) textChatView.findViewById(R.id.text_chat_quick_toast5);
        toastText6 = (TextView) textChatView.findViewById(R.id.text_chat_quick_toast6);
    }

    public void setData() {
        if (mLiveHelper == null) return;
        //内容展示布局初始化
        mTextCastContentList = new ArrayList<VideoClassMessageBeen>();
        mTextCastContentAdapter = new TextCastContentAdapter(mTextCastContentList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        textChatListView.setLayoutManager(linearLayoutManager);
        textChatListView.setAdapter(mTextCastContentAdapter);
        textChatShowLayout.setOnClickListener(this);
        textChatQuickChooseLayout.setOnClickListener(this);
        toastText1.setOnClickListener(this);
        toastText2.setOnClickListener(this);
        toastText3.setOnClickListener(this);
        toastText4.setOnClickListener(this);
        toastText5.setOnClickListener(this);
        toastText6.setOnClickListener(this);
        textChatEdit.setOnClickListener(this);
        textChatSubmitLayout.setOnClickListener(this);
        canelTextChatList.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textChatlayout:
                //聊天父布局
                break;
            case R.id.canel_text_chat_list:
                //聊天取消按钮
                textChatListView.setVisibility(View.GONE);
                // textChatSubmitLayout.setVisibility(View.GONE);
                canelTextChatList.setVisibility(View.GONE);
                break;
            case R.id.text_chat_submit_Layout:
                //发送文字信息
                if (!TextUtils.isEmpty(textChatEdit.getText().toString().trim())) {
                    //将文本发送到众望平台
                    // toID：接收者peerID，0：所有人除了自己，-1：所有人包括自己，正整数：某个人的ID
                    // text：文字
                    // textFromat：自定义的文本格式，可以为空
                    mLiveHelper.sendMessage(textChatEdit.getText().toString().trim());
                    //mSession.sendTextMessage(-1, textChatEdit.getText().toString().trim(), null);
                    studentText = textChatEdit.getText().toString().trim();
                    textChatEdit.setText("");
                } else {
                    ToastUitl.showShort("输入内容不能为空");
                }
                break;
            case R.id.video_text_chat_edit:
                //输入框拦截
                //发送布局
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.text_chat_quick_toast1:
                //你可以重复一遍吗?
                textChatEdit.setText("Can you repeat it?");
                textChatPopup.dismiss();
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.text_chat_quick_toast2:
                //我不明白?
                textChatEdit.setText("I don't understand.");
                textChatPopup.dismiss();
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.text_chat_quick_toast3:
                //你能帮我吗?
                textChatEdit.setText("Can you help me?");
                textChatPopup.dismiss();
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.text_chat_quick_toast4:
                //你可以解释一下吗?
                textChatEdit.setText("Can you explain it?");
                textChatPopup.dismiss();
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.text_chat_quick_toast5:
                //这是什么意思?
                textChatEdit.setText("What does this mean?");
                textChatPopup.dismiss();
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.text_chat_quick_toast6:
                //你可以举例吗?
                textChatEdit.setText("Can you have an example?");
                textChatPopup.dismiss();
                textChatSubmitLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.video_text_chat_toast_layout:
                //快捷提示
                textChatPopup.showAsDropDown(v);
                break;
            case R.id.video_text_chat_according_to_layout:
                //文字聊天 展示或隐藏 切换事件
                if (textChatListView.getVisibility() == View.VISIBLE) {
                    textChatListView.setVisibility(View.GONE);
                    //textChatSubmitLayout.setVisibility(View.GONE);
                    canelTextChatList.setVisibility(View.GONE);
                } else if (textChatListView.getVisibility() == View.GONE) {
                    textChatListView.setVisibility(View.VISIBLE);
                    textChatSubmitLayout.setVisibility(View.VISIBLE);
                    canelTextChatList.setVisibility(View.VISIBLE);
                }
                break;
            default:

                break;
        }
    }

    /**
     * 添加一条聊天消息
     */
    public void addOneMessage(VideoClassMessageBeen textCastContentVo) {
        if (textCastContentVo == null) {
            return;
        }
        if (textCastContentVo.getIsStudent() == 1) {
            textChatEdit.setHint("老师说：" + UIUtils.getLimitString(textCastContentVo.getContent(), 40));
        }
        //mTextCastContentAdapter.setList(mTextCastContentList);

        mTextCastContentList.add(textCastContentVo);
        mTextCastContentAdapter.notifyItemChanged(mTextCastContentList.size() - 1);
        textChatListView.smoothScrollToPosition(mTextCastContentList.size() - 1);
        textChatEdit.setText("");
        studentText = "";
    }
}

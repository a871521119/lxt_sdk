package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.been.VideoClassMessageBeen;
import com.lxt.sdk.cache.SharedPreference;

import java.util.List;


/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 杨福
 * @create-time : 2016/11/3 18:20
 * @description : 文字聊天 内容适配器
 */
public class TextCastContentAdapter extends RecyclerView.Adapter {


    private List<VideoClassMessageBeen> list;
    private Context context;
    public final int TYPE_TETURE = 1;
    public final int TEYP_STUDENT = 2;
    LayoutInflater inflater = null;

    public TextCastContentAdapter(List<VideoClassMessageBeen> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<VideoClassMessageBeen> list) {
        this.list = list;
        notifyItemInserted(list.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TETURE:
                View v1 = inflater.inflate(R.layout.text_cast_teacher_item, null, false);
                TextCastTeacherInnerClass textCastTeacherInnerClass = new TextCastTeacherInnerClass(v1);
                return textCastTeacherInnerClass;
            case TEYP_STUDENT:
                View v2 = inflater.inflate(R.layout.text_cast_student_item, null, false);
                TextCastStudentInnerClass textCastStudentInnerClass = new TextCastStudentInnerClass(v2);
                return textCastStudentInnerClass;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getIsStudent() == 1) {
            return TYPE_TETURE;
        }
        return TEYP_STUDENT;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoClassMessageBeen vo = list.get(position);
        if (vo == null) return;
        if (holder instanceof TextCastTeacherInnerClass) {
            initTeatureData((TextCastTeacherInnerClass) holder, vo);

        } else {
            initStudentData((TextCastStudentInnerClass) holder, vo);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 设置教师数据
     */
    public void initTeatureData(TextCastTeacherInnerClass mTextCastTeacher, VideoClassMessageBeen vo) {
        ImageLoaderUtil.getInstence().loadRoundImage(context, vo.getHeadImage(), mTextCastTeacher.teacherTitleImg);
        mTextCastTeacher.teacherTime.setText(vo.getCreatTime());
        mTextCastTeacher.teacherContent.setText(vo.getContent());
    }

    /**
     * 设置学生的数据
     *
     * @return
     */
    public void initStudentData(TextCastStudentInnerClass mTextCastStudent, VideoClassMessageBeen vo) {
        ImageLoaderUtil.getInstence().loadRoundImage(context, SharedPreference.getData("pic"), mTextCastStudent.studentTitleImg);
        mTextCastStudent.studentTime.setText(vo.getCreatTime());
        mTextCastStudent.studentContent.setText(vo.getContent());
    }

    class TextCastTeacherInnerClass extends RecyclerView.ViewHolder {
        //教师头像,学生头像
        private ImageView teacherTitleImg;
        //教师 当前时间 教师聊天内容,学生当前时间，学生聊天内容
        private TextView teacherTime, teacherContent;

        public TextCastTeacherInnerClass(View itemView) {
            super(itemView);
            teacherTitleImg = (ImageView) itemView.findViewById(R.id.text_cast_teacher_head_portrait);
            teacherTime = (TextView) itemView.findViewById(R.id.text_cast_teacher_current_time_text);
            teacherContent = (TextView) itemView.findViewById(R.id.text_cast_teacher_text_content);
        }
    }

    class TextCastStudentInnerClass extends RecyclerView.ViewHolder {
        //学生头像
        private ImageView studentTitleImg;
        //学生当前时间，学生聊天内容
        private TextView studentTime, studentContent;

        public TextCastStudentInnerClass(View itemView) {
            super(itemView);
            studentTitleImg = (ImageView) itemView.findViewById(R.id.text_cast_student_head_portrait);
            studentTime = (TextView) itemView.findViewById(R.id.text_cast_student_current_time_text);
            studentContent = (TextView) itemView.findViewById(R.id.text_cast_student_text_content);
        }
    }
}

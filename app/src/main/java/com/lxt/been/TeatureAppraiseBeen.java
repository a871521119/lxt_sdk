package com.lxt.been;

import com.google.gson.annotations.SerializedName;
import com.lxt.base.BaseBeen;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/25 11:03
 * @description :
 */
public class TeatureAppraiseBeen extends BaseBeen{

    /**
     * data : [{"Remark":"rrreee","CreateTime":"2017-05-22 18:05:25","Sum":5,"student_name":"NickName","Gender":2,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg"},{"Remark":"福","CreateTime":"2017-05-22 15:25:21","Sum":5,"student_name":"NickName","Gender":2,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg"},{"Remark":"bbr","CreateTime":"2017-05-19 18:08:42","Sum":5,"student_name":"NickName","Gender":2,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg"},{"Remark":"出测试","CreateTime":"2017-05-19 17:35:37","Sum":5,"student_name":"NickName","Gender":2,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg"},{"Remark":"机会","CreateTime":"2017-05-19 17:32:43","Sum":5,"student_name":"NickName","Gender":2,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg"},{"Remark":"测试","CreateTime":"2017-05-19 17:19:36","Sum":4,"student_name":"NickName","Gender":2,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg"},{"Remark":"dasfadsf","CreateTime":"2017-05-18 14:46:32","Sum":5,"student_name":"student","Gender":1,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/"},{"Remark":"ihbihbihibhiub","CreateTime":"2017-05-15 18:28:09","Sum":5,"student_name":"mawe","Gender":1,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/14951812311495181231.jpg"},{"Remark":"Good","CreateTime":"2017-05-15 17:59:48","Sum":5,"student_name":"mawe","Gender":1,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/14951812311495181231.jpg"},{"Remark":"123","CreateTime":"2017-05-15 11:18:05","Sum":5,"student_name":"mawe","Gender":1,"StudentImage":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/14951812311495181231.jpg"}]
     * Tags : {"1":"9","2":"9","3":"7","4":"7","5":"6","6":"11","7":"8","8":"10"}
     * totalCount : 27
     * pingfen : 5.0
     */

    private TagsBean Tags;
    private int totalCount;
    private String pingfen;
    private List<ConmmentBeen> data;

    public TagsBean getTags() {
        return Tags;
    }

    public void setTags(TagsBean Tags) {
        this.Tags = Tags;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getPingfen() {
        return pingfen;
    }

    public void setPingfen(String pingfen) {
        this.pingfen = pingfen;
    }

    public List<ConmmentBeen> getData() {
        return data;
    }

    public void setData(List<ConmmentBeen> data) {
        this.data = data;
    }

    public static class TagsBean {
        /**
         * 1 : 9
         * 2 : 9
         * 3 : 7
         * 4 : 7
         * 5 : 6
         * 6 : 11
         * 7 : 8
         * 8 : 10
         */

        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;
        @SerializedName("3")
        private String _$3;
        @SerializedName("4")
        private String _$4;
        @SerializedName("5")
        private String _$5;
        @SerializedName("6")
        private String _$6;
        @SerializedName("7")
        private String _$7;
        @SerializedName("8")
        private String _$8;

        public String get_$1() {
            return _$1;
        }

        public void set_$1(String _$1) {
            this._$1 = _$1;
        }

        public String get_$2() {
            return _$2;
        }

        public void set_$2(String _$2) {
            this._$2 = _$2;
        }

        public String get_$3() {
            return _$3;
        }

        public void set_$3(String _$3) {
            this._$3 = _$3;
        }

        public String get_$4() {
            return _$4;
        }

        public void set_$4(String _$4) {
            this._$4 = _$4;
        }

        public String get_$5() {
            return _$5;
        }

        public void set_$5(String _$5) {
            this._$5 = _$5;
        }

        public String get_$6() {
            return _$6;
        }

        public void set_$6(String _$6) {
            this._$6 = _$6;
        }

        public String get_$7() {
            return _$7;
        }

        public void set_$7(String _$7) {
            this._$7 = _$7;
        }

        public String get_$8() {
            return _$8;
        }

        public void set_$8(String _$8) {
            this._$8 = _$8;
        }
    }
}

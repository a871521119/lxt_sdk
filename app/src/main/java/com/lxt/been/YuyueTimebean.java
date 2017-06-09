package com.lxt.been;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 李鑫旺
 * @create-time : 2017/1/6 10:39
 * @description :
 */
public class YuyueTimebean {
    /**
     * serverTime : 1483670568
     * ServerNo : SN200
     * ResultData : [{"time":"10:00","lesson_list_id":13124},{"time":"10:30","lesson_list_id":13125},{"time":"11:00","lesson_list_id":13126},{"time":"11:30","lesson_list_id":13127},{"time":"12:00","lesson_list_id":13128},{"time":"12:30","lesson_list_id":13129},{"time":"13:00","lesson_list_id":13130},{"time":"13:30","lesson_list_id":13131},{"time":"14:00","lesson_list_id":13132},{"time":"14:30","lesson_list_id":13133},{"time":"15:00","lesson_list_id":13134},{"time":"15:30","lesson_list_id":13135}]
     */

    private int serverTime;
    private String ServerNo;
    /**
     * time : 10:00
     * lesson_list_id : 13124
     */

    private List<ResultDataBean> ResultData;

    public int getServerTime() {
        return serverTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }

    public String getServerNo() {
        return ServerNo;
    }

    public void setServerNo(String ServerNo) {
        this.ServerNo = ServerNo;
    }

    public List<ResultDataBean> getResultData() {
        return ResultData;
    }

    public void setResultData(List<ResultDataBean> ResultData) {
        this.ResultData = ResultData;
    }

    public static class ResultDataBean {
        private String time;
        private int lesson_list_id;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getLesson_list_id() {
            return lesson_list_id;
        }

        public void setLesson_list_id(int lesson_list_id) {
            this.lesson_list_id = lesson_list_id;
        }
    }
}

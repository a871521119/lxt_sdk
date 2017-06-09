package com.lxt.been;


import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/4/1 15:51
 * @description :
 */
public class ClassBeen extends BaseBeen {
    private String url;
    private String port;
    private String docIP;
    private int usertype;
    private String nickname;
    private String password;
    private int invisibleuser;
    private String serial;
    private int residualTime;
    /**
     * fileid : 10929
     * downloadpath : /upload/20161027_133730_uaeyisij.pdf
     * swfpath : /upload/20161027_133730_uaeyisij.jpg
     * pdfpath : /upload/20161027_133730_uaeyisij.pdf
     * filetype : pdf
     * pagenum : 3
     * result : 0
     */

    private WeiyifileBean weiyifile;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDocIP() {
        return docIP;
    }

    public void setDocIP(String docIP) {
        this.docIP = docIP;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInvisibleuser() {
        return invisibleuser;
    }

    public void setInvisibleuser(int invisibleuser) {
        this.invisibleuser = invisibleuser;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getResidualTime() {
        return residualTime;
    }

    public void setResidualTime(int residualTime) {
        this.residualTime = residualTime;
    }

    public WeiyifileBean getWeiyifile() {
        return weiyifile;
    }

    public void setWeiyifile(WeiyifileBean weiyifile) {
        this.weiyifile = weiyifile;
    }

}

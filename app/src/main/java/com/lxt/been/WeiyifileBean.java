package com.lxt.been;


import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/4/1 15:53
 * @description :
 */
public class WeiyifileBean extends BaseBeen {
    private String fileid;
    private String downloadpath;
    private String swfpath;
    private String pdfpath;
    private String filetype;
    private String pagenum;
    private int result;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getDownloadpath() {
        return downloadpath;
    }

    public void setDownloadpath(String downloadpath) {
        this.downloadpath = downloadpath;
    }

    public String getSwfpath() {
        return swfpath;
    }

    public void setSwfpath(String swfpath) {
        this.swfpath = swfpath;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}

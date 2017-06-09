package com.lxt.been;

import com.lxt.base.BaseBeen;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/6/2 10:36
 * @description :
 */
public class OrderClassDetailBeen extends BaseBeen{


    /**
     * title : [{"pptTitle":"GCYL B2U1L1","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/1.png","buke":0},{"pptTitle":"GCYL B2U1L2","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/2.png","buke":0},{"pptTitle":"GCYL B2U2L3","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/3.png","buke":0},{"pptTitle":"GCYL B2U3L4","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/4.png","buke":0},{"pptTitle":"GCYL B2U4L5","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/5.png","buke":0},{"pptTitle":"GCYL B2U5L6","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/6.png","buke":0},{"pptTitle":"GCYL B2U6L7","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/7.png","buke":0},{"pptTitle":"GCYL B2U6L8","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/8.png","buke":0},{"pptTitle":"GCYL B2U7L9","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/9.png","buke":0},{"pptTitle":"GCYL B2U8L10","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/10.png","buke":0},{"pptTitle":"GCYL B2U8L11","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/11.png","buke":0},{"pptTitle":"GCYL B2U9L12","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/12.png","buke":0},{"pptTitle":"GCYL B2U10L13","pronunciation":0,"vocabulary":0,"sentence_pattern":0,"fluency":0,"interactive_quality":0,"lesson_pic":"http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/13.png","buke":0}]
     * className : 绿色通道少儿版第二册（GCYL book 2）
     * bespeakCount : 0
     * evolve : 0
     * classPic : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/teachingmaterial/147755909954073004.jpg
     * section : 13
     */

    private String className;
    private int bespeakCount;
    private int evolve;
    private String classPic;
    private int section;
    private List<TitleBean> title;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getBespeakCount() {
        return bespeakCount;
    }

    public void setBespeakCount(int bespeakCount) {
        this.bespeakCount = bespeakCount;
    }

    public int getEvolve() {
        return evolve;
    }

    public void setEvolve(int evolve) {
        this.evolve = evolve;
    }

    public String getClassPic() {
        return classPic;
    }

    public void setClassPic(String classPic) {
        this.classPic = classPic;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public List<TitleBean> getTitle() {
        return title;
    }

    public void setTitle(List<TitleBean> title) {
        this.title = title;
    }

    public static class TitleBean {
        /**
         * pptTitle : GCYL B2U1L1
         * pronunciation : 0
         * vocabulary : 0
         * sentence_pattern : 0
         * fluency : 0
         * interactive_quality : 0
         * lesson_pic : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/number/1.png
         * buke : 0
         */

        private String pptTitle;
        private int pronunciation;
        private int vocabulary;
        private int sentence_pattern;
        private int fluency;
        private int interactive_quality;
        private String lesson_pic;
        private int buke;

        public String getPptTitle() {
            return pptTitle;
        }

        public void setPptTitle(String pptTitle) {
            this.pptTitle = pptTitle;
        }

        public int getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(int pronunciation) {
            this.pronunciation = pronunciation;
        }

        public int getVocabulary() {
            return vocabulary;
        }

        public void setVocabulary(int vocabulary) {
            this.vocabulary = vocabulary;
        }

        public int getSentence_pattern() {
            return sentence_pattern;
        }

        public void setSentence_pattern(int sentence_pattern) {
            this.sentence_pattern = sentence_pattern;
        }

        public int getFluency() {
            return fluency;
        }

        public void setFluency(int fluency) {
            this.fluency = fluency;
        }

        public int getInteractive_quality() {
            return interactive_quality;
        }

        public void setInteractive_quality(int interactive_quality) {
            this.interactive_quality = interactive_quality;
        }

        public String getLesson_pic() {
            return lesson_pic;
        }

        public void setLesson_pic(String lesson_pic) {
            this.lesson_pic = lesson_pic;
        }

        public int getBuke() {
            return buke;
        }

        public void setBuke(int buke) {
            this.buke = buke;
        }
    }
}

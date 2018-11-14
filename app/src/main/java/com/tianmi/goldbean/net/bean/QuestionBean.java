package com.tianmi.goldbean.net.bean;

public class QuestionBean {

    /**
     * id : 1
     * userId : 3
     * questionName : 哦送
     * questionAnswer : 搜狗
     * questionKeys : null
     * createTime : 2018-11-12T09:35:17.000+0000
     * goodsId : null
     */

    private int id;
    private int userId;
    private String questionName;
    private String questionAnswer;
    private Object questionKeys;
    private String createTime;
    private Object goodsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Object getQuestionKeys() {
        return questionKeys;
    }

    public void setQuestionKeys(Object questionKeys) {
        this.questionKeys = questionKeys;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Object goodsId) {
        this.goodsId = goodsId;
    }
}

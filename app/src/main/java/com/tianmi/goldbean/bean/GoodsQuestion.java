package com.tianmi.goldbean.bean;

public class GoodsQuestion {
    private String questionName;
    private String questionAnswer;
    private String questionKeys;
    private int userId;

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

    public String getQuestionKeys() {
        return questionKeys;
    }

    public void setQuestionKeys(String questionKeys) {
        this.questionKeys = questionKeys;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

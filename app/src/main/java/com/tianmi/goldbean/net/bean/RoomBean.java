package com.tianmi.goldbean.net.bean;

import java.util.List;

public class RoomBean {

    /**
     * goodsUrl : ,http://59.111.105.170/img//2018-11-12/2dd8139dfbbb4e84a703e29f46c7a0ae.jpg,http://59.111.105.170/img//2018-11-12/a8c10b40c5d44836a80c2ded65e8300f.jpg
     * userId : 3
     * redAmount : 100.00
     * redCount : 10
     * remainAmount : 0
     * remainCount : null
     * commentInfo : []
     */

    private String goodsUrl;
    private int userId;
    private String redAmount;
    private int redCount;
    private String remainAmount;
    private Object remainCount;

    public List<CommentInfo> getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(List<CommentInfo> commentInfo) {
        this.commentInfo = commentInfo;
    }

    private List<CommentInfo> commentInfo;
    public class CommentInfo{
        private String userPhone;
        private String userComments;

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserComments() {
            return userComments;
        }

        public void setUserComments(String userComments) {
            this.userComments = userComments;
        }
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(String redAmount) {
        this.redAmount = redAmount;
    }

    public int getRedCount() {
        return redCount;
    }

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Object getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Object remainCount) {
        this.remainCount = remainCount;
    }


}

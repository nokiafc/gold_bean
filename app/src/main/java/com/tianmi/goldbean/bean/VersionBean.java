package com.tianmi.goldbean.bean;

import java.util.Date;

public class VersionBean {
    private String versionName;    //版本号
    private Integer versionId;   //版本号码
    private Integer currentFlag;  //是否当前最新版本，0:不是；1:是
    private String url;     //下载的地址
    private String createTime;   //创建时间
    private Integer updateFlag;   //是否强制更新
    private String remark;    //更新说明

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(Integer currentFlag) {
        this.currentFlag = currentFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

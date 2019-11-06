package com.ruoyi.project.app.domain;

import java.io.Serializable;

public class Index implements Serializable {
    private static final long serialVersionUID = 5346719092855139240L;
    private Integer parentId;
    /**
     * 看板编码
     */
    private String watchCode;
    private String devCode;
    private String devType;

    /**
     * app上报异常参数
     *  工单id 异常名称 上报信息
     */
    private Integer workId;
    private String workExcTypeName;
    private String workExcInfo;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getWatchCode() {
        return watchCode;
    }

    public void setWatchCode(String watchCode) {
        this.watchCode = watchCode;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getWorkExcTypeName() {
        return workExcTypeName;
    }

    public void setWorkExcTypeName(String workExcTypeName) {
        this.workExcTypeName = workExcTypeName;
    }

    public String getWorkExcInfo() {
        return workExcInfo;
    }

    public void setWorkExcInfo(String workExcInfo) {
        this.workExcInfo = workExcInfo;
    }

    @Override
    public String toString() {
        return "Index{" +
                "parentId=" + parentId +
                ", watchCode='" + watchCode + '\'' +
                ", devCode='" + devCode + '\'' +
                ", devType='" + devType + '\'' +
                ", workId=" + workId +
                ", workExcTypeName='" + workExcTypeName + '\'' +
                ", workExcInfo='" + workExcInfo + '\'' +
                '}';
    }
}

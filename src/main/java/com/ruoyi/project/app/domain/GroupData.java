package com.ruoyi.project.app.domain;

import java.io.Serializable;

/**
 * App端交互实体封装
 * @Author: Rainey
 * @Date: 2019/12/3 15:40
 * @Version: 1.0
 **/
public class GroupData implements Serializable {
    private static final long serialVersionUID = 7057110584337894577L;
    /** 小组id */
    private int groupId;
    /** 扫码的建档信息 */
    private String pnMain;
    /** 工单id */
    private int workId;
    /** 分配数量 */
    private int workNum;
    /** 用户id */
    private int userId;
    /** 公司id */
    private Integer companyId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPnMain() {
        return pnMain;
    }

    public void setPnMain(String pnMain) {
        this.pnMain = pnMain;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public int getWorkNum() {
        return workNum;
    }

    public void setWorkNum(int workNum) {
        this.workNum = workNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "groupId=" + groupId +
                ", pnMain='" + pnMain + '\'' +
                ", workId=" + workId +
                ", workNum=" + workNum +
                ", userId=" + userId +
                '}';
    }
}

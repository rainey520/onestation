package com.ruoyi.project.quality.afterService.domain;

import com.ruoyi.project.group.groupWork.domain.GroupWork;

import java.io.Serializable;
import java.util.List;

/**
 * 扫码后对应展示数据封装
 * @Author: Rainey
 * @Date: 2019/12/4 14:06
 * @Version: 1.0
 **/
public class ScanDataView implements Serializable {
    private static final long serialVersionUID = 6585306774761424664L;
    /** 工单号 */
    private String workCode = "--";
    /** 产品编码 */
    private String pnCode = "--";
    /** 产品名称 */
    private String pnName = "--";
    /** 生产时间 年月日时分*/
    private String workTime = "--";
    /** 工单数量 */
    private int workNum;
    /** 实际做的数量 */
    private int actNum;
    /** 退货百分比 */
    private String backPercent = "--";
    /** 生产对应扫码产品的小组信息 唯一一个 */
    private GroupWork group;
    /** 生产对应扫码工单的小组信息 可能多个 */
    private List<GroupWork> otherGroups;

    public String getBackPercent() {
        return backPercent;
    }

    public void setBackPercent(String backPercent) {
        this.backPercent = backPercent;
    }

    public int getActNum() {
        return actNum;
    }

    public void setActNum(int actNum) {
        this.actNum = actNum;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getPnCode() {
        return pnCode;
    }

    public void setPnCode(String pnCode) {
        this.pnCode = pnCode;
    }

    public String getPnName() {
        return pnName;
    }

    public void setPnName(String pnName) {
        this.pnName = pnName;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public int getWorkNum() {
        return workNum;
    }

    public void setWorkNum(int workNum) {
        this.workNum = workNum;
    }

    public GroupWork getGroup() {
        return group;
    }

    public void setGroup(GroupWork group) {
        this.group = group;
    }

    public List<GroupWork> getOtherGroups() {
        return otherGroups;
    }

    public void setOtherGroups(List<GroupWork> otherGroups) {
        this.otherGroups = otherGroups;
    }

    @Override
    public String toString() {
        return "ScanDataView{" +
                "workCode='" + workCode + '\'' +
                ", pnCode='" + pnCode + '\'' +
                ", pnName='" + pnName + '\'' +
                ", workTime='" + workTime + '\'' +
                ", workNum=" + workNum +
                ", backPercent='" + backPercent + '\'' +
                ", group=" + group +
                ", otherGroups=" + otherGroups +
                '}';
    }
}

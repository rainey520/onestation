package com.ruoyi.project.production.devWorkOrder.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 工单拉长录入记录
 * @Author: Rainey
 * @Date: 2019/9/26 12:13
 * @Version: 1.0
 **/
public class WorkLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer companyId;
    private Integer workId;
    /** 工单生产数量 */
    private Integer workNumber;
    private String workProduct;
    private Integer lineId;
    private String lineName;
    /** 标准产量 */
    private Integer bzOutput;
    /** 实际产量 */
    private Integer sjOutput;
    private Date inputData;
    private Date inputTime;
    /** 总共生产数量 */
    private Integer totalOutput;
    /** 达成率 */
    private String rateNum;
    /** 工单号 */
    private String workCode;

    public String getRateNum() {
        return rateNum;
    }

    public void setRateNum(String rateNum) {
        this.rateNum = rateNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Integer getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(Integer workNumber) {
        this.workNumber = workNumber;
    }

    public String getWorkProduct() {
        return workProduct;
    }

    public void setWorkProduct(String workProduct) {
        this.workProduct = workProduct;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Integer getBzOutput() {
        return bzOutput;
    }

    public void setBzOutput(Integer bzOutput) {
        this.bzOutput = bzOutput;
    }

    public Integer getSjOutput() {
        return sjOutput;
    }

    public void setSjOutput(Integer sjOutput) {
        this.sjOutput = sjOutput;
    }

    public Date getInputData() {
        return inputData;
    }

    public void setInputData(Date inputData) {
        this.inputData = inputData;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Integer getTotalOutput() {
        return totalOutput;
    }

    public void setTotalOutput(Integer totalOutput) {
        this.totalOutput = totalOutput;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    @Override
    public String toString() {
        return "WorkLog{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", workId=" + workId +
                ", workNumber='" + workNumber + '\'' +
                ", workProduct='" + workProduct + '\'' +
                ", lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", bzOutput=" + bzOutput +
                ", sjOutput=" + sjOutput +
                ", inputData=" + inputData +
                ", inputTime=" + inputTime +
                ", totalOutput=" + totalOutput +
                ", workCode='" + workCode + '\'' +
                '}';
    }
}

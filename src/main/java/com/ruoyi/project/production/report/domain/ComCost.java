package com.ruoyi.project.production.report.domain;

import java.io.Serializable;

/**
 * 公司数据分析封装
 * @Author: Rainey
 * @Date: 2019/10/23 12:02
 * @Version: 1.0
 **/
public class ComCost implements Serializable {
    private static final long serialVersionUID = -1932277766370253713L;

    /** 开始时间 */
    private String startTime;
    /** 结束时间 */
    private String endTime;
    /** 产线id */
    private Integer lineId;
    /** 产品编码 */
    private String productCode;
    /** 公司id */
    private Integer companyId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "ComCost{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", lineId=" + lineId +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}

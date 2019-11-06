package com.ruoyi.project.app.domain;

import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.domain.WorkLog;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;
import com.ruoyi.project.production.workExceptionList.domain.WorkExceptionList;

import java.io.Serializable;
import java.util.List;

/**
 * 看板明细信息
 * @Author: Rainey
 * @Date: 2019/9/27 9:29
 * @Version: 1.0
 **/
public class WatchDetail implements Serializable {
    private static final long serialVersionUID = 1770290765496393891L;
    /** 产线信息 */
    private ProductionLine line;
    /** 正在进行的工单信息 */
    private DevWorkOrder workOrder;
    /** 工单异常信息 */
    private List<WorkExceptionList> excList;
    /** 该工单录入明细 */
    private List<WorkLog> workLogList;
    /** 当天工单信息 */
    private List<DevWorkOrder> todayWork;

    public List<DevWorkOrder> getTodayWork() {
        return todayWork;
    }

    public void setTodayWork(List<DevWorkOrder> todayWork) {
        this.todayWork = todayWork;
    }

    public List<WorkExceptionList> getExcList() {
        return excList;
    }

    public void setExcList(List<WorkExceptionList> excList) {
        this.excList = excList;
    }

    public ProductionLine getLine() {
        return line;
    }

    public void setLine(ProductionLine line) {
        this.line = line;
    }

    public DevWorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(DevWorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public List<WorkLog> getWorkLogList() {
        return workLogList;
    }

    public void setWorkLogList(List<WorkLog> workLogList) {
        this.workLogList = workLogList;
    }

    @Override
    public String toString() {
        return "WatchDetail{" +
                "line=" + line +
                ", workOrder=" + workOrder +
                ", excList=" + excList +
                ", workLogList=" + workLogList +
                ", todayWork=" + todayWork +
                '}';
    }
}

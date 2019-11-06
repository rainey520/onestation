package com.ruoyi.project.app.domain;

import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;

import java.io.Serializable;

/**
 * 看板汇总信息
 * @Author: Rainey
 * @Date: 2019/9/27 9:28
 * @Version: 1.0
 **/
public class WatchTem implements Serializable {
    private static final long serialVersionUID = -7472014080055359058L;

    /** 产线信息 */
    private ProductionLine line;
    /** 工单信息 */
    private DevWorkOrder workOrder;

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

    @Override
    public String toString() {
        return "WatchTem{" +
                "line=" + line +
                ", workOrder=" + workOrder +
                '}';
    }
}

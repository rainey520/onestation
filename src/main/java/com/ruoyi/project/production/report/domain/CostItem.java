package com.ruoyi.project.production.report.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索结果封装
 * @Author: Rainey
 * @Date: 2019/10/23 16:30
 * @Version: 1.0
 **/
public class CostItem implements Serializable {
    private static final long serialVersionUID = 2444786144371167763L;

    /** 检索天值 */
    private String dayTime;

    /** 图一x轴数据 日期年月日显示 */
    private List<String> xData;
    /** 图一y轴数据 达成率 */
    private List<Float> yData;


    /** 图二x轴数据 日期年月日*/
    private List<String> xDataTwo;
    /** 图二y轴数据 利用率 */
    private List<Float> yDataTwo;

    public List<String> getxDataTwo() {
        return xDataTwo;
    }

    public void setxDataTwo(List<String> xDataTwo) {
        this.xDataTwo = xDataTwo;
    }

    public List<Float> getyDataTwo() {
        return yDataTwo;
    }

    public void setyDataTwo(List<Float> yDataTwo) {
        this.yDataTwo = yDataTwo;
    }

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public List<Float> getyData() {
        return yData;
    }

    public void setyData(List<Float> yData) {
        this.yData = yData;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    @Override
    public String toString() {
        return "CostItem{" +
                "dayTime='" + dayTime + '\'' +
                ", xData=" + xData +
                ", yData=" + yData +
                ", xDataTwo=" + xDataTwo +
                ", yDataTwo=" + yDataTwo +
                '}';
    }
}

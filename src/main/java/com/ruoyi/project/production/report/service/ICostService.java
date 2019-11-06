package com.ruoyi.project.production.report.service;

import com.ruoyi.project.production.report.domain.ComCost;

import java.util.Map;

/**
 * 公司成本分析服务接口
 * @Author: Rainey
 * @Date: 2019/10/23 12:00
 * @Version: 1.0
 **/
public interface ICostService {

    /**
     * 数据检索
     * @param comCost 搜索条件
     * @return 结果
     */
    Map<String, Object> searchData(ComCost comCost);
}

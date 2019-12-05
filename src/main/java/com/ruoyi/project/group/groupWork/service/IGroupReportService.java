package com.ruoyi.project.group.groupWork.service;

import com.itextpdf.text.DocumentException;
import com.ruoyi.project.production.report.domain.ComCost;

import java.io.IOException;

/**
 * 小组工单报表逻辑接口
 * @Author: Rainey
 * @Date: 2019/12/4 11:33
 * @Version: 1.0
 **/
public interface IGroupReportService {

    /**
     * 生成组装车间报表信息
     * @param report 检索信息
     * @return 结果
     */
    String reportInfo(ComCost report) throws IOException, DocumentException;
}

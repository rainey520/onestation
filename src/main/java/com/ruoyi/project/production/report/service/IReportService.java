package com.ruoyi.project.production.report.service;

import com.itextpdf.text.DocumentException;
import com.ruoyi.project.production.report.domain.AppReport;

import java.io.IOException;

/**
 * PDF 报表导出
 */
public interface IReportService {

    /**
     * 产线生产报表导出
     * @param lineId 产线id
     * @param productCode 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    String lineReport(int lineId, String productCode, String startTime, String endTime) throws IOException, DocumentException;

    /**
     * 删除app端下载缓存文件
     */
    int appRemoveFile(AppReport appReport);
}

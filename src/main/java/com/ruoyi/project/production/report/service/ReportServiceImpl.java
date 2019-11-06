package com.ruoyi.project.production.report.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.PdfUtil;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.production.filesource.domain.FileSourceInfo;
import com.ruoyi.project.production.filesource.mapper.FileSourceInfoMapper;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;
import com.ruoyi.project.production.productionLine.mapper.ProductionLineMapper;
import com.ruoyi.project.production.report.domain.AppReport;
import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import com.ruoyi.project.production.timeRecord.mapper.TimeRecordMapper;
import com.ruoyi.project.production.workExceptionList.domain.WorkExceptionList;
import com.ruoyi.project.production.workExceptionList.mapper.WorkExceptionListMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {
    private static PdfPTable table;//表格创建
    private static PdfPCell cell;//单元格
    private boolean isColor = false;

    @Autowired
    private ProductionLineMapper productionLineMapper;

    @Autowired
    private DevCompanyMapper devCompanyMapper;

    @Autowired
    private DevWorkOrderMapper devWorkOrderMapper;

    @Autowired
    private FileSourceInfoMapper fileSourceInfoMapper;

    @Autowired
    private WorkExceptionListMapper workExceptionListMapper;

    @Autowired
    private TimeRecordMapper timeRecordMapper;

    @Value("${file.iso}")
    private String fileUrl;


    private float getFloat3(float val) {
        return new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 产线生产报表导出
     *
     * @param lineId      产线id
     * @param productCode 产品id
     * @param startTime   开始时间
     * @param endTime     结束时间
     */
    @Override
    public String lineReport(int lineId, String productCode, String startTime, String endTime) throws IOException, DocumentException {
        //表头参数定义
        String titleHeader[] = {"总入库数量", "总报废数量", "总投入数量", "平均达成率(%)", "平均直通率(%)", "工时利用率(%)", "生产总工时",
                "考勤总工时", "正常总工时", "加班总工时"};
        String workHeader[] = {"工单号", "产品编码", "产品名称", "工单数量", "入库数量", "报废数量", "投入数量", "达成率%", "直通率%", "用工人数"};
        String eHeader[] = {"工单号", "异常分类", "发生时间", "异常描述", "处理状态", "处理者", "解决方案", "处理时间"};

        //汇总参数
        //总入库数量
        int totalActualWarehouseNum = 0;
        //总报废数量
        int totalScrapNum = 0;
        //总投入数量
        int totalInputNum = 0;
        // 考勤总工时= 标准总工时+加班总工时
        float totalSumHour = 0.0F;
        //出勤标准总工时
        float totalWorkHour = 0.0F;
        //考勤加班总工时
        float totalOvertimeHour = 0.0F;
        //生产总工时
        float totalProductHour = 0.0F;
        //总达成率
        float totalReachRate = 0.0F;
        //总直通率
        float totalDirectPassRate = 0.0F;
        //总工单数量
        int workNum = 0;
        StringBuffer sb = new StringBuffer();
        HttpServletResponse response = ServletUtils.getResponse();
        StringBuffer fileName = new StringBuffer();
        if (lineId > 0) {
            ProductionLine line = productionLineMapper.selectProductionLineById(lineId);
            fileName.append(line.getLineName() != null ? line.getLineName() : "");
        } else {
            fileName.append("全局");
        }
        if (com.ruoyi.common.utils.StringUtils.isNotEmpty(productCode)) {
            fileName.append("-产品：" + productCode);
        } else {
            fileName.append("-全部产品-");
        }
        fileName.append("时间从" + startTime + "至" + endTime);
        response.setHeader("content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName.toString() + ".pdf");
        Document document = new Document(PageSize.A4);
        Font titleFont = new Font(BaseFont.createFont("/fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
        titleFont.setSize(14);
        PdfWriter writer;
        FileOutputStream fos = null;
        String appPath = null;
        User u = JwtUtil.getUser();

        // 文件根路径
        String filePath = RuoYiConfig.getProfile() + "watch" + u.getCompanyId();
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            //不存在创建对应文件夹
            fileDir.mkdir();
        }
        String savePath = filePath + "/" + fileName.toString() + ".pdf";
        File file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }
        fos = new FileOutputStream(savePath);
        appPath = fileUrl + "watch" + u.getCompanyId() + "/" + fileName.toString() + ".pdf";
        writer = PdfWriter.getInstance(document, fos);

        document.open();
        writer.open();

        StringBuilder dataSource = new StringBuilder();
        if (lineId > 0 && !StringUtils.isEmpty(productCode)) {
            ProductionLine line = productionLineMapper.selectProductionLineById(lineId);
            dataSource.append(line.getLineName() + ",产品/半成品：" + productCode);
        } else {
            if (lineId > 0) {
                ProductionLine line = productionLineMapper.selectProductionLineById(lineId);
                dataSource.append(line.getLineName());
            } else if (!StringUtils.isEmpty(productCode)) {
                dataSource.append("产品/半成品：" + productCode);
            } else {
                dataSource.append("全局");
            }
        }
        // 查询搜索时间段内的考勤列表
        List<TimeRecord> timeRecordList = timeRecordMapper.selectTimeRecordListRangeTime(u.getCompanyId(), lineId, startTime, endTime);
        for (TimeRecord timeRecord : timeRecordList) {
            // 正常工时
            totalWorkHour += PdfUtil.floatNull(timeRecord.getNormalHour()) * PdfUtil.IntegerNull(timeRecord.getNormalNumber());
            // 加班总工时
            totalOvertimeHour += PdfUtil.floatNull(timeRecord.getOvertimeHour()) * PdfUtil.IntegerNull(timeRecord.getOvertimeNumber());
            // 考勤总工时
            totalSumHour += totalWorkHour + totalOvertimeHour + PdfUtil.floatNull(timeRecord.getChangeHour());
        }

        DevCompany company = devCompanyMapper.selectDevCompanyById(u.getCompanyId());
        if (company != null) {
            Paragraph companyTile = new Paragraph(company.getComName(), titleFont);
            companyTile.setAlignment(1);
            document.add(companyTile);
        }
        //查询产线在该时间段内已经提交的所以工单数据
        List<DevWorkOrder> orders = devWorkOrderMapper.selectOrderByLineIsSubmit(u.getCompanyId(), productCode,
                lineId, startTime + " 00:00:00", endTime + " 23:59:59", 0);
        //数据计算
        if (orders != null && orders.size() > 0) {
            workNum = orders.size();
            for (DevWorkOrder order : orders) {
                sb.append(order.getId());
                sb.append(",");
                //达成率 = 实际计数数量/(标准工时*生产用时)
                order.setReachRate(0F);
                if (PdfUtil.IntegerNull(order.getCumulativeNumber()) > 0) {
                    //实际标准产量
                    float total = order.getProductStandardHour() * PdfUtil.floatNull(order.getSignHuor());
                    order.setReachRate(total == 0 ? 0 : getFloat3(((float) order.getCumulativeNumber() / total) * 100));
                }
                //总达成率
                totalReachRate += order.getReachRate();
                //总直通率
                totalDirectPassRate += PdfUtil.floatNull(order.getDirectPassRate());
                //入库总数量
                totalActualWarehouseNum += PdfUtil.IntegerNull(order.getActualWarehouseNum());
                //报废总数量
                totalScrapNum += PdfUtil.IntegerNull(order.getScrapNum());
                //投入总数量
                totalInputNum += PdfUtil.IntegerNull(order.getPutIntoNumber());
                //生产总工时
                totalProductHour += PdfUtil.floatNull(order.getSignHuor());
            }
        }

        //查询所以的异常事件
        List<WorkExceptionList> exceptionLists = null;
        if (sb.toString().length() > 0) {
            exceptionLists = workExceptionListMapper.selectWorkExceByWorkId(sb.toString().substring(0, sb.length() - 1));
        }
        titleFont.setSize(12);
        Paragraph text = new Paragraph("生产报表", titleFont);
        text.setAlignment(1);
        document.add(text);
        //数据来源
        titleFont.setSize(10);
        Phrase phrase = new Phrase("数据来源：" + dataSource.toString() + ";  报表时间:" + startTime + "至" + endTime, titleFont);
        document.add(phrase);
        //数据汇总表
        Font bodyFont = new Font(BaseFont.createFont("/fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
        bodyFont.setSize(8);
        PdfPTable table = new PdfPTable(titleHeader.length);
        table.setTotalWidth(580);
        table.setLockedWidth(true);
        for (String h : titleHeader) {
            table.addCell(createCell(h, bodyFont, 1, isColor));
        }
        //总入库数量
        table.addCell(createCell(PdfUtil.stringNullValue(totalActualWarehouseNum), bodyFont, 1, isColor));
        //总入报废数量
        table.addCell(createCell(PdfUtil.stringNullValue(totalScrapNum), bodyFont, 1, isColor));
        //总入投入数量
        table.addCell(createCell(PdfUtil.stringNullValue(totalInputNum), bodyFont, 1, isColor));
        if (workNum > 0) {
            //平均达成率
            table.addCell(createCell(PdfUtil.stringNullValue(getFloat3((totalReachRate / workNum))) + "%", bodyFont, 1, isColor));
            //平均直通率
            table.addCell(createCell(PdfUtil.stringNullValue(getFloat3((totalDirectPassRate / workNum))) + "%", bodyFont, 1, isColor));
        } else {
            //平均达成率
            table.addCell(createCell(0 + "%", bodyFont, 1, isColor));
            //平均直通率
            table.addCell(createCell(0 + "%", bodyFont, 1, isColor));
        }
        // 工时利用率
        if (totalProductHour != 0.0F && totalSumHour != 0.0F) {
            table.addCell(createCell(PdfUtil.stringNullValue(getFloat3((totalProductHour / totalSumHour) * 100)) + "%", bodyFont, 1, isColor));
        } else {
            table.addCell(createCell(0 + "%", bodyFont, 1, isColor));
        }
        // 精确到小数点后两位
        totalProductHour = new BigDecimal(totalProductHour).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //生产总工时
        table.addCell(createCell(PdfUtil.stringNullValue(totalProductHour), bodyFont, 1, isColor));
        // 考勤总数
        table.addCell(createCell(PdfUtil.stringNullValue(totalSumHour), bodyFont, 1, isColor));
        // 考勤正常工时数
        table.addCell(createCell(PdfUtil.stringNullValue(totalWorkHour), bodyFont, 1, isColor));
        // 考勤加班工时数
        table.addCell(createCell(PdfUtil.stringNullValue(totalOvertimeHour), bodyFont, 1, isColor));
        document.add(table);
        document.add(new Chunk("\n"));
        //工单详情
        PdfPTable detailsTable = createTable(workHeader.length + 2);
        PdfPCell titleCell = createCell("工单详情", titleFont, workHeader.length + 2, isColor);
        titleCell.setVerticalAlignment(1);
        detailsTable.addCell(titleCell);
        int i = 0;
        for (; i < workHeader.length; i++) {
            if (i == 0 || i == 1) {
                detailsTable.addCell(createCell(workHeader[i], bodyFont, 2, !isColor));
            } else {
                detailsTable.addCell(createCell(workHeader[i], bodyFont, 1, !isColor));
            }
        }
        //循环工单信息
        if (orders != null && orders.size() > 0) {
            for (DevWorkOrder order : orders) {
                //工单号
                detailsTable.addCell(createCell(order.getWorkorderNumber(), bodyFont, 2, isColor));
                //产品编码
                detailsTable.addCell(createCell(order.getProductCode(), bodyFont, 2, isColor));
                //产品名称
                detailsTable.addCell(createCell(order.getProductName(), bodyFont, 1, isColor));
                //工单数量
                detailsTable.addCell(createCell(PdfUtil.nullValue(order.getProductNumber()), bodyFont, 1, isColor));
                //入库数量
                detailsTable.addCell(createCell(PdfUtil.nullValue(order.getActualWarehouseNum()), bodyFont, 1, isColor));
                //报废数量
                detailsTable.addCell(createCell(PdfUtil.nullValue(order.getScrapNum()), bodyFont, 1, isColor));
                //投入数量
                detailsTable.addCell(createCell(PdfUtil.nullValue(order.getPutIntoNumber()), bodyFont, 1, isColor));
                //达成率
                detailsTable.addCell(createCell(PdfUtil.floatNullValue(order.getReachRate()) + "%", bodyFont, 1, isColor));
                //直通率
                detailsTable.addCell(createCell(PdfUtil.floatNullValue(order.getDirectPassRate()) + "%", bodyFont, 1, isColor));
                //用工人数
                detailsTable.addCell(createCell(PdfUtil.floatNullValue(order.getPeopleNumber()), bodyFont, 1, isColor));
            }
        }
        document.add(detailsTable);
        document.add(new Chunk("\n"));
        //工单异常
        PdfPTable eTable = createTable(eHeader.length + 7);
        PdfPCell eCell = createCell("异常事件", titleFont, eHeader.length + 7, false);
        eCell.setVerticalAlignment(1);
        eTable.addCell(eCell);
        i = 0;
        for (; i < eHeader.length; i++) {
            if (i == 0 || i == 2 || i == eHeader.length - 1) {
                eTable.addCell(createCell(eHeader[i], bodyFont, 2, !isColor));
            } else if (i == 3 || i == 6) {
                eTable.addCell(createCell(eHeader[i], bodyFont, 3, !isColor));
            } else {
                eTable.addCell(createCell(eHeader[i], bodyFont, 1, !isColor));
            }
        }
        if (exceptionLists != null && exceptionLists.size() > 0) {
            for (WorkExceptionList exe : exceptionLists) {
                //工单号
                eTable.addCell(createCell(exe.getWorkorderNumber(), bodyFont, 2, isColor));
                //异常分类
                eTable.addCell(createCell(exe.getTypeName(), bodyFont, 1, isColor));
                //发生时间
                eTable.addCell(createCell(DateUtils.getDateTime(exe.getCreateTime()), bodyFont, 2, isColor));
                //异常描述
                eTable.addCell(createCell(exe.getRemark(), bodyFont, 3, isColor));
                //处理状态
                eTable.addCell(createCell(exe.getExce(), bodyFont, 1, isColor));
                //处理者
                eTable.addCell(createCell(exe.getHandleUser(), bodyFont, 1, isColor));
                //解决方法
                eTable.addCell(createCell(exe.getHandleContent(), bodyFont, 3, isColor));
                //解决时间
                eTable.addCell(createCell(DateUtils.getDateTime(exe.getHandleTime()), bodyFont, 2, isColor));
            }
        }

        /**
         * 文件系统操作
         */
        FileSourceInfo fileSourceInfo = fileSourceInfoMapper.selectFileSourceByFileName(u.getCompanyId(), null, fileName.toString() + ".pdf");
        if (fileSourceInfo != null) {
            fileSourceInfoMapper.deleteFileSourceInfoById(fileSourceInfo.getId());
            addFileInfo(fileName.toString(), 1, appPath, savePath, u);
        } else {
            addFileInfo(fileName.toString(), 1, appPath, savePath, u);
        }


        document.add(eTable);
        document.close();
        return appPath;
    }


    /**
     * 创建单元格
     *
     * @param value 对应数值
     * @param font  对应样式
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int span, boolean color) {
        cell = new PdfPCell(new Paragraph(value, font));
        cell.setHorizontalAlignment(1);
        cell.setColspan(span);
        if (color) {
            cell.setBackgroundColor(PdfUtil.headerColor);
        }
        return cell;
    }

    /**
     * 创建表格
     *
     * @param numColumns 单元格个数
     * @return
     */
    public static PdfPTable createTable(int numColumns) {
        table = new PdfPTable(numColumns);
        table.setTotalWidth(580);
        table.setLockedWidth(true);
        return table;
    }

    /**
     * 删除app端下载缓存文件
     */
    @Override
    public int appRemoveFile(AppReport appReport) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return 0;
        }
        DevCompany company = devCompanyMapper.selectDevCompanyById(user.getCompanyId());
        if (company != null && !StringUtils.isEmpty(appReport.getFileName())) {
            String filePath = RuoYiConfig.getProfile() + company.getTotalIso() + "/" + appReport.getFileName();
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
        return 1;
    }

    /**
     * 新增文件
     *
     * @param fileName
     * @param appPath
     * @param savePath
     * @param u
     */
    private void addFileInfo(String fileName, Integer saveType, String appPath, String savePath, User u) {
        FileSourceInfo fileSourceInfo;
        fileSourceInfo = new FileSourceInfo();
        fileSourceInfo.setCompanyId(u.getCompanyId());
        // 报表文件类型
        fileSourceInfo.setSaveType(14);
        fileSourceInfo.setSaveId(saveType);
        fileSourceInfo.setSavePath(savePath);
        fileSourceInfo.setFilePath(appPath);
        fileSourceInfo.setFileName(fileName + ".pdf");
        fileSourceInfo.setCreateTime(new Date());
        fileSourceInfoMapper.insertFileInfo(fileSourceInfo);
    }

}

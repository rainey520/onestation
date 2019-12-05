package com.ruoyi.project.group.groupWork.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.PdfUtil;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import com.ruoyi.project.group.groupInfo.mapper.GroupInfoMapper;
import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkMapper;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkUserMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.filesource.domain.FileSourceInfo;
import com.ruoyi.project.production.filesource.mapper.FileSourceInfoMapper;
import com.ruoyi.project.production.report.domain.ComCost;
import com.ruoyi.project.system.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 小组报表逻辑实现
 *
 * @Author: Rainey
 * @Date: 2019/12/4 11:34
 * @Version: 1.0
 **/
@Service
public class GroupReportServiceImpl implements IGroupReportService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupReportServiceImpl.class);
    /**
     * 表格创建
     */
    private static PdfPTable table;
    /**
     * 单元创建
     */
    private static PdfPCell cell;

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private DevCompanyMapper companyMapper;

    @Autowired
    private GroupWorkMapper groupWorkMapper;

    @Autowired
    private GroupWorkUserMapper groupWorkUserMapper;

    @Autowired
    private FileSourceInfoMapper fileSourceInfoMapper;


    @Value("${file.iso}")
    private String fileUrl;

    /**
     * 生成组装车间报表信息
     *
     * @param report 检索信息
     * @return 结果
     */
    @Override
    public String reportInfo(ComCost report) throws IOException, DocumentException {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }

        //选择了小组 查询小组相关工单数据
        if (report.getLineId() != null && report.getLineId() > 0) {
            String[] groupHeader = {"小组名", "工单号", "产品编码", "工单总数/完成", "分配/完成", "操作员"};
            HttpServletResponse response = ServletUtils.getResponse();
            // 文件名
            String fileName = "";
            GroupInfo group = groupInfoMapper.selectGroupInfoById(report.getLineId());
            fileName += (StringUtils.isNotEmpty(group.getGroupName()) ? group.getGroupName() : "");
            if (StringUtils.isNotEmpty(report.getProductCode())) {
                fileName += ("-产品：" + report.getProductCode());
            }
            fileName += ("时间从" + report.getStartTime() + "至" + report.getEndTime());

            response.setHeader("content-Type", "application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".pdf");
            Document document = new Document(PageSize.A4);
            Font titleFont = new Font(BaseFont.createFont("/fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
            titleFont.setSize(14);
            // 获取对应公司单工位文件夹
            String fileDirPath = RuoYiConfig.getProfile() + "station" + user.getCompanyId();
            File fileDir = new File(fileDirPath);
            if (!fileDir.exists()) {
                //不存在创建对应文件夹
                fileDir.mkdir();
            }
            String savePath = fileDirPath + "/" + fileName + ".pdf";
            File file = new File(savePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(savePath);
            PdfWriter writer = PdfWriter.getInstance(document, fos);

            document.open();
            writer.open();

            // 表头公司信息
            DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
            if (company != null) {
                Paragraph companyTile = new Paragraph(company.getComName(), titleFont);
                companyTile.setAlignment(1);
                document.add(companyTile);
            }

            // 报表信息
            titleFont.setSize(12);
            Paragraph text = new Paragraph("组装车间报表", titleFont);
            // 居中
            text.setAlignment(1);
            document.add(text);
            titleFont.setSize(10);

            report.setCompanyId(user.getCompanyId());
            report.setStartTime(report.getStartTime() + " 00:00:00");
            report.setEndTime(report.getEndTime() + " 23:59:59");

            // 查询总数
            ComCost numInfo = groupWorkMapper.selectGroupWorkNumSum(report);
            String numStr = "";
            if (numInfo != null) {
                numStr = "   总计工单分配数量:" + PdfUtil.IntegerNull(numInfo.getAllSumNum()) + "  完成总数:" + PdfUtil.IntegerNull(numInfo.getActSumNum());
            }

            Phrase phrase = new Phrase("数据来源：" + fileName + numStr, titleFont);
            document.add(phrase);

            //数据汇总表
            Font bodyFont = new Font(BaseFont.createFont("/fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
            bodyFont.setSize(8);
            PdfPTable table = new PdfPTable(groupHeader.length);
            table.setTotalWidth(new float[]{80, 100, 100, 60, 80, 160});
            table.setLockedWidth(true);
            for (String h : groupHeader) {
                table.addCell(createCell(h, bodyFont, 1, true));
            }

            // 查询小组工单报表详情
            List<GroupWork> groupWorkList = groupWorkMapper.selectGroupWorkReport(report);
            List<String> userNames;
            StringBuilder sb = null;
            for (GroupWork item : groupWorkList) {
                sb = new StringBuilder();
                table.addCell(createCell(PdfUtil.nullStrVal(item.getGroupName()), bodyFont, 1, false));
                table.addCell(createCell(PdfUtil.nullStrVal(item.getWorkCode()), bodyFont, 1, false));
                table.addCell(createCell(PdfUtil.nullStrVal(item.getPnCode()), bodyFont, 1, false));
                table.addCell(createCell(item.getWorkAllNum() + "/" + item.getWorkActNum(), bodyFont, 1, false));
                table.addCell(createCell(item.getWorkNum() + "/" + item.getActualNum(), bodyFont, 1, false));
                // 查询作业员
                userNames = groupWorkUserMapper.selectGroupFinishWorkUsers(item.getId());
                for (String userName : userNames) {
                    sb.append(userName).append(",");
                    if (sb.length() > 12) {
                        sb.append("...");
                        break;
                    }
                }
                table.addCell(createCell(sb.toString(), bodyFont, 1, false));
            }
            document.add(table);

            // 文件全路径
            String filePath = fileUrl + "station" + user.getCompanyId() + "/" + fileName + ".pdf";
            // 查询文件信息
            FileSourceInfo fileSourceInfo = fileSourceInfoMapper.selectFileSourceByFileName(user.getCompanyId(), null, fileName + ".pdf");
            if (fileSourceInfo != null) {
                fileSourceInfoMapper.deleteFileSourceInfoById(fileSourceInfo.getId());
                addFileInfo(fileName, filePath, savePath, user);
            } else {
                addFileInfo(fileName, filePath, savePath, user);
            }

            document.close();
            return filePath;

            // 未选择小组 查询所有工单数据
        } else {
            String[] workHeader = {"工单号", "产品编码", "产品名称", "工单总数/完成数", "工单时间", "生产小组信息"};
            HttpServletResponse response = ServletUtils.getResponse();
            // 文件名
            String fileName = "";
            if (StringUtils.isNotEmpty(report.getProductCode())) {
                fileName += ("产品：" + report.getProductCode());
            }
            fileName += ("时间从" + report.getStartTime() + "至" + report.getEndTime());

            response.setHeader("content-Type", "application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".pdf");
            Document document = new Document(PageSize.A4);
            Font titleFont = new Font(BaseFont.createFont("/fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
            titleFont.setSize(14);

            // 获取对应公司单工位文件夹
            String fileDirPath = RuoYiConfig.getProfile() + "station" + user.getCompanyId();
            File fileDir = new File(fileDirPath);
            if (!fileDir.exists()) {
                //不存在创建对应文件夹
                fileDir.mkdir();
            }
            String savePath = fileDirPath + "/" + fileName + ".pdf";
            File file = new File(savePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(savePath);
            PdfWriter writer = PdfWriter.getInstance(document, fos);

            document.open();
            writer.open();

            // 表头公司信息
            DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
            if (company != null) {
                Paragraph companyTile = new Paragraph(company.getComName(), titleFont);
                companyTile.setAlignment(1);
                document.add(companyTile);
            }

            // 报表信息
            titleFont.setSize(12);
            Paragraph text = new Paragraph("组装车间报表", titleFont);
            // 居中
            text.setAlignment(1);
            document.add(text);
            titleFont.setSize(10);

            report.setCompanyId(user.getCompanyId());
            report.setStartTime(report.getStartTime() + " 00:00:00");
            report.setEndTime(report.getEndTime() + " 23:59:59");

            // 查询总数
            ComCost numInfo = groupWorkMapper.selectWorkNumSum(report);
            String numStr = "";
            if (numInfo != null) {
                numStr = "   总计工单数量:" + PdfUtil.IntegerNull(numInfo.getAllSumNum()) + "  完成总数:" + PdfUtil.IntegerNull(numInfo.getActSumNum());
            }
            Phrase phrase = new Phrase("数据来源：" + fileName + numStr, titleFont);
            document.add(phrase);

            //数据汇总表
            Font bodyFont = new Font(BaseFont.createFont("/fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
            bodyFont.setSize(8);
            PdfPTable table = new PdfPTable(workHeader.length);
            table.setTotalWidth(new float[]{80, 90, 90, 70, 50, 200});
            table.setLockedWidth(true);
            for (String h : workHeader) {
                table.addCell(createCell(h, bodyFont, 1, true));
            }

            // 查询检索条件的所有的工单信息
            List<DevWorkOrder> workOrderList = groupWorkMapper.selectAllGroupWorkReport(report);
            List<GroupWork> groupWorks;
            StringBuilder sb = null;
            for (DevWorkOrder item : workOrderList) {
                sb = new StringBuilder();
                table.addCell(createCell(PdfUtil.nullStrVal(item.getWorkorderNumber()), bodyFont, 1, false));
                table.addCell(createCell(PdfUtil.nullStrVal(item.getProductCode()), bodyFont, 1, false));
                table.addCell(createCell(PdfUtil.nullStrVal(item.getProductName()), bodyFont, 1, false));
                table.addCell(createCell(item.getProductNumber() + "/" + item.getCumulativeNumber(), bodyFont, 1, false));
                if (item.getStartTime() != null) {
                    table.addCell(createCell(DateUtils.dateTime(item.getStartTime()), bodyFont, 1, false));
                } else {
                    table.addCell(createCell("--", bodyFont, 1, false));
                }

                // 查询生产该工单的三个小组信息
                groupWorks = groupWorkMapper.selectThreeGroupWorkInfoByWorkId(item.getId());
                for (GroupWork group : groupWorks) {
                    sb.append(PdfUtil.nullStrVal(group.getGroupName())).append("生产").append(group.getActualNum()).append(";");
                }
                table.addCell(createCell(sb.toString(), bodyFont, 1, false));
            }
            document.add(table);

            String filePath = fileUrl + "station" + user.getCompanyId() + "/" + fileName + ".pdf";

            // 查询文件信息
            FileSourceInfo fileSourceInfo = fileSourceInfoMapper.selectFileSourceByFileName(user.getCompanyId(), null, fileName + ".pdf");
            if (fileSourceInfo != null) {
                fileSourceInfoMapper.deleteFileSourceInfoById(fileSourceInfo.getId());
                addFileInfo(fileName, filePath, savePath, user);
            } else {
                addFileInfo(fileName, filePath, savePath, user);
            }

            document.close();
            return filePath;

        }
    }

    /**
     * 创建单元格
     *
     * @param value 显示值
     * @param font  字体样式
     * @param span  字体大小
     * @param color 是否需要颜色背景
     * @return cell
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
     * 新增文件体系
     *
     * @param fileName 文件类型
     * @param saveType 保存类型
     * @param filePath 文件全路径地址
     * @param savePath 文件保存地址
     * @param user     用户信息
     */
    private void addFileInfo(String fileName, String filePath, String savePath, User user) {
        FileSourceInfo fileSourceInfo;
        fileSourceInfo = new FileSourceInfo();
        fileSourceInfo.setCompanyId(user.getCompanyId());
        // 组装车间报表类型
        fileSourceInfo.setSaveType(15);
        fileSourceInfo.setSavePath(savePath);
        fileSourceInfo.setFilePath(filePath);
        fileSourceInfo.setFileName(fileName + ".pdf");
        fileSourceInfo.setCreateTime(new Date());
        fileSourceInfoMapper.insertFileInfo(fileSourceInfo);
    }

}

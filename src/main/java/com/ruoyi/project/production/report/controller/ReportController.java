package com.ruoyi.project.production.report.controller;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.service.IDevCompanyService;
import com.ruoyi.project.product.list.service.IDevProductListService;
import com.ruoyi.project.production.productionLine.service.IProductionLineService;
import com.ruoyi.project.production.report.domain.AppReport;
import com.ruoyi.project.production.report.service.IReportService;
import com.ruoyi.project.production.singleWork.service.ISingleWorkService;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.domain.UserApp;
import com.ruoyi.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/production/report")
public class ReportController extends BaseController {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    private String prefix = "production/report";

    @Autowired
    private IReportService reportService;

    @Autowired
    private IDevCompanyService companyService;


    @RequestMapping("/download")
    @ResponseBody
    public void downloadFile(String filePath) throws UnsupportedEncodingException {
        HttpServletResponse response = ServletUtils.getResponse();
        String fileName = filePath.substring(filePath.lastIndexOf("/"));
        User user = JwtUtil.getUser();
        if (user != null) {
            String savePath = RuoYiConfig.getProfile() + "station" + user.getCompanyId() + "/" + fileName;
            // 下载文件
            File file = new File(savePath);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setHeader("Content-Length", "" + file.length());
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream outputStream = response.getOutputStream();
                    int index = bis.read(buffer);
                    while (index != -1) {
                        outputStream.write(buffer, 0, index);
                        index = bis.read(buffer);
                    }
                } catch (Exception e) {
                    LOGGER.error("下载文件出现异常：" + e.getMessage());
                    // e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            // e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            // e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 查询报表数
     *
     * @return
     */
    @GetMapping
    @RequiresPermissions("production:report:view")
    public String report(ModelMap map) {
        User user = JwtUtil.getUser();
        map.put("vipSign",0);
        // 查询公司的会员信息
        DevCompany company = companyService.selectDevCompanyById(user.getCompanyId());
        if (company != null) {
            map.put("vipSign",company.getSign());
        }
        if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
            return prefix + "/reportEn";
        }
        return prefix + "/report";
    }

    /**
     * 导出产线报表数据
     *
     * @param lineId      产线编号
     * @param productCode 产品编码
     * @param startTime   开始时间
     * @param endTime     结束时间
     */
    @ResponseBody
    @RequestMapping("/line/pdf")
    @RequiresPermissions("production:report:pdf")
    public AjaxResult exportReport(int lineId, String productCode, String startTime, String endTime) {
        try {
            return AjaxResult.success(reportService.lineReport(lineId, productCode, startTime, endTime));
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }


    /******************************************************************************************************
     *********************************** app端数据报表交互 *************************************************
     ******************************************************************************************************/

    @Autowired
    private IProductionLineService lineService;

    @Autowired
    private ISingleWorkService singleWorkService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDevProductListService productService;

    /**
     * 响应app产线车间产品信息
     * 首次传参 wlSign 0、流水线，1、车间
     * 在线用户 uid
     */
    @PostMapping("/appReport")
    @ResponseBody
    public AjaxResult appReport(@RequestBody AppReport appReport) {
        if (appReport != null) {
            Map<String, Object> map = new HashMap<>(16);
            if (appReport.getWlSign() != null) {
                if (appReport.getWlSign().equals(WorkConstants.SING_LINE)) {
                    // 产线
                    map.put("lineList", lineService.appSelectLineList(null));
                } else if (appReport.getWlSign().equals(WorkConstants.SING_SINGLE)) {
                    // 车间
                    map.put("cjList", singleWorkService.selectSingleWorkByParentId(null, 0));
                    // 所有用户
                    map.put("userList", userService.appSelectUserList(new UserApp()));
                }
            }
            // 所有产品
            map.put("productList", productService.selectProductAll());
            return AjaxResult.success("请求成功", map);
        }
        return error();
    }

    /**
     * app端导出数据报表
     */
    @PostMapping("/appReportData")
    @ResponseBody
    public AjaxResult appReportData(@RequestBody AppReport appReport) {
        try {
            if (appReport != null) {
                return AjaxResult.success("请求成功", reportService.lineReport(appReport.getLineId(), appReport.getProductCode(), appReport.getStartTime(), appReport.getEndTime()));
            }
            return error();
        } catch (Exception e) {
            LOGGER.error("app导出报表出现异常：" + e.getMessage());
            return error();
        }
    }

    /**
     * 删除文件
     */
    @PostMapping("/appRemoveFile")
    @ResponseBody
    public AjaxResult appRemoveFile(@RequestBody AppReport appReport) {
        try {
            if (appReport != null && StringUtils.isNotEmpty(appReport.getFileName())) {
                return toAjax(reportService.appRemoveFile(appReport));
            }
            return error();
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }
}

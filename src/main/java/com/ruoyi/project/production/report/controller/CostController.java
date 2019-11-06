package com.ruoyi.project.production.report.controller;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.production.report.domain.ComCost;
import com.ruoyi.project.production.report.service.ICostService;
import com.ruoyi.project.production.report.service.IReportService;
import com.ruoyi.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 公司成本分析控制层
 * @Author: Rainey
 * @Date: 2019/10/23 11:59
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/production/cost")
public class CostController {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(CostController.class);

    @Autowired
    private ICostService costService;

    @Autowired
    private IReportService reportService;

    /**
     * 跳转公司分析成本页面
     */
    @GetMapping()
    @RequiresPermissions("production:cost:list")
    public String cost(){
        User user = JwtUtil.getUser();
        if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
            return "production/report/costEn";
        }
        return "production/report/cost";
    }

    /**
     * 查看图表数据
     */
    @RequestMapping("/searchData")
    @RequiresPermissions("production:cost:list")
    @ResponseBody
    public Map<String, Object> searchData(ComCost comCost){
        return costService.searchData(comCost);
    }

    /**
     * 查看报表数据
     */
    @RequestMapping("/searchReport")
    @RequiresPermissions("production:report:pdf")
    @ResponseBody
    public AjaxResult exportReport(ComCost comCost) {
        try {
            return AjaxResult.success(reportService.lineReport(comCost.getLineId(), comCost.getProductCode(), comCost.getStartTime(), comCost.getEndTime()));
        } catch (Exception e) {
            LOGGER.error("公司成本分析控制层查看报表数据出现异常:" + e.getMessage());
            return AjaxResult.error();
        }
    }

}

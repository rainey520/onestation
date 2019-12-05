package com.ruoyi.project.group.groupWork.controller;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.group.groupWork.service.IGroupReportService;
import com.ruoyi.project.production.report.domain.ComCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 小组工单报表控制层
 *
 * @Author: Rainey
 * @Date: 2019/12/4 11:31
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/group/report")
public class GroupReportController {
    private String prefix = "group/groupWork";

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupReportController.class);

    @Autowired
    private IGroupReportService reportService;

    /**
     * 跳转组装车间报表页面
     */
    @GetMapping("/list")
    public String list() {
        return prefix + "/workReport";
    }

    /**
     * 生成小组工单报表信息
     */
    @PostMapping("/info")
    @ResponseBody
    public AjaxResult reportInfo(@RequestBody ComCost report) {
        try {
            String filePath = reportService.reportInfo(report);
            if (StringUtils.isNotEmpty(filePath)) {
                return AjaxResult.api(0, filePath, null);
            } else {
                return AjaxResult.error("查看报表失败");
            }
        } catch (BusinessException e) {
            LOGGER.error("生成小组工单报表信息出现异常：" + e.getMessage());
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("生成小组工单报表信息出现异常：" + e.getMessage());
            return AjaxResult.error();
        }
    }
}

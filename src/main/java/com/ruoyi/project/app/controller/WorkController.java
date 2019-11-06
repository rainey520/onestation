package com.ruoyi.project.app.controller;

import com.ruoyi.common.constant.CompanyConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.app.domain.Index;
import com.ruoyi.project.device.devCompany.service.IDevCompanyService;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.service.IDevWorkOrderService;
import com.ruoyi.project.production.filesource.domain.FileSourceInfo;
import com.ruoyi.project.production.filesource.service.IFileSourceInfoService;
import com.ruoyi.project.production.report.domain.ComCost;
import com.ruoyi.project.production.report.service.ICostService;
import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import com.ruoyi.project.production.timeRecord.service.ITimeRecordService;
import com.ruoyi.project.production.workExceptionType.service.IWorkExceptionTypeService;
import com.ruoyi.project.system.menu.service.IMenuService;
import com.ruoyi.project.system.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * app工单交互逻辑
 *
 * @Author: Rainey
 * @Date: 2019/9/26 17:50
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/work")
public class WorkController {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkController.class);

    @Autowired
    private IDevWorkOrderService workOrderService;

    @Autowired
    private IFileSourceInfoService fileSourceInfoService;

    @Autowired
    private IWorkExceptionTypeService workExcTypeService;

    @Autowired
    private ITimeRecordService timeRecordService;

    @Autowired
    private ICostService costService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IDevCompanyService companyService;

    /**
     * 编辑保存工单
     */
    @RequestMapping("/appWorkSave")
    public AjaxResult appWorkSave(@RequestBody DevWorkOrder workOrder) {
        try {
            int i = workOrderService.updateDevWorkOrder(workOrder);
            return i > 0 ? AjaxResult.success(workOrder.getId()) : AjaxResult.error();
        } catch (BusinessException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通过工单id拉取工单信息
     */
    @RequestMapping("/appSelectWorkById")
    public AjaxResult appSelectWorkById(@RequestBody DevWorkOrder workOrder) {
        try {
            if (workOrder != null && workOrder.getId() != null) {
                return AjaxResult.success(workOrderService.findWorkInfoById(workOrder.getId()));
            }
        } catch (BusinessException e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error();
    }

    /**
     * 查看历史报表信息
     */
    @RequestMapping("/appSelectHistoryReport")
    public AjaxResult appSelectHistoryReport(@RequestBody FileSourceInfo fileSourceInfo) {
        fileSourceInfo.setSaveType(14);
        return AjaxResult.success(fileSourceInfoService.selectFileSourceInfoList(fileSourceInfo));
    }

    /**
     * app拉取异常类型列表信息
     */
    @RequestMapping("/workExcType")
    public Map<String, Object> workExcType() {
        return workExcTypeService.selectWorkExcTypeList();
    }


    /**
     * app端上报工单异常信息
     */
    @RequestMapping("/workExc")
    public Map<String, Object> appWorkExc(@RequestBody Index index) {
        return workOrderService.appWorkExc(index);
    }

    /**
     * 拉取考勤信息
     */
    @RequestMapping("/timeRecord")
    public Map<String, Object> appSelectTimeRecord(@RequestBody TimeRecord timeRecord) {
        Map<String, Object> map = new HashMap<>(16);
        // 确认昨天的考勤信息
        TimeRecord record = timeRecordService.selectTimeRecordByLineIdYesterday(timeRecord.getLineId());
        if (CompanyConstants.LINE_TIME_RECORD_NOT_CONFIRM.equals(record.getInputFlag())) {
            map.put("data",record);
            map.put("code", 0);
            map.put("msg", "请求失败");
            return map;
        } else {
            map.put("data", record);
            map.put("code", 1);
            map.put("msg", "请求成功");
            return map;
        }
    }

    /**
     * 拉取今日考勤信息
     */
    @RequestMapping("/todayTime")
    public AjaxResult appSelectTodayTime(@RequestBody TimeRecord timeRecord){
        if (timeRecord != null && timeRecord.getLineId() != null) {
            return AjaxResult.success(timeRecordService.selectTimeRecordByLineIdToday(timeRecord.getLineId()));
        }
        return AjaxResult.error();
    }

    /**
     * 新增修改考勤信息
     */
    @RequestMapping("/saveTimeRecord")
    public AjaxResult appSaveTimeRecord(@RequestBody TimeRecord timeRecord) {
        try {
            if (timeRecord != null && timeRecord.getLineId() > 0) {
                if (timeRecord.getId() != null && timeRecord.getId() > 0) {
                    // 修改考勤
                    timeRecordService.updateTimeRecord(timeRecord);
                } else {
                    // 新增考勤
                    timeRecordService.insertTimeRecord(timeRecord);
                }
                return AjaxResult.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("app端修改考勤信息出现异常：" + e.getMessage());
        }
        return AjaxResult.error();
    }


    /**
     * app端拉取图表分析数据
     */
    @RequestMapping("/costRecord")
    public Map<String, Object> costRecord(@RequestBody ComCost comCost) {
        return costService.searchData(comCost);
    }

    /**
     * app 端拉数据分析权限相关信息
     */
    @RequestMapping("/costAuth")
    public Map<String,Object> companyCostAuth(@RequestBody Index index){
        Map<String,Object> map = new HashMap<>(16);
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                map.put("code",0);
                map.put("msg","用户未登录或登录超时");
                return map;
            }
            map.put("company",companyService.selectDevCompanyById(user.getCompanyId()));
            if (index != null && index.getParentId() != null) {
                map.put("menuList", menuService.selectMenuListByParentIdAndUserId(user.getUserId().intValue(), index.getParentId()));
            }
            map.put("code",1);
            map.put("msg","请求成功");
            return map;
        } catch (Exception e) {
            LOGGER.error("app端拉数据分析权限相关信息出现异常：" + e.getMessage());
            map.put("code",0);
            map.put("msg","请求失败");
            return map;
        }
    }
}

package com.ruoyi.project.quality.afterService.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.quality.afterService.domain.AfterService;
import com.ruoyi.project.quality.afterService.service.IAfterServiceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 售后服务 信息操作处理
 *
 * @author sj
 * @date 2019-08-20
 */
@Controller
@RequestMapping("/quality/afterService")
public class AfterServiceController extends BaseController {
    private String prefix = "quality/afterService";
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AfterServiceController.class);

    @Autowired
    private IAfterServiceService afterServiceService;

    @RequiresPermissions("quality:afterService:view")
    @GetMapping()
    public String afterService() {
        return prefix + "/afterService";
    }

    /**
     * 查询售后服务列表
     */
    @RequiresPermissions("quality:afterService:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AfterService afterService) {
        startPage();
        List<AfterService> list = afterServiceService.selectAfterServiceList(afterService);
        return getDataTable(list);
    }

    /**
     * 通过搜索条件查询售后服务列表
     */
    @PostMapping("/listBySearchInfo")
    @ResponseBody
    public AjaxResult listBySearchInfo(@RequestBody AfterService afterService) {
        try {
            return AjaxResult.success(afterServiceService.selectListBySearchInfo(afterService));
        } catch (Exception e) {
            log.error("售后查询出现异常:" + e.getMessage());
            return error();
        }
    }

    /**
     * 新增售后服务
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存售后服务
     */
    @RequiresPermissions("quality:afterService:add")
    @Log(title = "售后服务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AfterService afterService) {
        return afterServiceService.insertAfterService(afterService);
    }


    /**
     * 删除售后服务
     */
    @RequiresPermissions("quality:afterService:add")
    @Log(title = "售后服务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(afterServiceService.deleteAfterServiceByIds(ids));
    }

    /**
     * 用户输入建档信息查询对应数据
     */
    @RequestMapping("/searchPnMain")
    @ResponseBody
    public AjaxResult searchPnMain(String pnMain){
        return afterServiceService.searchPnMain(pnMain);
    }

}

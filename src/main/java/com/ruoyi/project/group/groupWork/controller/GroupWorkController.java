package com.ruoyi.project.group.groupWork.controller;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.CodeUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.group.groupInfo.service.IGroupInfoService;
import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.group.groupWork.service.IGroupWorkInfoService;
import com.ruoyi.project.group.groupWork.service.IGroupWorkService;
import com.ruoyi.project.product.importConfig.domain.ImportConfig;
import com.ruoyi.project.product.importConfig.service.IImportConfigService;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.quality.mesBatch.domain.MesBatch;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小组工单关联管理 信息操作处理
 *
 * @author sj
 * @date 2019-11-30
 */
@Controller
@RequestMapping("/group/groupWork")
public class GroupWorkController extends BaseController {
    private String prefix = "group/groupWork";

    @Autowired
    private IGroupWorkService groupWorkService;

    @Autowired
    private IImportConfigService configService;

    @Autowired
    private IGroupInfoService groupInfoService;

    @Autowired
    private IGroupWorkInfoService groupWorkInfoService;


    @RequiresPermissions("group:groupWork:view")
    @GetMapping()
    public String groupWork() {
        return prefix + "/groupWork";
    }

    /**
     * 查询小组工单关联管理列表
     */
    @RequiresPermissions("group:groupWork:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DevWorkOrder workOrder) {
        startPage();
        List<DevWorkOrder> list = groupWorkService.selectGroupWorkList(workOrder);
        return getDataTable(list);
    }


    /**
     * 查询对应工单分配所在的小组信息
     */
    @RequiresPermissions("group:groupWork:list")
    @PostMapping("/disWorkList")
    @ResponseBody
    public TableDataInfo disWorkList(GroupWork groupWork) {
        startPage();
        List<GroupWork> list = groupWorkService.selectDisGroupWorkList(groupWork);
        return getDataTable(list);
    }


    /**
     * 导出小组工单关联管理列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DevWorkOrder workOrder) {
        List<DevWorkOrder> list = groupWorkService.selectGroupWorkList(workOrder);
        ExcelUtil<DevWorkOrder> util = new ExcelUtil<DevWorkOrder>(DevWorkOrder.class);
        return util.exportExcel(list, "小组工单信息");
    }

    /**
     * 新增小组工单关联管理
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
        map.put("workCode", CodeUtils.getWorkOrderCode());
        return prefix + "/add";
    }

    /**
     * 新增保存小组工单关联管理
     */
    @Log(title = "小组工单关联管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DevWorkOrder workOrder) {
        try {
            return toAjax(groupWorkService.insertGroupWork(workOrder));
        } catch (BusinessException e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改小组工单关联管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        GroupWork groupWork = groupWorkService.selectGroupWorkById(id);
        mmap.put("groupWork", groupWork);
        return prefix + "/edit";
    }

    /**
     * 修改保存小组工单关联管理
     */
    @Log(title = "小组工单关联管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GroupWork groupWork) {
        return toAjax(groupWorkService.updateGroupWork(groupWork));
    }

    /**
     * 删除小组整个工单
     */
    @Log(title = "小组工单关联管理", businessType = BusinessType.DELETE)
    @PostMapping("/removeWork")
    @ResponseBody
    public AjaxResult removeWork(Integer id) {
        try {
            return toAjax(groupWorkService.deleteGroupWorkById(id));
        } catch (BusinessException e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除下发到小组的某个工单信息
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Integer id){
        try {
            return toAjax(groupWorkService.deleteGroupWorkInfoById(id));
        } catch (BusinessException e) {
            return error(e.getMessage());
        }
    }

    /**
     * 跳转MES工单规则配置
     */
    @GetMapping("/workMainMesConfig")
    public String workMainMesConfig(ModelMap map) {
        //4、工单MES建档规则
        map.put("cType", 4);
        ImportConfig importConfig = configService.selectImportConfigByType(4);
        if (importConfig == null) {
            importConfig = new ImportConfig();
        }
        map.put("config", importConfig);
        return prefix + "/workMesConfig";
    }

    /**
     * 保存工单MES规则信息
     */
    @PostMapping("/saveWorkMesConfig")
    @ResponseBody
    public AjaxResult saveWorkMesConfig(ImportConfig importConfig) {
        return toAjax(configService.saveConfig(importConfig));
    }

    /**
     * 跳转分配工单页面
     */
    @GetMapping("/distributeWork")
    public String distributeWork(Integer workId, int workNum,int workStatus, ModelMap map) {
        map.put("workId", workId);
        map.put("workStatus",workStatus);
        map.put("allNum",workNum);
        // 查询工单已分配的数量
        map.put("disNum",groupWorkService.selectWorkDisNum(workId));
        map.put("cfWorkList", groupInfoService.selectGroupInfoNoWork(workId));
        return prefix + "/distributeWork";
    }

    /**
     * 重新加载数据
     */
    @PostMapping("/reloadData")
    @ResponseBody
    public AjaxResult reloadData(int workId){
        Map<String,Object> map = new HashMap<>(16);
        map.put("disNum",groupWorkService.selectWorkDisNum(workId));
        map.put("cfWorkList", groupInfoService.selectGroupInfoNoWork(workId));
        return AjaxResult.success(map);
    }

    /**
     * 保存工单分配操作
     */
    @PostMapping("/saveDistributeWork")
    @ResponseBody
    public AjaxResult saveDistributeWork(GroupWork groupWork){
        return groupWorkService.saveDistributeWork(groupWork);
    }

    /**
     * 工单负责人结束工单信息
     */
    @PostMapping("/finishWork")
    @ResponseBody
    public AjaxResult finishWork(Integer id){
        return groupWorkService.finishWork(id);
    }

    /**
     * 小组结束对应工单任务
     */
    @RequiresPermissions("group:groupInfo:finishTask")
    @PostMapping("/finishTask")
    @ResponseBody
    public AjaxResult finishTask(Integer id){
        return groupWorkService.finishTask(id);
    }

    /**
     * 导出对应工单的产品建档信息
     */
    @PostMapping("/exportAllPnInfo")
    @ResponseBody
    public AjaxResult exportAllPnInfo(Integer workId,String workCode) {
        String fileName = "建档信息";
        List<GroupWorkInfo> list = groupWorkInfoService.selectGroupWorkInfoByWorkId(workId);
        if (StringUtils.isNotEmpty(workCode)) {
            fileName = workCode + "建档信息";
        }
        ExcelUtil<GroupWorkInfo> util = new ExcelUtil<GroupWorkInfo>(GroupWorkInfo.class);
        return util.exportExcel(list, fileName);
    }

}

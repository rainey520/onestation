package com.ruoyi.project.group.groupWork.controller;

import com.ruoyi.common.constant.GroupConstants;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.group.groupWork.service.IGroupWorkInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工单产品建档 信息操作处理
 *
 * @author sj
 * @date 2019-11-30
 */
@Controller
@RequestMapping("/group/groupWorkInfo")
public class GroupWorkInfoController extends BaseController {
    private String prefix = "group/groupWorkInfo";

    @Autowired
    private IGroupWorkInfoService groupWorkInfoService;

    @RequiresPermissions("group:groupWorkInfo:view")
    @GetMapping()
    public String groupWorkInfo() {
        return prefix + "/groupWorkInfo";
    }

    /**
     * 查询工单产品建档列表
     */
    @RequiresPermissions("group:groupWorkInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GroupWorkInfo groupWorkInfo) {
        startPage();
        List<GroupWorkInfo> list = groupWorkInfoService.selectGroupWorkInfoList(groupWorkInfo);
        return getDataTable(list);
    }


    /**
     * 导出工单产品建档列表
     */
    @RequiresPermissions("group:groupWorkInfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupWorkInfo groupWorkInfo) {
        List<GroupWorkInfo> list = groupWorkInfoService.selectGroupWorkInfoList(groupWorkInfo);
        ExcelUtil<GroupWorkInfo> util = new ExcelUtil<GroupWorkInfo>(GroupWorkInfo.class);
        return util.exportExcel(list, "groupWorkInfo");
    }

    /**
     * 新增工单产品建档
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存工单产品建档
     */
    @RequiresPermissions("group:groupWorkInfo:add")
    @Log(title = "工单产品建档", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GroupWorkInfo groupWorkInfo) {
        return toAjax(groupWorkInfoService.insertGroupWorkInfo(groupWorkInfo));
    }

    /**
     * 修改工单产品建档
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        GroupWorkInfo groupWorkInfo = groupWorkInfoService.selectGroupWorkInfoById(id);
        mmap.put("groupWorkInfo", groupWorkInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存工单产品建档
     */
    @RequiresPermissions("group:groupWorkInfo:edit")
    @Log(title = "工单产品建档", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GroupWorkInfo groupWorkInfo) {
        return toAjax(groupWorkInfoService.updateGroupWorkInfo(groupWorkInfo));
    }

    /**
     * 删除工单产品建档
     */
    @RequiresPermissions("group:groupWorkInfo:remove")
    @Log(title = "工单产品建档", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(groupWorkInfoService.deleteGroupWorkInfoByIds(ids));
    }

    /**
     * 通过工单id删除该工单未扫码的建档信息
     */
    @RequestMapping("/removeWorkInfoNotScan")
    @ResponseBody
    public AjaxResult removeWorkInfoNotScan(Integer workId){
        return groupWorkInfoService.removeWorkInfoNotScan(workId, GroupConstants.SACN_SIGN_NO);
    }

}

package com.ruoyi.project.app.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.app.domain.GroupData;
import com.ruoyi.project.app.service.IGroupService;
import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import com.ruoyi.project.group.groupWork.service.IGroupWorkService;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.quality.afterService.domain.AfterService;
import com.ruoyi.project.quality.afterService.service.IAfterServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app 组装小组车间交互控制层
 *
 * @Author: Rainey
 * @Date: 2019/12/3 15:38
 * @Version: 1.0
 **/
@RequestMapping("/app")
@RestController
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IGroupWorkService groupWorkService;

    @Autowired
    private IAfterServiceService afterServiceService;

    /**
     * 工单扫码建档信息生产操作
     */
    @RequestMapping("/scanPnMain")
    public AjaxResult scanPnMain(@RequestBody GroupData groupData) {
        return groupService.scanPnMain(groupData);
    }

    /**
     * 工单分配到小组
     */
    @RequestMapping("/disWork")
    public AjaxResult disWork(@RequestBody GroupData groupData) {
        return groupService.disWork(groupData);
    }

    /**
     * 小组关联用户
     */
    @RequestMapping("/relUser")
    public AjaxResult relUser(@RequestBody GroupData groupData) {
        return groupService.relUser(groupData);
    }

    /**
     * 拉取小组工单列表
     */
    @RequestMapping("/group/work/list")
    public AjaxResult getGroupWorkList(@RequestBody DevWorkOrder workOrder) {
        return AjaxResult.api(0, "请求成功", groupWorkService.selectGroupWorkList(workOrder));
    }

    /**
     * 拉取小组列表信息
     */
    @RequestMapping("/group/list")
    public AjaxResult getGroupList(@RequestBody GroupInfo group){
        return groupService.getGroupList(group);
    }

    /**
     * APP端售后扫码录入操作
     */
    @RequestMapping("/back/scanInput")
    public AjaxResult scanInput(@RequestBody AfterService afterService){
        return afterServiceService.insertAfterService(afterService);
    }
}

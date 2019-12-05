package com.ruoyi.project.app.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.app.domain.GroupData;
import com.ruoyi.project.group.groupInfo.domain.GroupInfo;

/**
 * App 组装小组车间交互服务层
 * @Author: Rainey
 * @Date: 2019/12/3 15:42
 * @Version: 1.0
 **/
public interface IGroupService {

    /**
     * 工单扫码生产操作
     * @param groupData 扫码信息
     * @return 结果
     */
    AjaxResult scanPnMain(GroupData groupData);

    /**
     * 工单分配扫码操作
     * @param groupData 扫码信息
     * @return 结果
     */
    AjaxResult disWork(GroupData groupData);

    /**
     * 小组关联用户
     * @param groupData 扫码信息
     * @return 结果
     */
    AjaxResult relUser(GroupData groupData);

    /**
     * 拉取小组列表信息
     * @param group 小组信息
     * @return 结果
     */
    AjaxResult getGroupList(GroupInfo group);
}

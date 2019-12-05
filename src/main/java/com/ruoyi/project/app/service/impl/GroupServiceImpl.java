package com.ruoyi.project.app.service.impl;

import com.ruoyi.common.constant.GroupConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.app.domain.GroupData;
import com.ruoyi.project.app.service.IGroupService;
import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import com.ruoyi.project.group.groupInfo.mapper.GroupInfoMapper;
import com.ruoyi.project.group.groupInfo.mapper.GroupUserMapper;
import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkInfoMapper;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.domain.UserRole;
import com.ruoyi.project.system.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * App 组装小组车间交互实现
 *
 * @Author: Rainey
 * @Date: 2019/12/3 15:43
 * @Version: 1.0
 **/
@Service
public class GroupServiceImpl implements IGroupService {

    /**
     * 日志记录
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupWorkInfoMapper groupWorkInfoMapper;

    @Autowired
    private GroupWorkMapper groupWorkMapper;

    @Autowired
    private DevWorkOrderMapper workOrderMapper;

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 工单扫码生产操作
     *
     * @param groupData 扫码信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult scanPnMain(GroupData groupData) {
        try {
            if (groupData == null || groupData.getGroupId() <= 0 || StringUtils.isEmpty(groupData.getPnMain())) {
                return AjaxResult.api(1, "请求参数错误", null);
            }
            // 查询建档信息是否存在
            GroupWorkInfo workInfo = groupWorkInfoMapper.selectMesBatchByMainInfo(groupData.getPnMain());
            if (workInfo == null) {
                return AjaxResult.api(1, "建档信息不存在", null);
            }
            // 查询对应小组是否有分配该工单
            GroupWork groupWork = groupWorkMapper.selectGroupWorkInfoById(workInfo.getWorkId(), groupData.getGroupId());
            if (groupWork == null) {
                return AjaxResult.api(1, "该小组没有分配扫码的工单信息", null);
            }
            // 查询小组信息是否存在
            GroupInfo groupInfo = groupInfoMapper.selectGroupInfoById(groupData.getGroupId());
            if (groupInfo == null) {
                return AjaxResult.api(1, "扫码小组信息不存在或被删除", null);
            }
            // 查询小组是否配置了操作员
            List<GroupUser> groupUsers = groupUserMapper.selectGroupUserById(groupInfo.getId());
            if (StringUtils.isEmpty(groupUsers)) {
                return AjaxResult.api(1, "该小组没有作业员,请先配置作业员", null);
            }
            // 查询工单信息
            DevWorkOrder workOrder = workOrderMapper.selectWorkOrderInfoById(workInfo.getWorkId());
            if (workOrder == null) {
                return AjaxResult.api(1, "工单信息不存在或被删除", null);
            }
            if (WorkConstants.WORK_STATUS_END.equals(workOrder.getWorkorderStatus())) {
                return AjaxResult.api(1, "该建档所属工单已经完成", null);
            }
            if (GroupConstants.SACN_SIGN_YES.equals(workInfo.getStatus())) {
                return AjaxResult.api(1, "该建档信息已扫过", null);
            }
            // 更新对应小组对应工单生产数量
            groupWork.setActualNum(groupWork.getActualNum() + 1);
            if (groupWork.getWorkNum().equals(groupWork.getActualNum())) {
                // 实际生产数量等于分配数量判定任务为已经完成
                groupWorkMapper.updateGroupWorkFinishTag(groupWork.getId(), GroupConstants.WORK_TASK_FINISH_TAG_YES);
            }
            groupWorkMapper.updateGroupWork(groupWork);

            // 更新扫码信息
            workInfo.setGroupId(groupData.getGroupId());
            workInfo.setStatus(GroupConstants.SACN_SIGN_YES);
            groupWorkInfoMapper.updateGroupWorkInfo(workInfo);
            // 更新工单信息
            if (WorkConstants.WORK_STATUS_NOSTART.equals(workOrder.getWorkorderStatus())) {
                workOrder.setWorkorderStatus(WorkConstants.WORK_STATUS_STARTING);
                // 实际开始时间
                workOrder.setStartTime(new Date());
                // 修改工单的状态为进行中
                workOrder.setWorkorderStatus(WorkConstants.WORK_STATUS_STARTING);
                // 修改工单的操作状态为正在进行，页面显示暂停按钮
                workOrder.setOperationStatus(WorkConstants.OPERATION_STATUS_STARTING);
                //标记开始时间
                workOrder.setSignTime(new Date());
                //标记用时
                workOrder.setSignHuor(0F);
            }
            workOrder.setCumulativeNumber(workOrder.getCumulativeNumber() + 1);
            workOrderMapper.updateDevWorkOrder(workOrder);
            return AjaxResult.api(0, "扫码成功", null);
        } catch (Exception e) {
            LOGGER.error("工单扫码生产操作出现异常：" + e.getMessage());
            return AjaxResult.api(500, "系统异常", null);
        }
    }

    /**
     * 工单分配扫码操作
     *
     * @param groupData 扫码信息
     * @return 结果
     */
    @Override
    public AjaxResult disWork(GroupData groupData) {
        try {
            if (groupData == null || groupData.getGroupId() <= 0 || groupData.getWorkId() <= 0 || groupData.getWorkNum() <= 0) {
                return AjaxResult.api(1, "请求参数错误", null);
            }
            User user = JwtUtil.getUser();
            if (user == null) {
                return AjaxResult.api(1, UserConstants.NOT_LOGIN, null);
            }
            // 查询扫码的工单信息
            DevWorkOrder workOrder = workOrderMapper.selectWorkOrderInfoById(groupData.getWorkId());
            if (workOrder == null) {
                return AjaxResult.api(1, "工单不存在或被删除", null);
            }
            // 查询在线用户是否有分配工单权限
            UserRole userRole = groupWorkMapper.selectWorkAuth(user.getUserId(), workOrder.getLineId());
            if (userRole == null) {
                return AjaxResult.api(1, "没有该工单的分配权限", null);
            }
            // 查询工单分配记录
            GroupWork groupWork = groupWorkMapper.selectGroupWorkInfoById(groupData.getWorkId(), groupData.getGroupId());
            if (groupWork != null) {
                return AjaxResult.api(1, "该小组已分配该工单,勿重复操作", null);
            }

            // 进行工单分配
            groupWork = new GroupWork();
            groupWork.setCompanyId(user.getCompanyId());
            groupWork.setcTime(new Date());
            groupWork.setGroupId(groupData.getGroupId());
            groupWork.setWorkId(workOrder.getId());
            groupWork.setPnCode(workOrder.getProductCode());
            groupWork.setLineId(workOrder.getLineId());
            groupWork.setWorkCode(workOrder.getWorkorderNumber());
            groupWork.setWorkNum(groupData.getWorkNum());
            groupWorkMapper.insertGroupWork(groupWork);
            return AjaxResult.api(0, "工单分配成功", null);
        } catch (Exception e) {
            LOGGER.error("工单分配扫码操作出现异常：" + e.getMessage());
            return AjaxResult.api(500, "系统异常", null);
        }
    }

    /**
     * 小组关联用户
     *
     * @param groupData 扫码信息
     * @return 结果
     */
    @Override
    public AjaxResult relUser(GroupData groupData) {
        try {
            if (groupData == null || groupData.getGroupId() <= 0 || groupData.getUserId() <= 0) {
                return AjaxResult.api(1, "请求参数错误", null);
            }
            // 查询小组信息
            GroupInfo groupInfo = groupInfoMapper.selectGroupInfoById(groupData.getGroupId());
            if (groupInfo == null) {
                return AjaxResult.api(1, "小组不存在或被删除", null);
            }
            User user = userMapper.selectUserInfoById(groupData.getUserId());
            if (user == null || !user.getCompanyId().equals(groupInfo.getCompanyId())) {
                return AjaxResult.api(1, "未找到员工信息或非该公司员工", null);
            }
            // 查询该员工是否已经关联该小组
            GroupUser groupUser = groupUserMapper.selectGroupUserUniqueById(groupData.getUserId(), groupData.getGroupId());
            if (groupUser != null) {
                return AjaxResult.api(1, "该小组已关联该员工,勿重复配置", null);
            }
            groupUser = new GroupUser();
            groupUser.setUserId(groupData.getUserId());
            groupUser.setGroupId(groupData.getGroupId());
            groupUserMapper.insertGroupUser(groupUser);
            return AjaxResult.api(0, "请求成功", null);
        } catch (Exception e) {
            LOGGER.error("小组关联用户出现异常：" + e.getMessage());
            return AjaxResult.api(500, "系统异常", null);
        }
    }

    /**
     * 拉取小组列表信息
     *
     * @param group 小组信息
     * @return 结果
     */
    @Override
    public AjaxResult getGroupList(GroupInfo group) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return AjaxResult.api(0, "请求成功", Collections.emptyList());
        }
        if (group.getWorkId() > 0) {
            // 查询未分配工单的小组信息
            return AjaxResult.api(0, "请求成功", groupInfoMapper.selectGroupInfoNoWork(user.getCompanyId(), group.getWorkId()));
        } else {
            return AjaxResult.api(0, "请求成功", groupInfoMapper.selectGroupListByComId(user.getCompanyId()));
        }
    }
}

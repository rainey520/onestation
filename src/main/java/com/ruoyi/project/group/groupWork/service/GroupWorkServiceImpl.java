package com.ruoyi.project.group.groupWork.service;

import com.ruoyi.common.constant.GroupConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.CodeUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TimeUtil;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import com.ruoyi.project.group.groupInfo.mapper.GroupUserMapper;
import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.group.groupWork.domain.GroupWorkUser;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkInfoMapper;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkMapper;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkUserMapper;
import com.ruoyi.project.product.importConfig.domain.ImportConfig;
import com.ruoyi.project.product.importConfig.mapper.ImportConfigMapper;
import com.ruoyi.project.product.list.domain.DevProductList;
import com.ruoyi.project.product.list.mapper.DevProductListMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.role.mapper.RoleMapper;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.domain.UserRole;
import com.ruoyi.project.system.user.mapper.UserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 小组工单关联管理 服务层实现
 *
 * @author sj
 * @date 2019-11-30
 */
@Service("groupWork")
public class GroupWorkServiceImpl implements IGroupWorkService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupWorkServiceImpl.class);

    @Autowired
    private GroupWorkMapper groupWorkMapper;

    @Autowired
    private DevWorkOrderMapper workOrderMapper;

    @Autowired
    private DevProductListMapper productMapper;

    @Autowired
    private ImportConfigMapper configMapper;

    @Autowired
    private GroupWorkInfoMapper workInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Autowired
    private GroupWorkUserMapper groupWorkUserMapper;


    /**
     * 查询小组工单关联管理信息
     *
     * @param id 小组工单关联管理ID
     * @return 小组工单关联管理信息
     */
    @Override
    public GroupWork selectGroupWorkById(Integer id) {
        return groupWorkMapper.selectGroupWorkById(id);
    }

    /**
     * 查询小组工单关联管理列表
     *
     * @param workOrder 小组工单关联管理信息
     * @return 小组工单关联管理集合
     */
    @Override
    public List<DevWorkOrder> selectGroupWorkList(DevWorkOrder workOrder) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        workOrder.setCompanyId(user.getCompanyId());
        workOrder.setWlSign(WorkConstants.SING_GROUP);
        List<DevWorkOrder> workOrders = workOrderMapper.selectDevWorkOrderList(workOrder);
        Role role = null;
        Integer disNum = null;
        UserRole userRole = null;
        for (DevWorkOrder order : workOrders) {
            role = roleMapper.selectRoleInfoById(order.getLineId());
            if (role != null) {
                order.setParamConfig(role.getRoleName());
            }
            // 查询已分配数量
            disNum = groupWorkMapper.selectWorkDisNum(order.getId());
            order.setBadNumber(disNum == null ? 0 : disNum);

            // 查询操作该工单的权限
            userRole = userRoleMapper.selectUserRoleInfo(user.getUserId().intValue(), order.getLineId());
            if (userRole != null) {
                order.setWork1(WorkConstants.GROUP_WORK_OP_YES);
            } else {
                order.setWork1(WorkConstants.GROUP_WORK_OP_NO);
            }
        }
        return workOrders;
    }

    /**
     * 查询对应工单分配的小组信息
     *
     * @param groupWork 小组信息
     * @return 结果
     */
    @Override
    public List<GroupWork> selectDisGroupWorkList(GroupWork groupWork) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        groupWork.setCompanyId(user.getCompanyId());
        List<GroupWork> groupWorks = groupWorkMapper.selectGroupWorkList(groupWork);
        UserRole userRole = null;
        for (GroupWork work : groupWorks) {
            userRole = userRoleMapper.selectUserRoleInfo(user.getUserId().intValue(), work.getLineId());
            if (userRole != null) {
                work.setOpSign(WorkConstants.GROUP_WORK_OP_YES);
            } else {
                work.setOpSign(WorkConstants.GROUP_WORK_OP_NO);
            }
            // 已经结束的工单
            if (WorkConstants.WORK_STATUS_END.equals(work.getWorkStatus())) {
                work.setUserNames(groupWorkUserMapper.selectGroupFinishWorkUsers(work.getId()));
            } else {
                work.setUserNames(groupUserMapper.selectGroupUserByGid(work.getGroupId()));
            }
        }
        return groupWorks;
    }

    /**
     * 新增小组工单关联管理
     *
     * @param workOrder 小组工单关联管理信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertGroupWork(DevWorkOrder workOrder) {
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                return 0;
            }
            workOrder.setCompanyId(user.getCompanyId());
            DevProductList product = productMapper.selectDevProductByCode(user.getCompanyId(), workOrder.getProductCode());
            if (product == null) {
                throw new BusinessException("工单生产的产品不存在或被删除");
            }
            workOrder.setMakeType(product.getSign());
            workOrder.setProductName(product.getProductName());
            workOrder.setProductCode(product.getProductCode());
            workOrder.setWorkPrice(product.getLabourPrice());
            workOrder.setProductModel(product.getProductModel());
            workOrder.setCreateBy(user.getUserName());
            workOrderMapper.insertDevWorkOrder(workOrder);

            /**
             * 生成建档信息
             */
            // 查询配置的规则信息
            ImportConfig config = configMapper.selectImportConfigByType(user.getCompanyId(), 4);
            if (config == null) {
                throw new BusinessException("请先配置建档规则");
            }
            // 生成建档信息
            String mainInfo = "";
            GroupWorkInfo workInfo = null;
            if (workOrder.getProductNumber() != null && workOrder.getProductNumber() > 0) {
                int totalNum = workOrder.getProductNumber();
                for (int i = 0; i < totalNum; i++) {
                    mainInfo = config.getConRule() + CodeUtils.getRandomStr(config.getCon1());
                    // 查询建档信息是否存在
                    workInfo = workInfoMapper.selectMesBatchByMainInfo(mainInfo);
                    int index = 0;
                    while (workInfo != null && index < 10) {
                        mainInfo = config.getConRule() + CodeUtils.getRandomStr(config.getCon1());
                        workInfo = workInfoMapper.selectMesBatchByMainInfo(mainInfo);
                        index++;
                    }
                    if (workInfo != null) {
                        throw new BusinessException("下单失败");
                    }
                    workInfo = new GroupWorkInfo();
                    workInfo.setPnMain(mainInfo);
                    workInfo.setWorkId(workOrder.getId());
                    workInfoMapper.insertGroupWorkInfo(workInfo);
                }
            }
            return 1;
        } catch (BusinessException e) {
            LOGGER.error("新增小组工单失败：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 修改小组工单关联管理
     *
     * @param groupWork 小组工单关联管理信息
     * @return 结果
     */
    @Override
    public int updateGroupWork(GroupWork groupWork) {
        return groupWorkMapper.updateGroupWork(groupWork);
    }

    /**
     * 删除小组工单信息
     *
     * @param id 工单id
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGroupWorkById(Integer id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException("用户未登录或登录超时");
        }
        // 查询工单信息
        DevWorkOrder workOrder = workOrderMapper.selectDevWorkOrderById(id);
        getOpSign(user, workOrder);
        if (!WorkConstants.WORK_STATUS_NOSTART.equals(workOrder.getWorkorderStatus())) {
            throw new BusinessException("未开始的工单才能删除");
        }
        // 删除工单信息
        workOrderMapper.deleteDevWorkOrderById(workOrder.getId());
        // 删除关单小组关联信息
        groupWorkMapper.deleteGroupWorkByWorkId(workOrder.getId());
        // 删除建档信息
        workInfoMapper.deleteGroupWorkInfoByWorkId(workOrder.getId(), null);
        return 1;
    }

    /**
     * 删除下发到小组的某个工单信息
     *
     * @param id 下发id
     * @return 结果
     */
    @Override
    public int deleteGroupWorkInfoById(Integer id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        // 查询工单下发小组信息
        GroupWork groupWork = groupWorkMapper.selectGroupWorkById(id);
        if (groupWork == null) {
            throw new BusinessException("未找到对应小组工单信息");
        }
        // 查询工单信息
        DevWorkOrder workOrder = workOrderMapper.selectDevWorkOrderById(groupWork.getWorkId());
        if (WorkConstants.WORK_STATUS_END.equals(workOrder.getWorkorderStatus())) {
            throw new BusinessException("该工单已完成,不能删除");
        }
        getOpSign(user, workOrder);
        // 查询对应小组对应扫码信息是否存在
        List<GroupWorkInfo> infoList = workInfoMapper.selectGroupWorkSignById(groupWork.getWorkId(), groupWork.getGroupId());
        if (StringUtils.isNotEmpty(infoList)) {
            throw new BusinessException("该小组工单已经开始生产,删除失败");
        }
        return groupWorkMapper.deleteGroupWorkById(id);
    }


    /**
     * 查询工单已分配的数量
     *
     * @param workId 工单id
     * @return 结果
     */
    @Override
    public int selectWorkDisNum(int workId) {
        Integer disNum = groupWorkMapper.selectWorkDisNum(workId);
        return disNum == null ? 0 : disNum;
    }

    /**
     * 保存工单分配操作
     *
     * @param groupWork 工单分配信息
     * @return 结果
     */
    @Override
    public AjaxResult saveDistributeWork(GroupWork groupWork) {
        if (groupWork == null || groupWork.getWorkId() == null || groupWork.getGroupId() == null || groupWork.getWorkNum() <= 0) {
            return AjaxResult.api(1, "传递参数错误", null);
        }
        User user = JwtUtil.getUser();
        if (user == null) {
            return AjaxResult.api(1, "用户未登陆", null);
        }
        // 查询工单信息
        DevWorkOrder workOrder = workOrderMapper.selectWorkOrderInfoById(groupWork.getWorkId());
        if (workOrder == null) {
            return AjaxResult.api(1, "工单不存在或被删除", null);
        }
        if (WorkConstants.WORK_STATUS_END.equals(workOrder.getWorkorderStatus())) {
            return AjaxResult.api(1, "该工单已完成,分配失败", null);
        }
        // 查询角色信息
        UserRole userRole = groupWorkMapper.selectWorkAuth(user.getUserId(), workOrder.getLineId());
        if (userRole == null) {
            return AjaxResult.api(1, "没有操作该工单的权限", null);
        }
        groupWork.setCompanyId(user.getCompanyId());
        groupWork.setcTime(new Date());
        groupWork.setWorkCode(workOrder.getWorkorderNumber());
        groupWork.setLineId(workOrder.getLineId());
        groupWork.setPnCode(workOrder.getProductCode());
        groupWorkMapper.insertGroupWork(groupWork);
        return AjaxResult.api(0, "分配成功", null);
    }


    /**
     * 结束小组工单
     *
     * @param workId 工单id
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult finishWork(Integer workId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return AjaxResult.api(1, UserConstants.NOT_LOGIN, null);
        }
        DevWorkOrder workOrder = workOrderMapper.selectWorkOrderInfoById(workId);
        if (workOrder == null) {
            return AjaxResult.api(1, "工单不存在或被删除", null);
        }
        // 查询角色信息
        UserRole userRole = groupWorkMapper.selectWorkAuth(user.getUserId(), workOrder.getLineId());
        if (userRole == null) {
            return AjaxResult.api(1, "没有操作该工单的权限", null);
        }

        // 更新工单相关信息
        workOrder.setWorkorderStatus(WorkConstants.WORK_STATUS_END);
        workOrder.setWorkSign(WorkConstants.WORK_SIGN_YES);
        workOrder.setOperationStatus(WorkConstants.OPERATION_STATUS_FINISH);
        workOrder.setEndTime(new Date());
        workOrder.setSignHuor(workOrder.getSignHuor() + TimeUtil.getDateDel(workOrder.getSignTime(), new Date()));
        workOrder.setUpdateBy(user.getUserName());
        workOrder.setUpdateTime(new Date());

        // 新增工单小组目前员工关联表
        List<GroupWork> groupWorks = groupWorkMapper.selectGroupWorkByWorkId(workId);
        List<GroupUser> groupUsers = new ArrayList<>();
        GroupWorkUser groupWorkUser = null;
        for (GroupWork groupWork : groupWorks) {
            // 主工单结束，对应所有的小组工单任务默认结束
            groupWork.setFinishTag(GroupConstants.WORK_TASK_FINISH_TAG_YES);
            groupWork.setFinishTime(new Date());
            groupUsers = groupUserMapper.selectGroupUserInfoByGid(groupWork.getGroupId());
            for (GroupUser groupUser : groupUsers) {
                groupWorkUser = new GroupWorkUser();
                groupWorkUser.setGwId(groupWork.getId());
                groupWorkUser.setUserId(groupUser.getUserId());
                groupWorkUserMapper.insertGroupWorkUser(groupWorkUser);
            }
            groupWorkMapper.updateGroupWork(groupWork);
        }
        // 查询该工单一共完成的产品
        List<GroupWorkInfo> infoList = workInfoMapper.selectGroupWorkInfoByWorkId(workId, GroupConstants.SACN_SIGN_YES);
        workOrder.setCumulativeNumber(infoList.size() > 0 ? infoList.size() : 0);
        workOrderMapper.updateDevWorkOrder(workOrder);
        return AjaxResult.api(0, "请求成功", infoList.size());
    }


    /**
     * 小组结束对应工单任务
     * @param id 工单小组关联id
     * @return 结果
     */
    @Override
    public AjaxResult finishTask(Integer id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return AjaxResult.api(1, UserConstants.NOT_LOGIN, null);
        }
        groupWorkMapper.updateGroupWorkFinishTag(id, GroupConstants.SACN_SIGN_YES);
        return AjaxResult.api(0, "请求成功", null);
    }

    /**
     * 获取小组工单操作权限
     *
     * @param user      用户信息
     * @param workOrder 工单信息
     */
    private void getOpSign(User user, DevWorkOrder workOrder) {
        if (workOrder == null) {
            throw new BusinessException("工单不存在或被删除");
        }
        // 查询角色信息
        UserRole userRole = groupWorkMapper.selectWorkAuth(user.getUserId(), workOrder.getLineId());
        if (userRole == null) {
            throw new BusinessException("没有操作该工单的权限");
        }
    }
}

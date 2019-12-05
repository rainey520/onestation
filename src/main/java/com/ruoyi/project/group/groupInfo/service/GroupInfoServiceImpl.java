package com.ruoyi.project.group.groupInfo.service;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.support.Convert;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import com.ruoyi.project.group.groupInfo.mapper.GroupInfoMapper;
import com.ruoyi.project.group.groupInfo.mapper.GroupUserMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 小组 服务层实现
 *
 * @author sj
 * @date 2019-11-30
 */
@Service("groupInfo")
public class GroupInfoServiceImpl implements IGroupInfoService {

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    /**
     * 查询小组信息
     *
     * @param id 小组ID
     * @return 小组信息
     */
    @Override
    public GroupInfo selectGroupInfoById(Integer id) {
        return groupInfoMapper.selectGroupInfoById(id);
    }

    /**
     * 查询小组列表
     *
     * @param groupInfo 小组信息
     * @return 小组集合
     */
    @Override
    public List<GroupInfo> selectGroupInfoList(GroupInfo groupInfo) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        groupInfo.setCompanyId(user.getCompanyId());
        List<GroupInfo> groupInfos = groupInfoMapper.selectGroupInfoList(groupInfo);
        for (GroupInfo info : groupInfos) {
            info.setUserNames(groupUserMapper.selectGroupUserByGid(info.getId()));
        }
        return groupInfos;
    }

    /**
     * 新增小组
     *
     * @param groupInfo 小组信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertGroupInfo(GroupInfo groupInfo) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        groupInfo.setCompanyId(user.getCompanyId());
        groupInfo.setcTime(new Date());
        groupInfoMapper.insertGroupInfo(groupInfo);
        // 添加小组用户关联
        saveGroupUser(groupInfo);
        return 1;
    }

    /**
     * 修改小组
     *
     * @param groupInfo 小组信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateGroupInfo(GroupInfo groupInfo) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        // 删除关联
        groupUserMapper.deleteGroupUserByGroupId(groupInfo.getId());
        // 添加小组用户关联
        saveGroupUser(groupInfo);
        return groupInfoMapper.updateGroupInfo(groupInfo);
    }

    /**
     * 添加用户小组关联
     * @param groupInfo 小组信息
     */
    private void saveGroupUser(GroupInfo groupInfo) {
        GroupUser groupUser = null;
        for (Integer userId : groupInfo.getUserIds()) {
            groupUser = new GroupUser();
            groupUser.setGroupId(groupInfo.getId());
            groupUser.setUserId(userId);
            groupUserMapper.insertGroupUser(groupUser);
        }
    }

    /**
     * 删除小组对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroupInfoByIds(String ids) {
        return groupInfoMapper.deleteGroupInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 校验小组用户名称唯一性
     *
     * @param groupInfo 小组信息
     * @return 结果
     */
    @Override
    public String checkGroupNameUnique(GroupInfo groupInfo) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        GroupInfo unique = groupInfoMapper.selectGroupInfoByName(user.getCompanyId(), groupInfo.getGroupName());
        if (unique != null && !unique.getId().equals(groupInfo.getId())) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 删除小组信息
     * @param id 小组id
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGroupInfoById(Integer id) {
        groupInfoMapper.deleteGroupInfoById(id);
        groupUserMapper.deleteGroupUserByGroupId(id);
        return 1;
    }

    /**
     * 查询小组配置的员工信息
     * @param groupId 小组id
     * @return 结果
     */
    @Override
    public List<GroupUser> selectGroupUserConfig(Integer groupId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return groupUserMapper.selectGroupUserConfig(user.getCompanyId(),groupId);
    }

    /**
     * 查询小组未配置的员工信息
     * @param groupId 小组id
     * @return 结果
     */
    @Override
    public List<GroupUser> selectGroupUserOther(Integer groupId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return groupUserMapper.selectGroupUserOther(user.getCompanyId(),groupId);
    }

    /**
     * 查询未分配对应工单的小组信息
     * @param workId 工单id
     * @return 结果
     */
    @Override
    public List<GroupInfo> selectGroupInfoNoWork(Integer workId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return groupInfoMapper.selectGroupInfoNoWork(user.getCompanyId(),workId);
    }


    /**
     * 查询公司小组列表
     * @return 结果
     */
    @Override
    public List<GroupInfo> selectGroupListByComId() {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return groupInfoMapper.selectGroupListByComId(user.getCompanyId());
    }
}

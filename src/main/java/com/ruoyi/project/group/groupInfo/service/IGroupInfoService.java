package com.ruoyi.project.group.groupInfo.service;

import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import com.ruoyi.project.group.groupInfo.domain.GroupUser;

import java.util.List;

/**
 * 小组 服务层
 *
 * @author sj
 * @date 2019-11-30
 */
public interface IGroupInfoService {
    /**
     * 查询小组信息
     *
     * @param id 小组ID
     * @return 小组信息
     */
    public GroupInfo selectGroupInfoById(Integer id);

    /**
     * 查询小组列表
     *
     * @param groupInfo 小组信息
     * @return 小组集合
     */
    public List<GroupInfo> selectGroupInfoList(GroupInfo groupInfo);

    /**
     * 新增小组
     *
     * @param groupInfo 小组信息
     * @return 结果
     */
    public int insertGroupInfo(GroupInfo groupInfo);

    /**
     * 修改小组
     *
     * @param groupInfo 小组信息
     * @return 结果
     */
    public int updateGroupInfo(GroupInfo groupInfo);

    /**
     * 删除小组信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupInfoByIds(String ids);

    /**
     * 校验小组用户名称唯一性
     *
     * @param groupInfo 小组信息
     * @return 结果
     */
    String checkGroupNameUnique(GroupInfo groupInfo);

    /**
     * 删除小组信息
     *
     * @param id 小组id
     * @return 结果
     */
    int deleteGroupInfoById(Integer id);

    /**
     * 查询小组配置的员工信息
     *
     * @param groupId 小组id
     * @return 结果
     */
    List<GroupUser> selectGroupUserConfig(Integer groupId);

    /**
     * 查询小组未配置的员工信息
     *
     * @param groupId 小组id
     * @return 结果
     */
    List<GroupUser> selectGroupUserOther(Integer groupId);

    /**
     * 查询未分配对应工单的小组信息
     *
     * @param workId 工单id
     * @return 结果
     */
    public List<GroupInfo> selectGroupInfoNoWork(Integer workId);

    /**
     * 查询公司小组列表
     * @return 结果
     */
    List<GroupInfo> selectGroupListByComId();
}

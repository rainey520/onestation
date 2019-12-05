package com.ruoyi.project.group.groupInfo.mapper;

import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组员工关联 数据层
 *
 * @author sj
 * @date 2019-11-30
 */
public interface GroupUserMapper {
    /**
     * 查询小组员工关联信息
     *
     * @param groupId 小组员工关联ID
     * @return 小组员工关联信息
     */
    public List<GroupUser> selectGroupUserById(Integer groupId);

    /**
     * 查询小组员工关联列表
     *
     * @param groupUser 小组员工关联信息
     * @return 小组员工关联集合
     */
    public List<GroupUser> selectGroupUserList(GroupUser groupUser);

    /**
     * 新增小组员工关联
     *
     * @param groupUser 小组员工关联信息
     * @return 结果
     */
    public int insertGroupUser(GroupUser groupUser);

    /**
     * 修改小组员工关联
     *
     * @param groupUser 小组员工关联信息
     * @return 结果
     */
    public int updateGroupUser(GroupUser groupUser);

    /**
     * 删除小组员工关联
     *
     * @param groupId 小组id
     * @return 结果
     */
    public int deleteGroupUserByGroupId(Integer groupId);

    /**
     * 批量删除小组员工关联
     *
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupUserByIds(String[] groupIds);

    /**
     * 查询对应组所有的员工信息
     *
     * @param groupId 组id
     * @return 结果
     */
    List<String> selectGroupUserByGid(@Param("groupId") Integer groupId);

    /**
     * 查询小组配置的员工信息
     *
     * @param companyId 公司id
     * @param groupId   小组id
     * @return 结果
     */
    List<GroupUser> selectGroupUserConfig(@Param("companyId") Integer companyId, @Param("groupId") Integer groupId);

    /**
     * 查询小组未配置的员工信息
     *
     * @param companyId 公司id
     * @param groupId   小组id
     * @return 结果
     */
    List<GroupUser> selectGroupUserOther(@Param("companyId") Integer companyId, @Param("groupId") Integer groupId);

    /**
     * 查询小组配置信息
     *
     * @param groupId 小组id
     * @return 结果
     */
    List<GroupUser> selectGroupUserInfoByGid(@Param("groupId") Integer groupId);

    /**
     * 查询小组员工关联信息
     * @param userId 用户id
     * @param groupId 小组id
     * @return 结果
     */
    GroupUser selectGroupUserUniqueById(@Param("userId") int userId, @Param("groupId") int groupId);
}
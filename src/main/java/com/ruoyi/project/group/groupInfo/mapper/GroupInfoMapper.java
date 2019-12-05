package com.ruoyi.project.group.groupInfo.mapper;

import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组 数据层
 *
 * @author sj
 * @date 2019-11-30
 */
public interface GroupInfoMapper {
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
     * 删除小组
     *
     * @param id 小组ID
     * @return 结果
     */
    public int deleteGroupInfoById(Integer id);

    /**
     * 批量删除小组
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupInfoByIds(String[] ids);

    /**
     * 通过小组名称查询小组信息
     *
     * @param companyId 公司id
     * @param groupName 小组名称
     * @return 结果
     */
    GroupInfo selectGroupInfoByName(@Param("companyId") Integer companyId, @Param("groupName") String groupName);

    /**
     * 查询未分配对应工单的小组信息
     *
     * @param companyId 公司id
     * @param workId    工单id
     * @return 结果
     */
    List<GroupInfo> selectGroupInfoNoWork(@Param("companyId") int companyId, @Param("workId") int workId);

    /**
     * 查询公司小组列表
     *
     * @param companyId 公司id
     * @return 结果
     */
    List<GroupInfo> selectGroupListByComId(@Param("companyId") Integer companyId);
}
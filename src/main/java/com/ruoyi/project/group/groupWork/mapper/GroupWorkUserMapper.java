package com.ruoyi.project.group.groupWork.mapper;

import com.ruoyi.project.group.groupWork.domain.GroupWorkUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单分配小组人员历史 数据层
 * 
 * @author sj
 * @date 2019-11-30
 */
public interface GroupWorkUserMapper 
{
	/**
     * 查询工单分配小组人员历史信息
     * 
     * @param gwId 工单分配小组人员历史ID
     * @return 工单分配小组人员历史信息
     */
	public GroupWorkUser selectGroupWorkUserById(Integer gwId);
	
	/**
     * 查询工单分配小组人员历史列表
     * 
     * @param groupWorkUser 工单分配小组人员历史信息
     * @return 工单分配小组人员历史集合
     */
	public List<GroupWorkUser> selectGroupWorkUserList(GroupWorkUser groupWorkUser);
	
	/**
     * 新增工单分配小组人员历史
     * 
     * @param groupWorkUser 工单分配小组人员历史信息
     * @return 结果
     */
	public int insertGroupWorkUser(GroupWorkUser groupWorkUser);
	
	/**
     * 修改工单分配小组人员历史
     * 
     * @param groupWorkUser 工单分配小组人员历史信息
     * @return 结果
     */
	public int updateGroupWorkUser(GroupWorkUser groupWorkUser);
	
	/**
     * 删除工单分配小组人员历史
     * 
     * @param gwId 工单分配小组人员历史ID
     * @return 结果
     */
	public int deleteGroupWorkUserById(Integer gwId);
	
	/**
     * 批量删除工单分配小组人员历史
     * 
     * @param gwIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupWorkUserByIds(String[] gwIds);

	/**
	 * 查询结束工单的员工信息
	 * @param groupWorkId 工单小组关联表主键id
	 * @return 结果
	 */
    List<String> selectGroupFinishWorkUsers(@Param("groupWorkId") Integer groupWorkId);
}
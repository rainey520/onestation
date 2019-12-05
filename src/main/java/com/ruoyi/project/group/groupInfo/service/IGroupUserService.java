package com.ruoyi.project.group.groupInfo.service;

import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import java.util.List;

/**
 * 小组员工关联 服务层
 * 
 * @author sj
 * @date 2019-11-30
 */
public interface IGroupUserService 
{

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
     * 删除小组员工关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupUserByIds(String ids);
	
}

package com.ruoyi.project.group.groupWork.service;

import com.ruoyi.common.support.Convert;
import com.ruoyi.project.group.groupWork.domain.GroupWorkUser;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工单分配小组人员历史 服务层实现
 * 
 * @author sj
 * @date 2019-11-30
 */
@Service
public class GroupWorkUserServiceImpl implements IGroupWorkUserService
{
	@Autowired
	private GroupWorkUserMapper groupWorkUserMapper;

	/**
     * 查询工单分配小组人员历史信息
     * 
     * @param gwId 工单分配小组人员历史ID
     * @return 工单分配小组人员历史信息
     */
    @Override
	public GroupWorkUser selectGroupWorkUserById(Integer gwId)
	{
	    return groupWorkUserMapper.selectGroupWorkUserById(gwId);
	}
	
	/**
     * 查询工单分配小组人员历史列表
     * 
     * @param groupWorkUser 工单分配小组人员历史信息
     * @return 工单分配小组人员历史集合
     */
	@Override
	public List<GroupWorkUser> selectGroupWorkUserList(GroupWorkUser groupWorkUser)
	{
	    return groupWorkUserMapper.selectGroupWorkUserList(groupWorkUser);
	}
	
    /**
     * 新增工单分配小组人员历史
     * 
     * @param groupWorkUser 工单分配小组人员历史信息
     * @return 结果
     */
	@Override
	public int insertGroupWorkUser(GroupWorkUser groupWorkUser)
	{
	    return groupWorkUserMapper.insertGroupWorkUser(groupWorkUser);
	}
	
	/**
     * 修改工单分配小组人员历史
     * 
     * @param groupWorkUser 工单分配小组人员历史信息
     * @return 结果
     */
	@Override
	public int updateGroupWorkUser(GroupWorkUser groupWorkUser)
	{
	    return groupWorkUserMapper.updateGroupWorkUser(groupWorkUser);
	}

	/**
     * 删除工单分配小组人员历史对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGroupWorkUserByIds(String ids)
	{
		return groupWorkUserMapper.deleteGroupWorkUserByIds(Convert.toStrArray(ids));
	}
	
}

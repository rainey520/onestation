package com.ruoyi.project.group.groupWork.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import java.util.List;

/**
 * 工单产品建档 服务层
 * 
 * @author sj
 * @date 2019-11-30
 */
public interface IGroupWorkInfoService 
{
	/**
     * 查询工单产品建档信息
     * 
     * @param id 工单产品建档ID
     * @return 工单产品建档信息
     */
	public GroupWorkInfo selectGroupWorkInfoById(Integer id);
	
	/**
     * 查询工单产品建档列表
     * 
     * @param groupWorkInfo 工单产品建档信息
     * @return 工单产品建档集合
     */
	public List<GroupWorkInfo> selectGroupWorkInfoList(GroupWorkInfo groupWorkInfo);
	
	/**
     * 新增工单产品建档
     * 
     * @param groupWorkInfo 工单产品建档信息
     * @return 结果
     */
	public int insertGroupWorkInfo(GroupWorkInfo groupWorkInfo);
	
	/**
     * 修改工单产品建档
     * 
     * @param groupWorkInfo 工单产品建档信息
     * @return 结果
     */
	public int updateGroupWorkInfo(GroupWorkInfo groupWorkInfo);
		
	/**
     * 删除工单产品建档信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGroupWorkInfoByIds(String ids);

	/**
	 * 通过工单id删除未扫码的建档信息
	 * @param workId 工单id
	 * @param status 扫码状态
	 * @return 结果
	 */
    AjaxResult removeWorkInfoNotScan(Integer workId, String status);

	/**
	 * 通过工单id查询建档信息
	 * @param workId 工单id
	 * @return 结果
	 */
	List<GroupWorkInfo> selectGroupWorkInfoByWorkId(Integer workId);
}

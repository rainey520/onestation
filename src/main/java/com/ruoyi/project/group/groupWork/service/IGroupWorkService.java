package com.ruoyi.project.group.groupWork.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;

import java.util.List;

/**
 * 小组工单关联管理 服务层
 * 
 * @author sj
 * @date 2019-11-30
 */
public interface IGroupWorkService 
{
	/**
     * 查询小组工单关联管理信息
     * 
     * @param id 小组工单关联管理ID
     * @return 小组工单关联管理信息
     */
	public GroupWork selectGroupWorkById(Integer id);
	
	/**
     * 查询小组工单关联管理列表
     * 
     * @param workOrder 小组工单信息
     * @return 小组工单关联管理集合
     */
	public List<DevWorkOrder> selectGroupWorkList(DevWorkOrder workOrder);
	
	/**
     * 新增小组工单关联管理
     * 
     * @param groupWork 小组工单关联管理信息
     * @return 结果
     */
	public int insertGroupWork(DevWorkOrder groupWork);
	
	/**
     * 修改小组工单关联管理
     * 
     * @param groupWork 小组工单关联管理信息
     * @return 结果
     */
	public int updateGroupWork(GroupWork groupWork);
		

	/**
	 * 删除小组工单信息
	 * @param id 工单id
	 * @return 结果
	 */
	int deleteGroupWorkById(Integer id);

	/**
	 * 查询工单已分配的数量
	 * @param workId 工单id
	 * @return 结果
	 */
	int selectWorkDisNum(int workId);

	/**
	 * 保存工单分配操作
	 * @param groupWork 工单分配信息
	 * @return 结果
	 */
	AjaxResult saveDistributeWork(GroupWork groupWork);

	/**
	 * 查询对应工单分配的小组信息
	 * @param groupWork 小组信息
	 * @return 结果
	 */
	List<GroupWork> selectDisGroupWorkList(GroupWork groupWork);

	/**
	 * 删除下发到小组的某个工单信息
	 * @param id 下发id
	 * @return 结果
	 */
	int deleteGroupWorkInfoById(Integer id);

	/**
	 * 工单负责人结束工单信息
	 * @param workId 工单id
	 * @return 结果
	 */
	AjaxResult finishWork(Integer workId);

	/**
	 * 小组结束对应工单任务
	 * @param id 工单小组关联id
	 * @return 结果
	 */
	AjaxResult finishTask(Integer id);
}

package com.ruoyi.project.group.groupWork.mapper;

import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.report.domain.ComCost;
import com.ruoyi.project.system.user.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组工单关联管理 数据层
 *
 * @author sj
 * @date 2019-11-30
 */
public interface GroupWorkMapper {
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
     * @param groupWork 小组工单关联管理信息
     * @return 小组工单关联管理集合
     */
    public List<GroupWork> selectGroupWorkList(GroupWork groupWork);

    /**
     * 新增小组工单关联管理
     *
     * @param groupWork 小组工单关联管理信息
     * @return 结果
     */
    public int insertGroupWork(GroupWork groupWork);

    /**
     * 修改小组工单关联管理
     *
     * @param groupWork 小组工单关联管理信息
     * @return 结果
     */
    public int updateGroupWork(GroupWork groupWork);

    /**
     * 删除小组工单关联管理
     *
     * @param id 小组工单关联管理ID
     * @return 结果
     */
    public int deleteGroupWorkById(Integer id);

    /**
     * 批量删除小组工单关联管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupWorkByIds(String[] ids);

    /**
     * 查询用户工单权限信息
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 结果
     */
    UserRole selectWorkAuth(@Param("userId") Long userId, @Param("roleId") Integer roleId);

    /**
     * 通过工单id查询全部分配的数量
     *
     * @param workId 工单id
     * @return 结果
     */
    Integer selectWorkDisNum(@Param("workId") int workId);

    /**
     * 查询对应小组是否有分配该工单
     *
     * @param workId  工单id
     * @param groupId 小组id
     * @return 结果
     */
    GroupWork selectGroupWorkInfoById(@Param("workId") Integer workId, @Param("groupId") Integer groupId);

    /**
     * 删除关单小组关联信息
     *
     * @param workId 工单id
     * @return 结果
     */
    int deleteGroupWorkByWorkId(@Param("workId") Integer workId);

    /**
     * 查询工单下发到小组的列表
     * @param workId 工单id
     * @return 结果
     */
    List<GroupWork> selectGroupWorkByWorkId(@Param("workId") Integer workId);

    /**
     * 通过扫码信息查询生产该产品的小组工单信息
     * @param pnMain 扫码信息
     * @return 结果
     */
    GroupWork selectGroupWorkOne(@Param("pnMain") String pnMain);

    /**
     * 小组结束对应工单任务
     * @param id 工单小组关联id
     * @param finishTag 完成标记
     * @return 结果
     */
    int updateGroupWorkFinishTag(@Param("id") Integer id, @Param("finishTag") String finishTag);

    /**
     * 查询小组工单报表详情
     * @param report 检索条件
     * @return 结果
     */
    List<GroupWork> selectGroupWorkReport(ComCost report);

    /**
     * 查询组装车间工单信息
     * @param report 检索条件
     * @return 结果
     */
    List<DevWorkOrder> selectAllGroupWorkReport(ComCost report);

    /**
     * 查询对应工单的三个小组生产情况
     * @param workId 工单id
     * @return 结果
     */
    List<GroupWork> selectThreeGroupWorkInfoByWorkId(@Param("workId") Integer workId);

    /**
     * 报表查询对应检索条件的总数量
     * @param report 检索条件
     * @return 结果
     */
    ComCost selectWorkNumSum(ComCost report);

    /**
     * 报表查询对应小组的工单总数
     * @param report 检索条件
     * @return 结果
     */
    ComCost selectGroupWorkNumSum(ComCost report);
}
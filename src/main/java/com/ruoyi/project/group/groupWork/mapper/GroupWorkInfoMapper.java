package com.ruoyi.project.group.groupWork.mapper;

import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单产品建档 数据层
 *
 * @author sj
 * @date 2019-11-30
 */
public interface GroupWorkInfoMapper {
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
     * 删除工单产品建档
     *
     * @param id 工单产品建档ID
     * @return 结果
     */
    public int deleteGroupWorkInfoById(Integer id);

    /**
     * 批量删除工单产品建档
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupWorkInfoByIds(String[] ids);

    /**
     * 查询建档信息是否存在
     *
     * @param mainInfo 建档信息
     * @return 结果
     */
    GroupWorkInfo selectMesBatchByMainInfo(@Param("mainInfo") String mainInfo);

    /**
     * 通过工单id删除建档信息
     *
     * @param workId 工单id
     * @param status 扫码状态
     * @return 结果
     */
    int deleteGroupWorkInfoByWorkId(@Param("workId") Integer workId, @Param("status") String status);

    /**
     * 查询已通过扫码的建档信息
     *
     * @param workId  工单id
     * @param groupId 小组id
     * @return 结果
     */
    List<GroupWorkInfo> selectGroupWorkSignById(@Param("workId") Integer workId, @Param("groupId") Integer groupId);

    /**
     * 通过工单id和扫码状态查询建档记录信息
     *
     * @param workId 工单id
     * @param status 扫码状态
     * @return 结果
     */
    List<GroupWorkInfo> selectGroupWorkInfoByWorkId(@Param("workId") Integer workId, @Param("status") String status);

    /**
     * 通过扫码信息查询工单信息
     * @param pnMain 扫码信息
     * @return 结果
     */
    DevWorkOrder selectWorkInfoByScanInfo(@Param("pnMain") String pnMain);
}
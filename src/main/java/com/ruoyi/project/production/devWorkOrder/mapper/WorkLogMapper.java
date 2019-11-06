package com.ruoyi.project.production.devWorkOrder.mapper;

import com.ruoyi.project.production.devWorkOrder.domain.WorkLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拉长录入 数据层
 * @Author: Rainey
 * @Date: 2019/9/26 12:16
 * @Version: 1.0
 **/
public interface WorkLogMapper {

    /**
     * 新增记录
     *
     * @param workLog 工单录入记录
     * @return 结果
     */
    public int insertWorkLog(WorkLog workLog);

    /**
     * 通过工单id公司id查询录入明细列表
     * @param workId 工单id
     * @param companyId 公司id
     * @return 结果
     */
    List<WorkLog> selectWorkLogListByWorkId(@Param("workId") Integer workId,@Param("companyId") Integer companyId);

    /**
     * 查询拉长录入明细
     * @param workLog 看板信息
     * @return 结果
     */
    List<WorkLog> selectWorkLogListByWatchInfo(WorkLog workLog);
}

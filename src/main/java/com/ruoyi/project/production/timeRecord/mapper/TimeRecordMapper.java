package com.ruoyi.project.production.timeRecord.mapper;

import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公司考勤记录 数据层
 *
 * @author sj
 * @date 2019-10-24
 */
public interface TimeRecordMapper {
    /**
     * 查询公司考勤记录信息
     *
     * @param id 公司考勤记录ID
     * @return 公司考勤记录信息
     */
    public TimeRecord selectTimeRecordById(Integer id);

    /**
     * 查询公司考勤记录列表
     *
     * @param timeRecord 公司考勤记录信息
     * @return 公司考勤记录集合
     */
    public List<TimeRecord> selectTimeRecordList(TimeRecord timeRecord);

    /**
     * 新增公司考勤记录
     *
     * @param timeRecord 公司考勤记录信息
     * @return 结果
     */
    public int insertTimeRecord(TimeRecord timeRecord);

    /**
     * 修改公司考勤记录
     *
     * @param timeRecord 公司考勤记录信息
     * @return 结果
     */
    public int updateTimeRecord(TimeRecord timeRecord);

    /**
     * 删除公司考勤记录
     *
     * @param id 公司考勤记录ID
     * @return 结果
     */
    public int deleteTimeRecordById(Integer id);

    /**
     * 批量删除公司考勤记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTimeRecordByIds(String[] ids);

    /**
     * 查看当天对应产线考勤录入信息
     *
     * @param companyId 公司id
     * @param lineId    产线id
     * @return 结果
     */
    TimeRecord selectTimeRecordByLineIdToday(@Param("companyId") Integer companyId, @Param("lineId") Integer lineId);

    /**
     * 查询对应产线指定天的考勤汇总信息
     *
     * @param companyId  公司id
     * @param lineId     产线id
     * @param recordDate 考勤时间
     * @return 结果
     */
    TimeRecord selectTimeRecordByLineTime(@Param("companyId") Integer companyId, @Param("lineId") Integer lineId, @Param("recordDate") String recordDate);

    /**
     * 查询时间范围的考勤信息
     * @param companyId 公司id
     * @param lineId 产线id
     * @param startTime 开始时间
     * @param endTime 结果时间
     * @return 结果
     */
    List<TimeRecord> selectTimeRecordListRangeTime(@Param("companyId") Integer companyId, @Param("lineId") Integer lineId,
                                                   @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询昨天的考勤信息
     * @param companyId 公司id
     * @param lineId 产线id
     * @return 结果
     */
    TimeRecord selectTimeRecordByLineIdYesterday(@Param("companyId") Integer companyId, @Param("lineId") Integer lineId);
}
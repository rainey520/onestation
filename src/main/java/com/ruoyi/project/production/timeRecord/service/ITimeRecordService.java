package com.ruoyi.project.production.timeRecord.service;

import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import java.util.List;

/**
 * 公司考勤记录 服务层
 * 
 * @author sj
 * @date 2019-10-24
 */
public interface ITimeRecordService 
{
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
     * 删除公司考勤记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTimeRecordByIds(String ids);

	/**
	 * 查看对应产线当天考勤信息
	 * @param lineId 产线id
	 * @return 结果
	 */
    TimeRecord selectTimeRecordByLineIdToday(Integer lineId);

	/**
	 * 查询昨天的考勤信息
	 * @param lineId 产线id
	 * @return 结果
	 */
	TimeRecord selectTimeRecordByLineIdYesterday(Integer lineId);
}

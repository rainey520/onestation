package com.ruoyi.project.production.timeRecord.service;

import com.ruoyi.common.constant.CompanyConstants;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import com.ruoyi.project.production.timeRecord.mapper.TimeRecordMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 公司考勤记录 服务层实现
 *
 * @author sj
 * @date 2019-10-24
 */
@Service
public class TimeRecordServiceImpl implements ITimeRecordService {
    @Autowired
    private TimeRecordMapper timeRecordMapper;

    @Autowired
    private DevWorkOrderMapper workOrderMapper;

    /**
     * 查询公司考勤记录信息
     *
     * @param id 公司考勤记录ID
     * @return 公司考勤记录信息
     */
    @Override
    public TimeRecord selectTimeRecordById(Integer id) {
        return timeRecordMapper.selectTimeRecordById(id);
    }

    /**
     * 查询公司考勤记录列表
     *
     * @param timeRecord 公司考勤记录信息
     * @return 公司考勤记录集合
     */
    @Override
    public List<TimeRecord> selectTimeRecordList(TimeRecord timeRecord) {
        return timeRecordMapper.selectTimeRecordList(timeRecord);
    }

    /**
     * 新增公司考勤记录
     *
     * @param timeRecord 公司考勤记录信息
     * @return 结果
     */
    @Override
    public int insertTimeRecord(TimeRecord timeRecord) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return 0;
        }
        updateHourInfo(timeRecord, timeRecord);
        timeRecord.setCompanyId(user.getCompanyId());
        // 昨天
        if (CompanyConstants.DAY_IS_YESTERDAY.equals(timeRecord.getDaySign())) {
            Calendar cal= Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            timeRecord.setRecordDate(cal.getTime());
            timeRecord.setInputFlag(CompanyConstants.LINE_TIME_RECORD_CONFIRM);
        } else {
            timeRecord.setRecordDate(new Date());
        }
        return timeRecordMapper.insertTimeRecord(timeRecord);
    }

    /**
     * 更新考勤时间信息
     *
     * @param timeRecord 录入时间
     * @param record     更新时间
     */
    private void updateHourInfo(TimeRecord timeRecord, TimeRecord record) {
        record.setNormalHour(timeRecord.getNormalHour() == null ? 0F : timeRecord.getNormalHour());
        record.setNormalNumber(timeRecord.getNormalNumber() == null ? 0 : timeRecord.getNormalNumber());
        record.setOvertimeHour(timeRecord.getOvertimeHour() == null ? 0F : timeRecord.getOvertimeHour());
        record.setOvertimeNumber(timeRecord.getOvertimeNumber() == null ? 0 : timeRecord.getOvertimeNumber());
        record.setChangeHour(timeRecord.getChangeHour() == null ? 0F : timeRecord.getChangeHour());
    }

    /**
     * 修改公司考勤记录
     *
     * @param timeRecord 公司考勤记录信息
     * @return 结果
     */
    @Override
    public int updateTimeRecord(TimeRecord timeRecord) {
        updateHourInfo(timeRecord, timeRecord);
        if (CompanyConstants.DAY_IS_YESTERDAY.equals(timeRecord.getDaySign())) {
            timeRecord.setInputFlag(CompanyConstants.LINE_TIME_RECORD_CONFIRM);
        } else {
            timeRecord.setInputFlag(CompanyConstants.LINE_TIME_RECORD_NOT_CONFIRM);
        }
        return timeRecordMapper.updateTimeRecord(timeRecord);
    }

    /**
     * 删除公司考勤记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTimeRecordByIds(String ids) {
        return timeRecordMapper.deleteTimeRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 查看当天产线考勤录入信息
     *
     * @param lineId 产线id
     * @return 结果
     */
    @Override
    public TimeRecord selectTimeRecordByLineIdToday(Integer lineId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return null;
        }
        return timeRecordMapper.selectTimeRecordByLineIdToday(user.getCompanyId(), lineId);
    }

    /**
     * 查询昨天的考勤信息
     *
     * @param lineId 产线id
     * @return 结果
     */
    @Override
    public TimeRecord selectTimeRecordByLineIdYesterday(Integer lineId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return null;
        }
        TimeRecord record = timeRecordMapper.selectTimeRecordByLineIdYesterday(user.getCompanyId(), lineId);
        // 查看昨天更新的工单信息
        List<DevWorkOrder> workOrders = workOrderMapper.selectWorkOrderAllYesterday(user.getCompanyId(),lineId);
        if (StringUtils.isEmpty(workOrders)) {
            record = new TimeRecord();
            record.setInputFlag(CompanyConstants.LINE_TIME_RECORD_CONFIRM);
        } else{
            if (record == null) {
                record = new TimeRecord();
                record.setInputFlag(CompanyConstants.LINE_TIME_RECORD_NOT_CONFIRM);
            }
        }
        return record;
    }
}

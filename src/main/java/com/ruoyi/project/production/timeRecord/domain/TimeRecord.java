package com.ruoyi.project.production.timeRecord.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 公司考勤记录表 tab_time_record
 * 
 * @author sj
 * @date 2019-10-24
 */
public class TimeRecord extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 公司考勤记录主键id */
	private Integer id;
	/** 公司id */
	private Integer companyId;
	/** 产线id */
	private Integer lineId;
	/** 正常工时数 */
	private Float normalHour;
	/** 正常用工人数 */
	private Integer normalNumber;
	/** 加班小时数 */
	private Float overtimeHour;
	/** 加班的人数 */
	private Integer overtimeNumber;
	/** 加班倍率(1.5、2、3) */
	private Float overtimeRace;
	/** 手动调整工时 */
	private Float changeHour;
	/** 记录日期年月日 */
	private Date recordDate;
	/** 填写输入标记 0、还有未录入完整的数据，1、录入信息完整 */
	private Integer inputFlag;
	/** 昨天标记为1 今天标记为0 */
	private Integer daySign;

	public Integer getDaySign() {
		return daySign;
	}

	public void setDaySign(Integer daySign) {
		this.daySign = daySign;
	}

	public Integer getInputFlag() {
		return inputFlag;
	}

	public void setInputFlag(Integer inputFlag) {
		this.inputFlag = inputFlag;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setCompanyId(Integer companyId) 
	{
		this.companyId = companyId;
	}

	public Integer getCompanyId() 
	{
		return companyId;
	}
	public void setLineId(Integer lineId) 
	{
		this.lineId = lineId;
	}

	public Integer getLineId() 
	{
		return lineId;
	}
	public void setNormalHour(Float normalHour) 
	{
		this.normalHour = normalHour;
	}

	public Float getNormalHour() 
	{
		return normalHour;
	}
	public void setNormalNumber(Integer normalNumber) 
	{
		this.normalNumber = normalNumber;
	}

	public Integer getNormalNumber() 
	{
		return normalNumber;
	}
	public void setOvertimeHour(Float overtimeHour) 
	{
		this.overtimeHour = overtimeHour;
	}

	public Float getOvertimeHour() 
	{
		return overtimeHour;
	}
	public void setOvertimeNumber(Integer overtimeNumber) 
	{
		this.overtimeNumber = overtimeNumber;
	}

	public Integer getOvertimeNumber() 
	{
		return overtimeNumber;
	}
	public void setOvertimeRace(Float overtimeRace) 
	{
		this.overtimeRace = overtimeRace;
	}

	public Float getOvertimeRace() 
	{
		return overtimeRace;
	}
	public void setChangeHour(Float changeHour) 
	{
		this.changeHour = changeHour;
	}

	public Float getChangeHour() 
	{
		return changeHour;
	}
	public void setRecordDate(Date recordDate) 
	{
		this.recordDate = recordDate;
	}

	public Date getRecordDate() 
	{
		return recordDate;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("lineId", getLineId())
            .append("normalHour", getNormalHour())
            .append("normalNumber", getNormalNumber())
            .append("overtimeHour", getOvertimeHour())
            .append("overtimeNumber", getOvertimeNumber())
            .append("overtimeRace", getOvertimeRace())
            .append("changeHour", getChangeHour())
            .append("recordDate", getRecordDate())
            .toString();
    }
}

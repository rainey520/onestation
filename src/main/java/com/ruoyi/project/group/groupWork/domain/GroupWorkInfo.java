package com.ruoyi.project.group.groupWork.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 工单产品建档表 tab_group_work_info
 * 
 * @author sj
 * @date 2019-11-30
 */
public class GroupWorkInfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 建档主键id */
	private Integer id;
	/** 小组id */
	private Integer groupId;
	/** 工单id */
	private Integer workId;
	/** 每个产品建档信息 */
	@Excel(name = "产品建档信息", type = Excel.Type.EXPORT)
	private String pnMain;
	/** 扫码排序预留字段 */
	private String sign;
	/** 该产品扫码完成状态(默认0未完成、1已完成) */
	private String status;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setGroupId(Integer groupId) 
	{
		this.groupId = groupId;
	}

	public Integer getGroupId() 
	{
		return groupId;
	}
	public void setWorkId(Integer workId) 
	{
		this.workId = workId;
	}

	public Integer getWorkId() 
	{
		return workId;
	}
	public void setPnMain(String pnMain) 
	{
		this.pnMain = pnMain;
	}

	public String getPnMain() 
	{
		return pnMain;
	}
	public void setSign(String sign) 
	{
		this.sign = sign;
	}

	public String getSign() 
	{
		return sign;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getStatus() 
	{
		return status;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("workId", getWorkId())
            .append("pnMain", getPnMain())
            .append("sign", getSign())
            .append("status", getStatus())
            .toString();
    }
}

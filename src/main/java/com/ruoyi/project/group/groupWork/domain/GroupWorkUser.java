package com.ruoyi.project.group.groupWork.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 工单分配小组人员历史表 tab_group_work_user
 * 
 * @author sj
 * @date 2019-11-30
 */
public class GroupWorkUser extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 小组工单关联表管理主键id */
	private Integer gwId;
	/** 用户id */
	private Integer userId;

	public void setGwId(Integer gwId) 
	{
		this.gwId = gwId;
	}

	public Integer getGwId() 
	{
		return gwId;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
	{
		return userId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("gwId", getGwId())
            .append("userId", getUserId())
            .toString();
    }
}

package com.ruoyi.project.group.groupInfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 小组员工关联表 tab_group_user
 * 
 * @author sj
 * @date 2019-11-30
 */
public class GroupUser extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 小组id */
	private Integer groupId;
	/** 用户id */
	private Integer userId;
	/** 用户名称 */
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}

	public Integer getGroupId() 
	{
		return groupId;
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
            .append("groupId", getGroupId())
            .append("userId", getUserId())
            .toString();
    }
}

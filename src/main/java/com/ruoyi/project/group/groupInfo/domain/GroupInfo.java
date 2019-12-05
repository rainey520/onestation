package com.ruoyi.project.group.groupInfo.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 小组表 tab_group_info
 * 
 * @author sj
 * @date 2019-11-30
 */
public class GroupInfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 小组主键id */
	private Integer id;
	/** 公司id */
	private Integer companyId;
	/** 小组名称 */
	private String groupName;
	/** 创建时间 */
	private Date cTime;
	/** 员工id信息 */
	private Integer[] userIds;
	/** 员工名称信息 */
	private List<String> userNames;
	/** 工单下发数量 */
	private int workAllNum;
	/** 完成数量 */
	private int finishNum;


	/** APP交互使用工单id */
	private int workId;

	public int getWorkId() {
		return workId;
	}

	public void setWorkId(int workId) {
		this.workId = workId;
	}

	public int getWorkAllNum() {
		return workAllNum;
	}

	public void setWorkAllNum(int workAllNum) {
		this.workAllNum = workAllNum;
	}

	public int getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(int finishNum) {
		this.finishNum = finishNum;
	}

	public List<String> getUserNames() {
		return userNames;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Integer[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Integer[] userIds) {
		this.userIds = userIds;
	}

	@Override
	public String toString() {
		return "GroupInfo{" +
				"id=" + id +
				", companyId=" + companyId +
				", groupName='" + groupName + '\'' +
				", cTime=" + cTime +
				", userIds=" + Arrays.toString(userIds) +
				", userNames=" + userNames +
				", workAllNum=" + workAllNum +
				", finishNum=" + finishNum +
				", workId=" + workId +
				'}';
	}
}

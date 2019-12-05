package com.ruoyi.project.group.groupWork.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 小组工单关联管理表 tab_group_work
 *
 * @author sj
 * @date 2019-11-30
 */
public class GroupWork extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Integer id;
	/** 公司id */
	private Integer companyId;
	/** 分配所在小组id */
	private Integer groupId;
	/** 分配所在小组名称 */
	private String groupName;
	/** 工单id */
	private Integer workId;
	/**
	 * 工单生产状态未进行为0<
	 * 0:为默认值，新工单未开始,<br>
	 * 1:处于进行状态的工单,<br>
	 * 2:已经完成的工单
	 */
	private Integer workStatus;
	/** 工单产线id即工单分配的角色id */
	private Integer lineId;
	/** 工单号 */
	private String workCode;
	/** 产品信息 */
	private String pnCode;
	/** 分配在该小组的工单数量 */
	private Integer workNum;
	/** 该小组该工单实际做的数量 */
	private Integer actualNum;
	/** 分配时间 */
	private Date cTime;
    /** 任务完成标记(0、默认值未完成，1、已完成) */
	private String finishTag;
    /** 任务完成时间 */
    private Date finishTime;

	/** 操作权限  0、没有权限，1、有权限*/
	private String opSign;
	/** 员工名称信息 */
	private List<String> userNames;


	/** 报表数据--工单总数 */
	private int workAllNum;
	/** 报表数据--工单实际完成数 */
	private int workActNum;
	/** 报表数据--工单时间 */
	private Date workTime;

    public int getWorkAllNum() {
        return workAllNum;
    }

    public void setWorkAllNum(int workAllNum) {
        this.workAllNum = workAllNum;
    }

    public int getWorkActNum() {
        return workActNum;
    }

    public void setWorkActNum(int workActNum) {
        this.workActNum = workActNum;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getFinishTag() {
        return finishTag;
    }

    public void setFinishTag(String finishTag) {
        this.finishTag = finishTag;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public List<String> getUserNames() {
		return userNames;
	}

	public void setUserNames(List<String> userNames) {
		this.userNames = userNames;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getPnCode() {
		return pnCode;
	}

	public void setPnCode(String pnCode) {
		this.pnCode = pnCode;
	}

	public Integer getWorkNum() {
		return workNum;
	}

	public void setWorkNum(Integer workNum) {
		this.workNum = workNum;
	}

	public Integer getActualNum() {
		return actualNum;
	}

	public void setActualNum(Integer actualNum) {
		this.actualNum = actualNum;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getOpSign() {
		return opSign;
	}

	public void setOpSign(String opSign) {
		this.opSign = opSign;
	}

    @Override
    public String toString() {
        return "GroupWork{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", workId=" + workId +
                ", workStatus=" + workStatus +
                ", lineId=" + lineId +
                ", workCode='" + workCode + '\'' +
                ", pnCode='" + pnCode + '\'' +
                ", workNum=" + workNum +
                ", actualNum=" + actualNum +
                ", cTime=" + cTime +
                ", finishTag='" + finishTag + '\'' +
                ", finishTime=" + finishTime +
                ", opSign='" + opSign + '\'' +
                ", userNames=" + userNames +
                ", workAllNum=" + workAllNum +
                ", workActNum=" + workActNum +
                ", workTime=" + workTime +
                '}';
    }
}

package com.ruoyi.project.device.devNotice.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 公司消息通知表 dev_notice
 *
 * @author zqm
 * @date 2019-04-18
 */
public class DevNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 消息主键ID
     */
    private Integer id;
    /**
     * 通知内容
     */
    private String noticeContent;
    /**
     * 创建者id
     */
    private Integer createId;
    /**
     * 通知者名称
     */
    private String createName;

    /** 用户头像 */
    private String avatar;
    /**
     * 消息通知者id
     */
    private Integer receiveBy;
    /**
     * 消息状态:1,未发布 2,已发布 3,已下线
     */
    private Integer noticeStatus;
    /**
     * 公司主键ID
     */
    private Integer companyId;
    /**
     * 消息创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getReceiveBy() {
        return receiveBy;
    }

    public void setReceiveBy(Integer receiveBy) {
        this.receiveBy = receiveBy;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DevNotice{" +
                "id=" + id +
                ", noticeContent='" + noticeContent + '\'' +
                ", createId=" + createId +
                ", createName='" + createName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", receiveBy=" + receiveBy +
                ", noticeStatus=" + noticeStatus +
                ", companyId=" + companyId +
                ", createTime=" + createTime +
                '}';
    }
}

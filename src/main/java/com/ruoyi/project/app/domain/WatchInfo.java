package com.ruoyi.project.app.domain;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.page.pageInfo.domain.PageTem;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Rainey
 * @Date: 2019/9/27 9:20
 * @Version: 1.0
 **/
public class WatchInfo implements Serializable {

    private static final long serialVersionUID = -8749265720711763804L;

    /** 登录账号密码 */
    private String loginNumber;
    private String loginPassword;
    /** 首次登陆时间戳 */
    private String firstTime;

    /** 产线信息 */
    private List<ProductionLine> lineList;
    /** 公司id */
    private Integer companyId;
    /** 工单id */
    private Integer workId;
    /** 公司信息 */
    private DevCompany company;

    /** 产线id或者产线id集合 */
    private Integer lineId;
    private String lineIds;

    /** 查看明细 */
    private WatchDetail watchDetail;
    /** 查看汇总 */
    private List<PageTem> watchList;

    /** app端分页 当前记录起始索引 */
    private Integer pageNum;
    /** app端分页 每页显示记录数 */
    private Integer pageSize;

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public DevCompany getCompany() {
        return company;
    }

    public void setCompany(DevCompany company) {
        this.company = company;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public List<ProductionLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<ProductionLine> lineList) {
        this.lineList = lineList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }

    public WatchDetail getWatchDetail() {
        return watchDetail;
    }

    public void setWatchDetail(WatchDetail watchDetail) {
        this.watchDetail = watchDetail;
    }

    public List<PageTem> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<PageTem> watchList) {
        this.watchList = watchList;
    }

    /**
     * app端设置请求分页数据
     */
    public void appStartPage()
    {
        if (StringUtils.isNotNull(getPageNum()) && StringUtils.isNotNull(getPageSize()))
        {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    @Override
    public String toString() {
        return "WatchInfo{" +
                "loginNumber='" + loginNumber + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", firstTime='" + firstTime + '\'' +
                ", lineList=" + lineList +
                ", companyId=" + companyId +
                ", workId=" + workId +
                ", company=" + company +
                ", lineId=" + lineId +
                ", lineIds='" + lineIds + '\'' +
                ", watchDetail=" + watchDetail +
                ", watchList=" + watchList +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}

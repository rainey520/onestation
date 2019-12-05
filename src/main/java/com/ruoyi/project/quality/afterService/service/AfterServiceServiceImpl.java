package com.ruoyi.project.quality.afterService.service;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.NumberUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.group.groupWork.domain.GroupWork;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkInfoMapper;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkMapper;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkUserMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.quality.afterService.domain.AfterService;
import com.ruoyi.project.quality.afterService.domain.AfterServiceItem;
import com.ruoyi.project.quality.afterService.domain.ScanDataView;
import com.ruoyi.project.quality.afterService.mapper.AfterServiceMapper;
import com.ruoyi.project.system.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 售后服务 服务层实现
 *
 * @author sj
 * @date 2019-08-20
 */
@Service
public class AfterServiceServiceImpl implements IAfterServiceService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AfterServiceServiceImpl.class);

    @Autowired
    private AfterServiceMapper afterServiceMapper;

    @Autowired
    private DevCompanyMapper companyMapper;

    @Autowired
    private GroupWorkInfoMapper groupWorkInfoMapper;

    @Autowired
    private GroupWorkMapper groupWorkMapper;

    @Autowired
    private GroupWorkUserMapper groupWorkUserMapper;

    /**
     * 查询售后服务信息
     *
     * @param id 售后服务ID
     * @return 售后服务信息
     */
    @Override
    public AfterService selectAfterServiceById(Integer id) {
        return afterServiceMapper.selectAfterServiceById(id);
    }

    /**
     * 查询售后服务列表
     *
     * @param afterService 售后服务信息
     * @return 售后服务集合
     */
    @Override
    public List<AfterService> selectAfterServiceList(AfterService afterService) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        afterService.setCompanyId(user.getCompanyId());
        setTimeValid(afterService);
        return afterServiceMapper.selectAfterServiceList(afterService);
    }

    /**
     * 通过搜索条件分记录查询售后服务列表
     *
     * @param afterService 售后服务信息
     * @return 售后服务集合
     */
    @Override
    public List<AfterServiceItem> selectListBySearchInfo(AfterService afterService) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        afterService.setCompanyId(user.getCompanyId());
        setTimeValid(afterService);
        List<AfterServiceItem> serviceItemList = new ArrayList<>();
        if (StringUtils.isNotEmpty(afterService.getSearchItems())) {
            String[] strings = Convert.toStrArray(afterService.getSearchItems());
            AfterServiceItem serviceItem = null;
            AfterServiceItem item = null;
            for (String searchItem : strings) {
                serviceItem = new AfterServiceItem();
                // 搜索条件
                serviceItem.setSearchItem(searchItem);
                // 查询录入该条件的所有人的信息
                serviceItem.setUserNames(afterServiceMapper.selectListBySearchInfoUserName(user.getCompanyId(), searchItem, afterService.getParams()));
                afterService.setInputBatchInfo(searchItem);
                // 查询总共的记录数
                item = afterServiceMapper.selectListByBatchInfo(afterService);
                if (StringUtils.isNotNull(item)) {
                    serviceItem.setTotalNum(item.getTotalNum());
                    serviceItem.setsTime(item.getsTime());
                    serviceItem.seteTime(item.geteTime());
                }
                serviceItemList.add(serviceItem);
            }
        }
        return serviceItemList;
    }

    /**
     * 新增售后服务,同时拉取对应数据信息
     *
     * @param afterService 售后服务信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertAfterService(AfterService afterService) {
        try {
            // 获取扫码信息
            String pnMain = afterService.getInputBatchInfo();
            if (StringUtils.isEmpty(pnMain)) {
                return AjaxResult.api(1, "扫码信息不能为空", null);
            }
            // 查询数据库信息
            GroupWorkInfo workInfo = groupWorkInfoMapper.selectMesBatchByMainInfo(afterService.getInputBatchInfo());
            if (workInfo == null) {
                return AjaxResult.api(1, "未找到建档信息", null);
            }
            User user = JwtUtil.getUser();
            if (user == null) {
                return AjaxResult.api(1, "用户未登录或登录超时", null);
            }
            // 查询工单信息
            DevWorkOrder workOrder = groupWorkInfoMapper.selectWorkInfoByScanInfo(pnMain);
            if (workOrder != null) {
                afterService.setWorkId(workOrder.getId());
            }
            afterService.setCompanyId(user.getCompanyId());
            afterService.setInputUserId(user.getUserId().intValue());
            afterService.setInputTime(new Date());
            afterServiceMapper.insertAfterService(afterService);
            // 返回对应检索信息
            return getResult(pnMain, workOrder);
        } catch (Exception e) {
            LOGGER.error("新增售后服务,同时拉取对应数据信息出现异常：" + e.getMessage());
            return AjaxResult.api(500, "系统异常", null);
        }
    }


    /**
     * 修改售后服务
     *
     * @param afterService 售后服务信息
     * @return 结果
     */
    @Override
    public int updateAfterService(AfterService afterService) {
        return afterServiceMapper.updateAfterService(afterService);
    }

    /**
     * 删除售后服务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAfterServiceByIds(String ids) {
        return afterServiceMapper.deleteAfterServiceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除录入信息
     *
     * @param id 录入id
     * @return 结果
     */
    @Override
    public int deleteAfterServiceById(Integer id) {
        return afterServiceMapper.deleteAfterServiceById(id);
    }

    /**
     * 用户输入建档信息查询对应数据
     *
     * @param pnMain 查询信息
     * @return 结果
     */
    @Override
    public AjaxResult searchPnMain(String pnMain) {
        try {
            if (StringUtils.isEmpty(pnMain)) {
                return AjaxResult.api(1, "扫码信息不能为空", null);
            }
            // 查询数据库信息
            GroupWorkInfo workInfo = groupWorkInfoMapper.selectMesBatchByMainInfo(pnMain);
            if (workInfo == null) {
                return AjaxResult.api(1, "未找到建档信息", null);
            }
            User user = JwtUtil.getUser();
            if (user == null) {
                return AjaxResult.api(1, "用户未登录或登录超时", null);
            }
            // 查询工单信息
            DevWorkOrder workOrder = groupWorkInfoMapper.selectWorkInfoByScanInfo(pnMain);
            // 返回对应检索信息
            return getResult(pnMain, workOrder);
        } catch (Exception e) {
            LOGGER.error("用户输入建档信息查询对应数据：" + e.getMessage());
            return AjaxResult.api(500, "系统异常", null);
        }

    }

    /**
     * 设置检索有效期
     *
     * @param afterService
     */
    private void setTimeValid(AfterService afterService) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 检索开始时间
        String searchBeginTime = (String) afterService.getParams().get("beginTime");
        Date bTime = DateUtils.parseDate(searchBeginTime);

        // 检索结束时间
        String searchEndTime = (String) afterService.getParams().get("endTime");
        Date eTime = DateUtils.parseDate(searchEndTime);

        // 查询公司是否为会员
        DevCompany devCompany = companyMapper.selectDevCompanyById(JwtUtil.getUser().getCompanyId());
        if (devCompany != null && devCompany.getSign() != 1) {
            // 有效时间，非会员检索时间为13个月，会员为永久
            Date validDate = DateUtils.stepMonth(new Date(), -13);
            String validDateStr = format.format(validDate);
            if (bTime == null) {
                searchBeginTime = validDateStr;
            }
            if (bTime != null) {
                int i = bTime.compareTo(validDate);
                if (i == -1) {
                    searchBeginTime = validDateStr;
                    throw new BusinessException("超过有效搜索时间");
                }
            }

            if (eTime != null) {
                int i = eTime.compareTo(validDate);
                if (i == -1) {
                    searchEndTime = validDateStr;
                }
            }
        }
        afterService.setSearchBeginTime(searchBeginTime);
        afterService.setSearchEndTime(searchEndTime);
    }

    /**
     * 查询对应检索数据
     * @param pnMain 建档信息
     * @param workOrder 工单信息
     * @return 结果
     */
    private AjaxResult getResult(String pnMain, DevWorkOrder workOrder) {
        ScanDataView viewData = new ScanDataView();
        if (workOrder != null) {
            viewData.setWorkCode(StringUtils.isNotEmpty(workOrder.getWorkorderNumber()) ? workOrder.getWorkorderNumber() : "--");
            viewData.setPnCode(StringUtils.isNotEmpty(workOrder.getProductCode()) ? workOrder.getProductCode() : "--");
            viewData.setPnName(StringUtils.isNotEmpty(workOrder.getProductName()) ? workOrder.getProductName() : "--");
            viewData.setWorkNum(workOrder.getProductNumber() != null ? workOrder.getProductNumber() : 0);
            viewData.setWorkTime(DateUtils.getDateTime(workOrder.getStartTime()));
            viewData.setActNum(workOrder.getCumulativeNumber());
            // 查询工单一共的退货数量
            List<AfterService> backList = afterServiceMapper.selectAfterServiceByWorkId(workOrder.getId());
            if (StringUtils.isNotEmpty(backList)) {
                viewData.setBackPercent(NumberUtils.getPercent(backList.size(), workOrder.getCumulativeNumber(), 2));
            } else {
                viewData.setBackPercent("--");
            }
        }
        // 查询生产该扫码产品的小组信息
        GroupWork groupWork = groupWorkMapper.selectGroupWorkOne(pnMain);
        if (groupWork != null) {
            groupWork.setUserNames(groupWorkUserMapper.selectGroupFinishWorkUsers(groupWork.getId()));
        }
        viewData.setGroup(groupWork);
        // 查询生产该扫码产品的其他小组信息
        if (workOrder != null) {
            List<GroupWork> groupWorks = groupWorkMapper.selectGroupWorkByWorkId(workOrder.getId());
            for (GroupWork work : groupWorks) {
                work.setUserNames(groupWorkUserMapper.selectGroupFinishWorkUsers(work.getId()));
            }
            if (StringUtils.isNotEmpty(groupWorks)) {
                Iterator<GroupWork> iterator = groupWorks.iterator();
                //读取当前集合数据元素
                GroupWork uni = iterator.next();
                if (groupWork != null && groupWork.getId().equals(uni.getId())) {
                    //使用集合当中的remove方法对当前迭代器当中的数据元素值进行删除操作(注:此操作将会破坏整个迭代器结构)使得迭代器在接下来将不会起作用
                    iterator.remove();
                }
                viewData.setOtherGroups(groupWorks);
            }
        }
        return AjaxResult.api(0, "请求成功", viewData);
    }
}

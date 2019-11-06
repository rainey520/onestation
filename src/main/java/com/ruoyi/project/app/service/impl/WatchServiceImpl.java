package com.ruoyi.project.app.service.impl;

import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.CodeUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TimeUtil;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.app.domain.Index;
import com.ruoyi.project.app.domain.WatchDetail;
import com.ruoyi.project.app.domain.WatchInfo;
import com.ruoyi.project.app.service.IWatchService;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.device.devList.domain.DevList;
import com.ruoyi.project.device.devList.mapper.DevListMapper;
import com.ruoyi.project.page.pageInfo.domain.PageTem;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.domain.WorkLog;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.production.devWorkOrder.mapper.WorkLogMapper;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;
import com.ruoyi.project.production.productionLine.mapper.ProductionLineMapper;
import com.ruoyi.project.production.workExceptionList.domain.WorkExceptionList;
import com.ruoyi.project.production.workExceptionList.mapper.WorkExceptionListMapper;
import com.ruoyi.project.production.workExceptionType.domain.WorkExceptionType;
import com.ruoyi.project.production.workExceptionType.mapper.WorkExceptionTypeMapper;
import com.ruoyi.project.production.workstation.domain.Workstation;
import com.ruoyi.project.production.workstation.mapper.WorkstationMapper;
import com.ruoyi.project.system.config.domain.JpushInfo;
import com.ruoyi.project.system.config.mapper.JpushInfoMapper;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 看板数据拉取接口实现
 *
 * @Author: Rainey
 * @Date: 2019/9/27 9:35
 * @Version: 1.0
 **/
@Service
public class WatchServiceImpl implements IWatchService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchServiceImpl.class);

    @Autowired
    private ProductionLineMapper lineMapper;

    @Autowired
    private WorkstationMapper workstationMapper;

    @Autowired
    private DevWorkOrderMapper workOrderMapper;

    @Autowired
    private WorkLogMapper workLogMapper;

    @Autowired
    private WorkExceptionListMapper workExcMapper;

    @Autowired
    private WorkExceptionTypeMapper exceptionTypeMapper;

    @Autowired
    private DevCompanyMapper companyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JpushInfoMapper jpushInfoMapper;

    @Autowired
    private DevListMapper devListMapper;

    /**
     * 拉取看板信息
     *
     * @param watchInfo 看板信息
     * @return 结果
     */
    @Override
    public Map<String, Object> selectWatchInfo(WatchInfo watchInfo) {
        Map<String, Object> map = new HashMap<>(16);
        Integer companyId = null;
        try {
            if (watchInfo == null) {
                map.put("msg", "请求失败");
                map.put("code", 1);
                return map;
            }
            if (watchInfo.getCompanyId() != null) {
                companyId = watchInfo.getCompanyId();
            } else {
                User user = JwtUtil.getUser();
                companyId = user.getCompanyId();
            }
            if (companyId == null) {
                map.put("msg", "公司不存在或被删除");
                map.put("code", 1);
                return map;
            }
            // 公司信息
            DevCompany company = companyMapper.selectDevCompanyById(companyId);
            WatchInfo info = new WatchInfo();
            info.setCompany(company);
            // 查看看板明细
            if (StringUtils.isNotNull(watchInfo.getLineId())) {
                WatchDetail watchDetail = new WatchDetail();
                // 查看产线信息
                ProductionLine line = lineMapper.selectProductionLineById(watchInfo.getLineId());
                if (line == null) {
                    map.put("msg", "产线不存在或被删除");
                    map.put("code", 1);
                    return map;
                }
                watchDetail.setLine(line);
                // 查询正在该产线正在进行的工单信息
                DevWorkOrder workOrder = workOrderMapper.selectWorkByCompandAndLine(companyId, watchInfo.getLineId(), WorkConstants.SING_LINE);
                // 查询当天所有的工单信息
                watchDetail.setTodayWork(workOrderMapper.selectDayWorkOrder(WorkConstants.SING_LINE, companyId, line.getId()));
                if (workOrder == null) {
                    info.setWatchDetail(watchDetail);
                    map.put("msg", "没有正在进行的工单");
                    map.put("data", info);
                    map.put("code", 0);
                    return map;
                }
                watchDetail.setWorkOrder(workOrder);
                // 查看工单异常信息
                List<WorkExceptionList> excList = workExcMapper.selectWorkExceAllByWorkId(workOrder.getId());
                watchDetail.setExcList(excList);
                // 查看拉长输入明细
                watchInfo.setWorkId(workOrder.getId());
                watchInfo.appStartPage();
                WorkLog wk = new WorkLog();
                wk.setWorkId(workOrder.getId());
                wk.setCompanyId(companyId);
                wk.setPageNum(watchInfo.getPageNum());
                wk.setPageSize(watchInfo.getPageSize());
                List<WorkLog> workLogList = workLogMapper.selectWorkLogListByWatchInfo(wk);
                watchDetail.setWorkLogList(workLogList);
                info.setWatchDetail(watchDetail);
                map.put("msg", "请求成功");
                map.put("data", info);
                map.put("code", 0);
                return map;

                // 查看汇总信息
            } else if (StringUtils.isNotEmpty(watchInfo.getLineIds())) {
                List<PageTem> tems = new ArrayList<>();
                PageTem tem = null;
                ProductionLine line = null;
                DevWorkOrder order = null;
                WorkExceptionList workExceptionList = null;
                Integer[] lineIds = Convert.toIntArray(watchInfo.getLineIds());
                for (Integer lineId : lineIds) {
                    line = lineMapper.selectProductionLineById(lineId);
                    if (line != null) {
                        tem = new PageTem();
                        tem.setLineName(line.getLineName());
                        tem.setInputNum(0);
                        tem.setStandardNum(0);
                        tem.setOutputNum(0);
                        tem.setAchievementRate(0.00F);
                        tem.setWorkCode("-");
                        tem.setProductCode("-");
                        tem.setWorkStatus(0);
                        tem.setWorkNum(0);
                        tem.setLineManual(line.getManual());
                        tem.setEx(0);
                        //查询责任人
                        User user = userMapper.selectUserInfoById(line.getDeviceLiable());
                        if (user != null) {
                            tem.setPersonLiable(user.getUserName());
                        }
                        //查询各个产线正在进行的工单
                        order = workOrderMapper.selectWorkByCompandAndLine(line.getCompanyId(), line.getId(), WorkConstants.SING_LINE);
                        if (order != null) {
                            tem.setWorkCode(order.getWorkorderNumber());
                            tem.setProductCode(order.getProductCode());
                            tem.setWorkNum(order.getProductNumber());
                            tem.setWorkStatus(order.getOperationStatus());
                            tem.setNumber(order.getPeopleNumber() == null ? 0 : order.getPeopleNumber());
                            tem.setInputNum(order.getPutIntoNumber() == null ? 0 : order.getPutIntoNumber());
                            tem.setOutputNum(order.getCumulativeNumber() == null ? 0 : order.getCumulativeNumber());
                            //查询对应工单是否出现未处理异常情况
                            workExceptionList = workExcMapper.selectWorkExceNotByWorkId(order.getId());
                            if (workExceptionList != null) {
                                tem.setEx(1);
                            }
                            float totalHour = order.getSignHuor();
                            //计算标准产量
                            if (order.getOperationStatus().equals(WorkConstants.OPERATION_STATUS_STARTING)) {
                                totalHour += TimeUtil.getDateDel(order.getSignTime(), new Date());
                            }
                            tem.setStandardNum((int) (totalHour * order.getProductStandardHour()));
                            //计数达成率
                            if (tem.getOutputNum() > 0 && tem.getStandardNum() > 0) {
                                tem.setAchievementRate(new BigDecimal(((float) tem.getOutputNum() / (float) tem.getStandardNum()) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
                            }
                        }
                        tems.add(tem);
                    }
                }
                info.setWatchList(tems);
                map.put("msg", "请求成功");
                map.put("data", info);
                map.put("code", 0);
                return map;
            }
        } catch (Exception e) {
            LOGGER.error("app端拉取看板信息出现异常：" + e.getMessage());
        }
        map.put("msg", "请求失败");
        map.put("code", 1);
        return map;
    }

    /**
     * 看板登录
     *
     * @param watchInfo 登录信息
     * @return 结果
     */
    @Override
    public Map<String, Object> watchLogin(WatchInfo watchInfo) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            if (watchInfo != null && StringUtils.isNotEmpty(watchInfo.getLoginNumber()) && watchInfo.getLoginPassword() != null) {
                DevCompany company = companyMapper.selectCompanyByLoginInfo(watchInfo.getLoginNumber().toUpperCase(), watchInfo.getLoginPassword());
                if (company == null) {
                    map.put("code", 0);
                    map.put("msg", "登录账号或密码错误");
                    return map;
                }
                WatchInfo watch = new WatchInfo();
                // 平板端公司账号首次登陆
                if (StringUtils.isEmpty(watchInfo.getFirstTime())) {
                    // 生成时间戳
                    String firstTime = CodeUtils.getCode();
                    // 查询对应账号，对应时间戳是否存在
                    JpushInfo jpushInfo = jpushInfoMapper.selectJPushInfoByFirstTime(watchInfo.getLoginNumber().toUpperCase(), firstTime);
                    if (jpushInfo != null) {
                        map.put("code", 0);
                        map.put("msg", "登陆失败，请重新登陆");
                        return map;
                    }
                    jpushInfo = new JpushInfo();
                    jpushInfo.setCompanyNumber(watchInfo.getLoginNumber().toUpperCase());
                    jpushInfo.setFirstTime(firstTime);
                    jpushInfo.setLastTime(new Date());
                    jpushInfoMapper.insertJPushInfo(jpushInfo);

                    JpushInfo jInfo = jpushInfoMapper.selectJPushInfoById(jpushInfo.getId());
                    watch.setFirstTime(jInfo.getFirstTime());

                } else {
                    // 查询对应时间戳是否存在
                    JpushInfo jpushInfo = jpushInfoMapper.selectJPushInfoByFirstTime(watchInfo.getLoginNumber().toUpperCase(), watchInfo.getFirstTime());
                    if (jpushInfo == null) {
                        // 新增
                        jpushInfo = new JpushInfo();
                        jpushInfo.setCompanyNumber(watchInfo.getLoginNumber().toUpperCase());
                        jpushInfo.setFirstTime(watchInfo.getFirstTime());
                        jpushInfo.setLastTime(new Date());
                        jpushInfoMapper.insertJPushInfo(jpushInfo);
                    } else {
                        // 更新最后登录时间
                        jpushInfo.setLastTime(new Date());
                        jpushInfoMapper.updateJPushInfo(jpushInfo);
                    }
                    watch.setFirstTime(watchInfo.getFirstTime());
                }
                watch.setLineList(lineMapper.selectAllDef0(company.getCompanyId()));
                watch.setCompanyId(company.getCompanyId());
                map.put("code", 1);
                map.put("msg", "登录成功");
                map.put("data", watch);
                return map;
            }
            map.put("code", 0);
            map.put("msg", "登录失败");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("app端登录看板出现异常：" + e.getMessage());
        }
        map.put("code", 0);
        map.put("msg", "操作失败");
        return map;
    }

    /**
     * 异常上报
     *
     * @param index 上报信息
     * @return 结果
     */
    @Override
    public Map<String, Object> workExc(Index index) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            if (index != null && StringUtils.isNotEmpty(index.getWatchCode())) {
                // 查询硬件信息
                DevList devList = devListMapper.selectDevListByCode(index.getWatchCode());
                if (devList == null || devList.getCompanyId() == null) {
                    map.put("msg","看板编码不存在或未归属公司");
                    map.put("code",0);
                    return map;
                }
                // 查询工位信息
                Workstation workstation = workstationMapper.selectInfoByDevice(0, devList.getId(), 0);
                if (workstation == null) {
                    map.put("msg","工位不存在或被删除");
                    map.put("code",0);
                    return map;
                }
                // 查询产线信息
                ProductionLine line = lineMapper.selectProductionLineById(workstation.getLineId());
                if (line == null) {
                    map.put("msg","产线不存在或被删除");
                    map.put("code",0);
                    return map;
                }
                // 查询正在进行的工单信息
                DevWorkOrder workOrder = workOrderMapper.selectWorkByCompandAndLine(devList.getCompanyId(), line.getId(), WorkConstants.SING_LINE);
                if (workOrder == null) {
                    map.put("msg","没有正在进行的工单信息");
                    map.put("code",0);
                    return map;
                }
                // 查询对应异常类型是否存在
                WorkExceptionType exceptionType = exceptionTypeMapper.selectByCompanyAndTypeName(devList.getCompanyId(), "异常上报");
                if (exceptionType == null) {
                    exceptionType = new WorkExceptionType();
                    exceptionType.setCompanyId(devList.getCompanyId());
                    exceptionType.setTypeName("异常上报");
                    exceptionType.setDefId(0);
                    exceptionType.setCreateTime(new Date());
                    exceptionTypeMapper.insertWorkExceptionType(exceptionType);
                }
                // 绑定工单异常记录
                WorkExceptionList exceptionList = new WorkExceptionList();
                exceptionList.setCompanyId(devList.getCompanyId());
                exceptionList.setExceStatut(0);
                exceptionList.setExceType(exceptionType.getId());
                exceptionList.setWorkId(workOrder.getId());
                exceptionList.setRemark("工位:" + workstation.getwName() + ",看板编码：" + index.getWatchCode() + ",上报异常");
                exceptionList.setCreateTime(new Date());
                workExcMapper.insertWorkExceptionList(exceptionList);
                map.put("msg","上报成功");
                map.put("code", 1);
                return map;

            }
        } catch (Exception e) {

        }
        map.put("msg", "上报失败");
        map.put("code", 0);
        return map;
    }
}

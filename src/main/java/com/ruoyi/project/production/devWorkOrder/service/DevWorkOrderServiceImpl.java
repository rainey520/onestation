package com.ruoyi.project.production.devWorkOrder.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.CodeUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TimeUtil;
import com.ruoyi.common.utils.poi.PdfUtil;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.app.domain.Index;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.device.devList.mapper.DevListMapper;
import com.ruoyi.project.product.importConfig.domain.ImportConfig;
import com.ruoyi.project.product.importConfig.mapper.ImportConfigMapper;
import com.ruoyi.project.product.list.domain.DevProductList;
import com.ruoyi.project.product.list.mapper.DevProductListMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.domain.Ocr;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.production.ecnLog.domain.EcnLog;
import com.ruoyi.project.production.ecnLog.mapper.EcnLogMapper;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;
import com.ruoyi.project.production.productionLine.mapper.ProductionLineMapper;
import com.ruoyi.project.production.singleWork.domain.SingleWork;
import com.ruoyi.project.production.singleWork.domain.SingleWorkOrder;
import com.ruoyi.project.production.singleWork.mapper.SingleWorkMapper;
import com.ruoyi.project.production.singleWork.mapper.SingleWorkOrderMapper;
import com.ruoyi.project.production.workData.domain.WorkData;
import com.ruoyi.project.production.workData.mapper.WorkDataMapper;
import com.ruoyi.project.production.workExceptionList.domain.WorkExceptionList;
import com.ruoyi.project.production.workExceptionList.mapper.WorkExceptionListMapper;
import com.ruoyi.project.production.workExceptionType.domain.WorkExceptionType;
import com.ruoyi.project.production.workExceptionType.mapper.WorkExceptionTypeMapper;
import com.ruoyi.project.production.workOrderChange.domain.WorkOrderChange;
import com.ruoyi.project.production.workOrderChange.mapper.WorkOrderChangeMapper;
import com.ruoyi.project.production.workstation.mapper.WorkstationMapper;
import com.ruoyi.project.quality.mesBatch.domain.MesBatch;
import com.ruoyi.project.quality.mesBatch.domain.MesBatchDetail;
import com.ruoyi.project.quality.mesBatch.mapper.MesBatchDetailMapper;
import com.ruoyi.project.quality.mesBatch.mapper.MesBatchMapper;
import com.ruoyi.project.quality.mesBatchRule.domain.MesBatchRule;
import com.ruoyi.project.quality.mesBatchRule.domain.MesBatchRuleDetail;
import com.ruoyi.project.quality.mesBatchRule.mapper.MesBatchRuleDetailMapper;
import com.ruoyi.project.quality.mesBatchRule.mapper.MesBatchRuleMapper;
import com.ruoyi.project.system.config.mapper.JpushInfoMapper;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.mapper.UserMapper;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 工单 服务层实现
 *
 * @author zqm
 * @date 2019-04-12
 */
@Service("workOrder")
public class DevWorkOrderServiceImpl implements IDevWorkOrderService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DevWorkOrderServiceImpl.class);

    @Autowired
    private DevWorkOrderMapper devWorkOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductionLineMapper productionLineMapper;

    @Autowired
    private DevProductListMapper productListMapper;

    @Autowired
    private EcnLogMapper ecnLogMapper;

    @Autowired
    private WorkOrderChangeMapper workOrderChangeMapper;

    @Autowired
    private SingleWorkMapper singleWorkMapper;

    @Autowired
    private SingleWorkOrderMapper singleWorkOrderMapper;

    @Autowired
    private DevCompanyMapper companyMapper;

    @Autowired
    private ImportConfigMapper configMapper;

    @Autowired
    private MesBatchMapper mesBatchMapper;

    @Autowired
    private MesBatchDetailMapper mesBatchDetailMapper;

    @Autowired
    private MesBatchRuleMapper mesBatchRuleMapper;

    @Autowired
    private MesBatchRuleDetailMapper mesBatchRuleDetailMapper;

    @Autowired
    private WorkExceptionTypeMapper exceptionTypeMapper;

    @Autowired
    private WorkExceptionListMapper workExcMapper;

    @Autowired
    private DevListMapper devListMapper;

    @Autowired
    private WorkDataMapper workDataMapper;

    @Value("${file.iso}")
    private String fileUrl;

    /**
     * 查询工单信息
     *
     * @param id 工单ID
     * @return 工单信息
     */
    @Override
    public DevWorkOrder selectDevWorkOrderById(Integer id) {
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        if (workOrder.getWlSign() == 0) {
            ProductionLine productionLine = productionLineMapper.selectProductionLineById(workOrder.getLineId());
            workOrder.setParam1(productionLine.getLineName());
            User user = userMapper.selectUserInfoById(productionLine.getDeviceLiable());
            workOrder.setParam2(user == null ? "" : user.getUserName());
            productionLine.setDeviceLiableName(user == null ? "" : user.getUserName());
            user = userMapper.selectUserInfoById(productionLine.getDeviceLiableTow());
            workOrder.setParam3(user == null ? "" : user.getUserName());
            productionLine.setDeviceLiableTowName(user == null ? "" : user.getUserName());
            workOrder.setProductionLine(productionLine);
        } else if (workOrder.getWlSign() == 1) {
            //查询车间
            SingleWork work = singleWorkMapper.selectSingleWorkById(workOrder.getLineId());
            if (work != null) {
                workOrder.setSingle(work.getId());
                workOrder.setParam1(work.getWorkshopName());
                User user = userMapper.selectUserInfoById(work.getLiableOne());
                workOrder.setParam2(user == null ? "" : user.getUserName());
                user = userMapper.selectUserInfoById(work.getLiableTwo());
                workOrder.setParam3(user == null ? "" : user.getUserName());
            }
        }
        return workOrder;
    }

    /**
     * 查询工单列表
     *
     * @param devWorkOrder 工单信息
     * @return 工单集合
     */
    @Override
    public List<DevWorkOrder> selectDevWorkOrderList(DevWorkOrder devWorkOrder) {
        User sysUser = JwtUtil.getUser();
        if (sysUser == null) {
            return Collections.emptyList();
        }
        devWorkOrder.setCompanyId(sysUser.getCompanyId());
        List<DevWorkOrder> workOrders = devWorkOrderMapper.selectDevWorkOrderList(devWorkOrder);
        getLineOrHouseName(workOrders, sysUser);
        return workOrders;
    }

    /**
     * 新增车间工单
     *
     * @param devWorkOrder 工单信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDevWorkOrder(DevWorkOrder devWorkOrder) throws Exception {
        User u = JwtUtil.getTokenUser(ServletUtils.getRequest());
        if (u == null) return 0;
        Integer productId = Integer.valueOf(devWorkOrder.getProductCode());
        DevProductList devProductList = productListMapper.selectDevProductListById(productId);
        if (devProductList == null) return 0;
        // 设置工单的相关信息
        setWorkInfo(devWorkOrder, u, devProductList);
        // 工单为单工位工单
        devWorkOrder.setWlSign(WorkConstants.SING_SINGLE);
        return devWorkOrderMapper.insertDevWorkOrder(devWorkOrder);
    }


    /**
     * 修改工单
     *
     * @param devWorkOrder 工单信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDevWorkOrder(DevWorkOrder devWorkOrder) {
        User u = JwtUtil.getTokenUser(ServletUtils.getRequest());
        if (u == null) {
            throw new BusinessException("用户未登录或登录超时");
        }
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(devWorkOrder.getId());
        // 查询公司信息
        DevCompany company = companyMapper.selectDevCompanyById(u.getCompanyId());
        if (company == null) {
            throw new BusinessException("公司不存在或被删除");
        }
        if (workOrder == null) {
            throw new BusinessException("工单不存在或已经删除");
        }
        // 查询工单生产的产品信息
        DevProductList product = productListMapper.selectDevProductByCode(u.getCompanyId(), workOrder.getProductCode());
        if (product == null) {
            throw new BusinessException("工单生产的产品不存在或被删除");
        }
        if (workOrder.getWorkSign().equals(WorkConstants.WORK_SIGN_YES)) {
            throw new BusinessException("已经提交数据的工单不能进行修改");
        }
        // 查询车间信息
        SingleWork house = singleWorkMapper.selectSingleWorkById(workOrder.getLineId());
        if (house == null) {
            throw new BusinessException("工单所在车间不存在或被删除");
        }
        // 验证是否是责任人
        if (u.getUserId().intValue() != house.getLiableOne() && u.getUserId().intValue() != house.getLiableTwo()) {
            throw new BusinessException("不是工单所在车间责任人");
        }
        // 消息推送生产看板
        // JPushWatchMsg(company);
        // JPushMsg(workOrder);

        return devWorkOrderMapper.updateDevWorkOrder(devWorkOrder);
    }

    /**
     * 删除工单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDevWorkOrderByIds(String ids) {
        Integer[] workIds = Convert.toIntArray(ids);
        DevWorkOrder devWorkOrder = null;
        List<MesBatch> mesBatchList = null;
        for (Integer workId : workIds) {
            devWorkOrder = devWorkOrderMapper.selectDevWorkOrderById(workId);
            if (devWorkOrder != null) {
                mesBatchList = mesBatchMapper.selectMesBatchListByWorkCode(JwtUtil.getUser().getCompanyId(), devWorkOrder.getWorkorderNumber());
                for (MesBatch mesBatch : mesBatchList) {
                    mesBatchDetailMapper.deleteMesBatchDetailByBId(mesBatch.getId());
                }
                mesBatchMapper.deleteMesBatchByWorkCode(devWorkOrder.getWorkorderNumber());
            }
        }
        return devWorkOrderMapper.deleteDevWorkOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 校验工单号是否存在
     *
     * @return
     */
    @Override
    public String checkWorkOrderNumber(DevWorkOrder devWorkOrder, HttpServletRequest request) {
        Integer companyId = JwtUtil.getTokenUser(request).getCompanyId();
        DevWorkOrder uniqueWork = devWorkOrderMapper.checkWorkOrderNumber(devWorkOrder.getWorkorderNumber(), companyId);
        if (uniqueWork != null) {
            return WorkConstants.WORKERORDER_NUMBER_NOT_UNIQUE;
        }
        return WorkConstants.WORKERORDER_NUMBER_UNIQUE;
    }

    /**
     * 工单状态控制 <br>
     * 点击开始，开始工单，数据进行初始化
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editWorkerOrderById(Integer id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        // 查询公司信息
        DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
        if (company == null) {
            throw new BusinessException("公司信息不存在或被删除");
        }
        DevWorkOrder devWorkOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        SingleWork house = singleWorkMapper.selectSingleWorkById(devWorkOrder.getLineId());
        if (house == null) {
            throw new BusinessException("车间不存在或被删除");
        }
        if (user.getUserId().intValue() != house.getLiableOne() && user.getUserId().intValue() != house.getLiableTwo()) {
            throw new BusinessException("不是该工单所在车间责任人");
        }
        // 工单生产状态处于进行中
        if (null != devWorkOrder && devWorkOrder.getWorkorderStatus().equals(WorkConstants.WORK_STATUS_STARTING)) {
            // 页面点击暂停按钮暂时暂停工单生产
            if (devWorkOrder.getOperationStatus().equals(WorkConstants.OPERATION_STATUS_STARTING)) {
                devWorkOrder.setOperationStatus(WorkConstants.OPERATION_STATUS_PAUSE);
                devWorkOrder.setUpdateBy(user.getUserName());
                //计数时间
                devWorkOrder.setSignHuor(devWorkOrder.getSignHuor() + TimeUtil.getDateDel(devWorkOrder.getSignTime(), new Date()));

                //页面点击开始按钮继续工单生产
            } else if (devWorkOrder.getOperationStatus().equals(WorkConstants.OPERATION_STATUS_PAUSE)) {
                // 计数状态标志重新标志位计数0
                devWorkOrder.setPbSign(WorkConstants.PB_SIGN_YES);
                devWorkOrder.setOperationStatus(WorkConstants.OPERATION_STATUS_STARTING);
                devWorkOrder.setUpdateBy(user.getUserName());
                devWorkOrder.setSignTime(new Date());
            }
        }
        //首次点击开始，工单处于未进行、未开始的状态，页面点击开始按钮
        if (null != devWorkOrder && devWorkOrder.getWorkorderStatus().equals(WorkConstants.WORK_STATUS_NOSTART)) {
            // 判断该工单配置的单工位是否存在正在进行的工单信息
            List<SingleWorkOrder> singleWorkOrderList = singleWorkOrderMapper.selectSingleWorkByWorkIdAndPid(devWorkOrder.getLineId(), id);
            if (StringUtils.isEmpty(singleWorkOrderList)) {
                throw new BusinessException("该工单未分配，请先分配工单");
            }
            DevWorkOrder order = null;
            WorkData workData = null;
            SingleWork station = null;
            StringBuilder failMsg = new StringBuilder();
            int failNum = 0;
            failMsg.append("工单开始失败,原因如下:");
            for (SingleWorkOrder singleWorkOrder : singleWorkOrderList) {
                order = devWorkOrderMapper.selectWorkInHouseBeInBySingId(devWorkOrder.getCompanyId(), devWorkOrder.getLineId(), WorkConstants.SING_SINGLE,
                        singleWorkOrder.getSingleId(), WorkConstants.WORK_STATUS_STARTING);
                if (StringUtils.isNotNull(order) && !order.getId().equals(id)) {
                    failNum++;
                    failMsg.append("<br/>" + failNum + "、单工位" + singleWorkOrder.getWorkshopName() + "存在未完成的工单，请先完成单工位工单或删除关联配置。");
                }
                // 查询工位操作员是否存在
                station = singleWorkMapper.selectSingleWorkById(singleWorkOrder.getSingleId());
                if (station == null) {
                    failNum++;
                    failMsg.append("<br/>" + failNum + "、单工位" + singleWorkOrder.getWorkshopName() + "不存在或被删除");
                }
                if (station != null && station.getLiableOne() == null) {
                    failNum++;
                    failMsg.append("<br/>" + failNum + "、请先配置单工位" + singleWorkOrder.getWorkshopName() + "操作员");
                }
                // 新增工单数据记录
                if (station != null && station.getDevId() > 0) {
                    workData = new WorkData();
                    workData.setCompanyId(user.getCompanyId());
                    workData.setWorkId(devWorkOrder.getId());
                    workData.setLineId(devWorkOrder.getLineId());
                    workData.setDevId(station.getDevId());
                    workData.setDevName(devListMapper.selectDevListById(station.getDevId()).getDeviceName());
                    workData.setIoId(singleWorkOrder.getSingleId());
                    workData.setCreateTime(new Date());
                    workData.setScType(WorkConstants.SING_SINGLE);
                    workDataMapper.insertWorkData(workData);
                }
            }
            if (failNum > 0) {
                throw new BusinessException(failMsg.toString());
            }

            if (devWorkOrder.getOperationStatus().equals(WorkConstants.OPERATION_STATUS_NOSTART)) {
                // 实际开始时间
                devWorkOrder.setStartTime(new Date());
                //标记开始时间
                devWorkOrder.setSignTime(new Date());
                //标记用时
                devWorkOrder.setSignHuor(0F);
            }

            // 推送ASOP
            // JPushMsg(devWorkOrder);
            // 推送生产看板
            // JPushWatchMsg(company);

            // 修改工单的状态为进行中
            devWorkOrder.setWorkorderStatus(WorkConstants.WORK_STATUS_STARTING);
            // 修改工单的操作状态为正在进行，页面显示暂停按钮
            devWorkOrder.setOperationStatus(WorkConstants.OPERATION_STATUS_STARTING);
            devWorkOrder.setUpdateBy(user.getUserName());

        }
        return devWorkOrderMapper.updateDevWorkOrder(devWorkOrder);
    }


    /**
     * 校验流水线是否只有一个处于生产状态的工单
     *
     * @param lineId
     * @return
     */
    @Override
    public DevWorkOrder checkWorkLineUnique(Integer lineId) {
        return devWorkOrderMapper.checkWorkLineUnique(lineId, WorkConstants.SING_LINE);
    }

    /**
     * 结束工单
     *
     * @param id 工单id
     * @return 结果
     */
    @Override
    public int finishWorkerOrder(Integer id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
        if (company == null) {
            throw new BusinessException("公司信息不存在或被删除");
        }
        DevWorkOrder devWorkOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        if (devWorkOrder == null) {
            throw new BusinessException("工单不存在或被删除");
        }
        // 查询车间责任人信息
        SingleWork house = singleWorkMapper.selectSingleWorkById(devWorkOrder.getLineId());
        if (house == null) {
            throw new BusinessException("车间不存在或被删除");
        }
        if (user.getUserId().intValue() != house.getLiableOne() && user.getUserId().intValue() != house.getLiableTwo()) {
            throw new BusinessException("不是该工单所在车间责任人");
        }
        // 推送生产看板
        // JPushWatchMsg(company);
        // 推送ASOP
        // JPushMsg(devWorkOrder);
        updateWork(user, devWorkOrder);
        return devWorkOrderMapper.updateDevWorkOrder(devWorkOrder);
    }

    /**
     * 结束工单更新工单相关信息
     *
     * @param user         用户
     * @param devWorkOrder 工单信息
     */
    private void updateWork(User user, DevWorkOrder devWorkOrder) {
        // 设置工单的生产状态为已经完成
        devWorkOrder.setWorkorderStatus(WorkConstants.WORK_STATUS_END);
        // 确认数据
        devWorkOrder.setWorkSign(WorkConstants.WORK_SIGN_YES);
        // 设置工单的操作状态为结束
        devWorkOrder.setOperationStatus(WorkConstants.OPERATION_STATUS_FINISH);
        // 设置结束时间
        devWorkOrder.setEndTime(new Date());
        // 设置统计用时
        devWorkOrder.setSignHuor(devWorkOrder.getSignHuor() + TimeUtil.getDateDel(devWorkOrder.getSignTime(), new Date()));
        devWorkOrder.setUpdateBy(user.getUserName());
        devWorkOrder.setUpdateTime(new Date());
    }

    /**
     * 提交工单确认工单
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int submitWorkOrder(Integer id, Integer uid) {
        User user = null;
        if (uid == null) {
            user = JwtUtil.getUser();
        } else {
            user = userMapper.selectUserInfoById(uid);
        }
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        Long userId = user.getUserId();
        DevWorkOrder devWorkOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        if (!devWorkOrder.getWorkorderStatus().equals(WorkConstants.WORK_STATUS_END)) {
            throw new BusinessException("未完成的工单不能提交");
        }
        /**
         * 产线工单
         */
        if (devWorkOrder.getWlSign().equals(WorkConstants.SING_LINE)) {
            ProductionLine productionLine = productionLineMapper.selectProductionLineById(devWorkOrder.getLineId());
            // 不是工单负责人
            if (!productionLine.getDeviceLiable().equals(userId.intValue()) &&
                    !productionLine.getDeviceLiableTow().equals(userId.intValue()) &&
                    !user.getLoginName().equals("admin")) {
                throw new BusinessException("不是工单负责人");
            }
        }
        if (devWorkOrder.getWorkSign().equals(WorkConstants.WORK_SIGN_YES)) {
            throw new BusinessException("该工单已经提交过，不能重复提交");
        }
        // 设置状态为已确认数据不可进行修改和删除
        devWorkOrder.setWorkSign(WorkConstants.WORK_SIGN_YES);
        devWorkOrder.setUpdateTime(new Date());
        devWorkOrder.setUpdateBy(user.getUserName());
        return devWorkOrderMapper.updateDevWorkOrder(devWorkOrder);
    }

    /**
     * 查询生产状态处于正在进行的所有工单
     *
     * @return
     */
    public List<DevWorkOrder> selectWorkOrderAllBeIn(Cookie[] cookies) {
        Integer companyId = JwtUtil.getTokenCookie(cookies).getCompanyId();
        return devWorkOrderMapper.selectWorkOrderAllBeIn(companyId);
    }

    /**
     * 查询当天所有工单
     *
     * @param wlSign 工单所在位置 0 流水线、1、车间，2、小组
     * @return 结果
     */
    public List<DevWorkOrder> selectWorkOrderAllToday(int wlSign) {
        User user = JwtUtil.getUser();
        List<DevWorkOrder> workOrders = devWorkOrderMapper.selectWorkOrderAllToday(user.getCompanyId(),wlSign);
        if (WorkConstants.SING_GROUP != wlSign) {
            getLineOrHouseName(workOrders, user);
            return workOrders;
        } else {
            return workOrders;
        }
    }

    /**
     * 通过产线Id查询该产线正在生产的工单
     *
     * @param lineId
     * @return
     */
    @Override
    public DevWorkOrder selectWorkOrderBeInByLineId(Integer lineId) {
        return devWorkOrderMapper.selectWorkByCompandAndLine(JwtUtil.getUser().getCompanyId(), lineId, WorkConstants.SING_LINE);
    }

    /**
     * 根据工单编号查询对应的工单信息
     *
     * @param work_id 工单编号
     * @return
     */
    @Override
    public DevWorkOrder findWorkInfoById(int work_id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return null;
        }
        //查询对应的工单是否存在
        DevWorkOrder order = devWorkOrderMapper.selectDevWorkOrderById(work_id);
        if (order == null || order.getLineId() == null) {
            return null;
        }
        // 查询工单生产的产品信息
        DevProductList product = productListMapper.selectDevProductByCode(user.getCompanyId(), order.getProductCode());
        if (product == null) {
            return null;
        }
        order.setProductStandardHour(PdfUtil.IntegerNull(product.getStandardHourYield()) * order.getPeopleNumber());
        order.setProductUPH(product.getStandardHourYield());
        DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
        if (company == null) {
            return null;
        }
        //查询对应的产线信息
        ProductionLine line = productionLineMapper.selectProductionLineById(order.getLineId());
        if (line == null) return null;
        if (null != line) {
            int lineSign = 0;
            if (user.getUserId().intValue() == line.getDeviceLiable() || user.getUserId().intValue() == line.getDeviceLiableTow()) {
                lineSign = 1;
            }
            order.setLineSign(lineSign);
            order.setParam1(line.getLineName());
        }
        float standardHour = order.getSignHuor();
        //达成率默认为0
        order.setReachRate(0.0F);
        if (order.getWorkorderStatus() == WorkConstants.WORK_STATUS_STARTING && order.getCumulativeNumber() != null) {
            //计数标准产量
            if (order.getOperationStatus() == WorkConstants.OPERATION_STATUS_STARTING) {//工单正在开始中
                standardHour += TimeUtil.getDateDel(order.getSignTime(), new Date());
            }
            int standardTotal = (int) (order.getProductStandardHour() * standardHour);
            order.setReachRate(standardTotal == 0 ? 0.0F : new BigDecimal(((float) order.getCumulativeNumber() / ((float) standardTotal)) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        }
        order.setSignHuor(standardHour);
        return order;
    }

    /**
     * 通过产线id查询已经提交的工单列表
     *
     * @param lineId 产线
     * @return 结果
     */
    @Override
    public List<DevWorkOrder> selectWorkOrderFinishByLineId(int lineId, HttpServletRequest request) {
        List<DevWorkOrder> workOrders = devWorkOrderMapper.selectWorkOrderFinishByLineIdOrWorkOrderId(JwtUtil.getTokenUser(request).getCompanyId(), lineId, null);
        for (DevWorkOrder workOrder : workOrders) {
            DevProductList productList = productListMapper.selectDevProductByCode(JwtUtil.getTokenUser(request).getCompanyId(), workOrder.getProductCode());
            workOrder.setProductId(productList.getId());
            workOrder.setProductModel(productList.getProductModel());
        }
        return workOrders;
    }

    /**
     * 根据产线id或工单id查询工单信息
     *
     * @param lineId
     * @param workOrderId
     * @return 结果
     */
    @Override
    public DevWorkOrder selectWorkOrderFinishByLineIdAndWorkOrderId(int lineId, int workOrderId, HttpServletRequest request) {
        return devWorkOrderMapper.selectWorkOrderFinishByLineIdOrWorkOrderId(JwtUtil.getTokenUser(request).getCompanyId(), lineId, workOrderId).get(0);
    }

    /**
     * 工单变更
     *
     * @param order
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeOrder(DevWorkOrder order) {
        if (order == null) return 0;
        User user = JwtUtil.getTokenUser(ServletUtils.getRequest());
        if (user == null) return 0;
        DevWorkOrder devWorkOrder = devWorkOrderMapper.selectDevWorkOrderById(order.getId());
        if (devWorkOrder == null) return 0;
        WorkOrderChange change = new WorkOrderChange();
        change.setCompanyId(user.getCompanyId());
        change.setOrderId(devWorkOrder.getId());
        change.setOrderCode(devWorkOrder.getWorkorderNumber());
        change.setOrderCodeInfo(devWorkOrder.getOrderCode());
        if (order.getWlSign() == WorkConstants.SING_LINE) {//产线
            //查询对应的产线信息
            ProductionLine line = productionLineMapper.selectProductionLineById(order.getLineId());
            if (line == null) return 0;
            //保存变更记录
            change.setLineId(line.getId());
            change.setLineName(line.getLineName());
            User u1 = userMapper.selectUserInfoById(line.getDeviceLiable());
            change.setDeviceLiable(u1 == null ? "" : u1.getUserName());
            u1 = userMapper.selectUserInfoById(line.getDeviceLiableTow());
            change.setDeviceLiable2(u1 == null ? "" : u1.getUserName());
        } else if (order.getWlSign() == WorkConstants.SING_SINGLE) {
            /**
             * 查询该工单是否已经配置过单工位
             */
            List<SingleWorkOrder> singleWorkOrders = singleWorkOrderMapper.selectSingleWorkByWorkIdAndPid(order.getSingle(), order.getId());
            if (StringUtils.isNotEmpty(singleWorkOrders)) {
                throw new BusinessException("该工单已分配单工位不允许变更");
            }
            SingleWork work = singleWorkMapper.selectSingleWorkById(order.getSingle());
            if (work == null) return 0;
            //保存变更记录
            change.setLineId(work.getId());
            change.setLineName(work.getWorkshopName());
            User u1 = userMapper.selectUserInfoById(work.getLiableOne());
            change.setDeviceLiable(u1 == null ? "" : u1.getUserName());
            u1 = userMapper.selectUserInfoById(work.getLiableTwo());
            change.setDeviceLiable2(u1 == null ? "" : u1.getUserName());
            change.setWorkPrice(devWorkOrder.getWorkPrice());
            order.setLineId(order.getSingle());
        } else {
            return 0;
        }
        change.setProductNumber(devWorkOrder.getProductNumber());
        change.setProductionStart(devWorkOrder.getProductionStart());
        change.setProductionEnd(devWorkOrder.getProductionEnd());
        change.setCreatePeople(user.getUserName());
        change.setCreateTime(new Date());
        change.setRemark(order.getRemark());
        workOrderChangeMapper.insertWorkOrderChange(change);
        return devWorkOrderMapper.updateDevWorkOrder(order);
    }

    /**
     * 根据工单id查询对应的ECN信息
     *
     * @param workId 工单id
     * @return
     */
    @Override
    public DevWorkOrder selectWorkOrderEcn(int workId) {
        DevWorkOrder order = devWorkOrderMapper.selectDevWorkOrderById(workId);
        return order;
    }

    /**
     * 工单合并验证，合并的前提是 所以工单是未进行工单并且是同一产品
     *
     * @param workIds 需合并工单id
     *                * @param type 0、合单 1、拆单
     * @return
     * @throws Exception
     */
    @Override
    public String workMergeVerif(int[] workIds, int type) throws Exception {
        int failNum = 0;//统计错误次数
        StringBuilder failMsg = new StringBuilder();//错误提示信息
        StringBuilder successMsg = new StringBuilder();
        if (workIds == null) {
            failMsg.insert(0, "很抱歉，您没有选中需要操作的工单");
            throw new Exception(failMsg.toString());
        }
        if (type == 1 && workIds.length != 1) {
            failMsg.insert(0, "很抱歉，拆除的工单操作，需要选择一条工单信息");
            throw new Exception(failMsg.toString());
        }
        if (type == 0 && workIds.length <= 1) {
            failMsg.insert(0, "很抱歉，合并的工单操作，工单条数至少2条以上");
            throw new Exception(failMsg.toString());
        }
        String pCode = null;//用于记录第一次查询出来的工单不为空的产品编码
        int index = 0;//用于记录循环次数
        DevWorkOrder order = null;
        for (int workId : workIds) {
            index++;
            //根据id查询对应工单信息
            order = devWorkOrderMapper.selectDevWorkOrderById(workId);
            if (order == null) {
                failNum++;
                failMsg.append("<br/>很抱歉，您选中的第" + (index) + "工单信息不存在");
                continue;
            }
            if (order.getAbolish() == 1) {//工单已经作废
                failNum++;
                failMsg.append("<br/>很抱歉，您选中的第" + (index) + "工单信息已经作废，不能进行操作");
                continue;
            }
            if (StringUtils.isEmpty(pCode)) {
                pCode = order.getProductCode();
            }
            //判断工单是否是未进行
            if (order.getWorkorderStatus() != WorkConstants.WORK_STATUS_NOSTART) {
                failNum++;
                failMsg.append("<br/>很抱歉，请选择工单状态为未进行状态，您选中的第" + (index) + "工单状态不是未进行状态");
                continue;
            }
            //判断是否是同一产品
            if (!pCode.equals(order.getProductCode())) {
                failNum++;
                failMsg.append("<br/>很抱歉，请选择同一产品的工单，您选中的第" + (index) + "工单所生产的产品与选中的第一条工单所生产的产品不同");
                continue;
            }
        }
        if (failNum > 0) {
            failMsg.insert(0, "很抱歉，操作失败:");
            throw new Exception(failMsg.toString());
        }
        successMsg.insert(0, "验证通过");
        return successMsg.toString();
    }

    /**
     * 初始化合并工单信息
     *
     * @param workIds 工单id
     * @return
     */
    @Override
    public Map<String, Object> workMergePage(String workIds) {
        User u = JwtUtil.getTokenUser(ServletUtils.getRequest());
        Map<String, Object> map = new HashMap<>();
        String[] ids = workIds.split(",");
        DevWorkOrder order = null;
        int num = 0;
        for (String id : ids) {
            //查询工单
            order = devWorkOrderMapper.selectDevWorkOrderById(Integer.parseInt(id));
            num += order.getProductNumber();
        }
        //查询产品
        DevProductList productList = productListMapper.selectDevProductByCode(u.getCompanyId(), order.getProductCode());
        map.put("code", CodeUtils.getWorkOrderCode());
        map.put("num", num);
        map.put("product", productList);
        return map;
    }

    /**
     * 合并工单信息
     *
     * @param order 工单信息
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int workMerge(DevWorkOrder order) throws Exception {
        User u = JwtUtil.getTokenUser(ServletUtils.getRequest());
        ProductionLine line = null;
        StringBuilder hCode = new StringBuilder();
        WorkOrderChange change = null;
        String ids[] = order.getParam1().split(",");
        for (String id : ids) {
            //查询工单信息
            DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(Integer.parseInt(id));
            hCode.append(workOrder.getWorkorderNumber() + "<br/>");
            devWorkOrderMapper.updateWorkOrderAbolish(workOrder.getId());
            line = productionLineMapper.selectProductionLineById(workOrder.getLineId());
            //添加工单变更记录
            change = new WorkOrderChange();
            change.setCompanyId(u.getCompanyId());
            change.setOrderId(workOrder.getId());
            change.setOrderCode(workOrder.getOrderCode());
            change.setLineId(workOrder.getLineId());
            change.setLineName(line.getLineName());
            change.setDeviceLiable(userMapper.selectUserInfoById(line.getDeviceLiable()).getUserName());
            change.setDeviceLiable2(userMapper.selectUserInfoById(line.getDeviceLiableTow()).getUserName());
            change.setProductionStart(workOrder.getProductionStart());
            change.setProductionEnd(workOrder.getProductionEnd());
            change.setCreatePeople(u.getUserName());
            change.setProductNumber(workOrder.getProductNumber());
            change.setCreateTime(new Date());
            change.setcStatus(1);
            change.setRemark("该工单合并为" + order.getWorkorderNumber() + "工单");
            workOrderChangeMapper.insertWorkOrderChange(change);
        }
        line = productionLineMapper.selectProductionLineById(order.getLineId());
        order.setCompanyId(u.getCompanyId());
        order.setCreateTime(new Date());
        order.setCreateBy(u.getUserName());
        order.setDeviceLiable(line.getDeviceLiable());
        //查询产线
        devWorkOrderMapper.insertDevWorkOrder(order);
        //添加工单变更记录
        change = new WorkOrderChange();
        change.setCompanyId(u.getCompanyId());
        change.setOrderId(order.getId());
        change.setOrderCode(order.getOrderCode());
        change.setLineId(order.getLineId());
        change.setLineName(line.getLineName());
        change.setDeviceLiable(userMapper.selectUserInfoById(line.getDeviceLiable()).getUserName());
        change.setDeviceLiable2(userMapper.selectUserInfoById(line.getDeviceLiableTow()).getUserName());
        change.setProductionStart(order.getProductionStart());
        change.setProductionEnd(order.getProductionEnd());
        change.setCreatePeople(u.getUserName());
        change.setProductNumber(order.getProductNumber());
        change.setCreateTime(new Date());
        change.setcStatus(1);
        change.setRemark("该工单由" + hCode.toString() + "工单合并而成;");
        workOrderChangeMapper.insertWorkOrderChange(change);

        DevProductList devProductList = productListMapper.selectDevProductByCode(u.getCompanyId(), order.getProductCode());
        if (order.getEcnStatus() == 1) {//添加ecn备注信息
            // 新增工单变更记录
            EcnLog ecnLog = new EcnLog();
            ecnLog.setCompanyId(u.getCompanyId());
            ecnLog.setSaveId(order.getId());
            ecnLog.setSaveCode(order.getWorkorderNumber());
            ecnLog.setEcnType(2);
            ecnLog.setEcnText(order.getEcnText());
            ecnLog.setCreatePeople(order.getCreateBy());
            ecnLog.setCreateId(u.getUserId().intValue());
            ecnLog.setCreateTime(new Date());
            ecnLogMapper.insertEcnLog(ecnLog);
            // 更新产品ECN备注信息
            if (!devProductList.getEcnText().equals(order.getEcnText())) { // 新建工单时更改了产品ECN
                // 更新ECN日志
                EcnLog proEcnLog = new EcnLog();
                proEcnLog.setCompanyId(u.getCompanyId());
                proEcnLog.setSaveId(devProductList.getId());
                proEcnLog.setSaveCode(devProductList.getProductCode());
                proEcnLog.setEcnType(1);
                proEcnLog.setEcnText(order.getEcnText());
                proEcnLog.setCreatePeople(order.getCreateBy());
                proEcnLog.setCreateId(u.getUserId().intValue());
                proEcnLog.setCreateTime(new Date());
                ecnLogMapper.insertEcnLog(proEcnLog);
                // 更新产品ECN
                devProductList.setEcnText(order.getEcnText());
                productListMapper.updateDevProductList(devProductList);
            }
        }
        return 1;
    }

    /**
     * 拆分工单
     *
     * @param orders 拆单详情
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int workDismantleInfo(List<DevWorkOrder> orders) throws Exception {
        User u = JwtUtil.getTokenUser(ServletUtils.getRequest());
        StringBuilder code = new StringBuilder();
        //获取原始工单
        if (orders == null) return 0;
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(orders.get(0).getSign());
        ProductionLine line = null;
        DevProductList devProductList = null;
        WorkOrderChange change = null;
        for (DevWorkOrder order : orders) {
            order.setWorkorderNumber(CodeUtils.getWorkOrderCode());
            try {
                Thread.sleep(1100);
            } catch (Exception e) {
            }
            order.setCompanyId(u.getCompanyId());
            order.setSign(1);
            line = productionLineMapper.selectProductionLineById(order.getLineId());
            order.setDeviceLiable(line.getDeviceLiable());
            order.setCreateTime(new Date());
            order.setCreateBy(u.getUserName());
            devWorkOrderMapper.insertDevWorkOrder(order);
            code.append("<br/>" + order.getWorkorderNumber());
            //添加工单变更记录
            change = new WorkOrderChange();
            change.setCompanyId(u.getCompanyId());
            change.setOrderId(order.getId());
            change.setOrderCode(order.getOrderCode());
            change.setLineId(order.getLineId());
            change.setLineName(line.getLineName());
            change.setDeviceLiable(userMapper.selectUserInfoById(line.getDeviceLiable()).getUserName());
            change.setDeviceLiable2(userMapper.selectUserInfoById(line.getDeviceLiableTow()).getUserName());
            change.setProductionStart(order.getProductionStart());
            change.setProductionEnd(order.getProductionEnd());
            change.setCreatePeople(u.getUserName());
            change.setProductNumber(order.getProductNumber());
            change.setCreateTime(new Date());
            change.setcStatus(2);
            change.setRemark("该工单由" + workOrder.getWorkorderNumber() + "工单拆分而成;");
            workOrderChangeMapper.insertWorkOrderChange(change);
            if (order.getEcnStatus() == 1) {//添加ecn备注信息
                devProductList = productListMapper.selectDevProductByCode(u.getCompanyId(), order.getProductCode());
                // 新增工单变更记录
                EcnLog ecnLog = new EcnLog();
                ecnLog.setCompanyId(u.getCompanyId());
                ecnLog.setSaveId(order.getId());
                ecnLog.setSaveCode(order.getWorkorderNumber());
                ecnLog.setEcnType(2);
                ecnLog.setEcnText(order.getEcnText());
                ecnLog.setCreatePeople(order.getCreateBy());
                ecnLog.setCreateId(u.getUserId().intValue());
                ecnLog.setCreateTime(new Date());
                ecnLogMapper.insertEcnLog(ecnLog);
                // 更新产品ECN备注信息
                if (!devProductList.getEcnText().equals(order.getEcnText())) { // 新建工单时更改了产品ECN
                    // 更新ECN日志
                    EcnLog proEcnLog = new EcnLog();
                    proEcnLog.setCompanyId(u.getCompanyId());
                    proEcnLog.setSaveId(devProductList.getId());
                    proEcnLog.setSaveCode(devProductList.getProductCode());
                    proEcnLog.setEcnType(1);
                    proEcnLog.setEcnText(order.getEcnText());
                    proEcnLog.setCreatePeople(order.getCreateBy());
                    proEcnLog.setCreateId(u.getUserId().intValue());
                    proEcnLog.setCreateTime(new Date());
                    ecnLogMapper.insertEcnLog(proEcnLog);
                    // 更新产品ECN
                    devProductList.setEcnText(order.getEcnText());
                    productListMapper.updateDevProductList(devProductList);
                }
            }
        }
        //作废工单
        devWorkOrderMapper.updateWorkOrderAbolish(workOrder.getId());
        line = productionLineMapper.selectProductionLineById(workOrder.getLineId());
        //添加工单变更记录
        change = new WorkOrderChange();
        change.setCompanyId(u.getCompanyId());
        change.setOrderId(workOrder.getId());
        change.setOrderCode(workOrder.getOrderCode());
        change.setLineId(workOrder.getLineId());
        change.setLineName(line.getLineName());
        change.setDeviceLiable(userMapper.selectUserInfoById(line.getDeviceLiable()).getUserName());
        change.setDeviceLiable2(userMapper.selectUserInfoById(line.getDeviceLiableTow()).getUserName());
        change.setProductionStart(workOrder.getProductionStart());
        change.setProductionEnd(workOrder.getProductionEnd());
        change.setCreatePeople(u.getUserName());
        change.setProductNumber(workOrder.getProductNumber());
        change.setCreateTime(new Date());
        change.setcStatus(2);
        change.setRemark("该工单拆分为:" + code.toString() + "多个工单");
        workOrderChangeMapper.insertWorkOrderChange(change);
        return 1;
    }

    /**
     * 查询所有下到车间的工单信息
     *
     * @return 结果
     */
    @Override
    public List<DevWorkOrder> selectWorkListInSw(Integer wlSign) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return devWorkOrderMapper.selectWorkListInSw(user.getCompanyId(), wlSign);
    }

    /**
     * 通过工作的状态查询所有的工单信息
     *
     * @param workOrderStatus 工作进行状态
     * @return 结果
     */
    @Override
    public List<DevWorkOrder> selectWorkListInWorkStatus(Integer workOrderStatus) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        List<DevWorkOrder> workOrders = devWorkOrderMapper.selectWorkListInWorkStatus(user.getCompanyId(), workOrderStatus);

        return devWorkOrderMapper.selectWorkListInWorkStatus(user.getCompanyId(), workOrderStatus);
    }

    /**
     * 查询工单未配置的未确认数据的工单信息
     *
     * @param lineId     车间id
     * @param workStatus 工单状态
     * @param wlSign     工单下到车间标记
     * @param singleId   单工位id
     * @param companyId  公司id
     * @return 结果过
     */
    @Override
    public List<DevWorkOrder> selectAllNotConfigBySwId(Integer lineId, Integer workStatus, Integer wlSign, Integer singleId, Integer companyId) {
        return devWorkOrderMapper.selectAllNotConfigBySwId(lineId, workStatus, wlSign, singleId, companyId);
    }

    /**
     * OCR 图片解析
     *
     * @param file 图片
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> ocrFile(MultipartFile file) throws Exception {
        User user = JwtUtil.getTokenUser(ServletUtils.getRequest());
        DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
        if (company == null) throw new Exception("系统异常");
        ImportConfig config = configMapper.selectImportConfigByType(user.getCompanyId(), 3);
        if (config == null || StringUtils.isEmpty(config.getSecretKey()) ||
                StringUtils.isEmpty(config.getAppId()) || StringUtils.isEmpty(config.getApiKey())) {
            throw new Exception("无APPKEY配置，请先初始化APPKEY配置");
        }
        Map<String, Object> map = new HashMap<>();
        File f = new File(RuoYiConfig.getProfile() + "/" + company.getTotalIso());
        if ((f.exists() && !f.isDirectory()) || !f.exists()) {
            f.mkdir();
        }
        String path = RuoYiConfig.getProfile() + "/" + company.getTotalIso() + "/ocr/";
        f = new File(path);
        if ((f.exists() && !f.isDirectory()) || !f.exists()) {
            f.mkdir();
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        String filePath = path + fileName;
        f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
        file.transferTo(f);
        List<Ocr> ocrs = null;
        try {
            //进行图片解析
            AipOcr client = new AipOcr(config.getAppId(), config.getApiKey(), config.getSecretKey());
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            JSONObject res = client.basicGeneral(filePath, new HashMap<String, String>());
            ocrs = JSON.parseArray(res.get("words_result").toString(), Ocr.class);
        } catch (Exception e) {
            throw new Exception("解析异常,请确认APP_ID,API_KEY,SECRET_KEY是否正确");
        }
        if (ocrs == null && ocrs.size() > 0) {
            throw new Exception("解决异常，请确认图片所包含内容是否是需要解决的工单信息");
        }
        //匹配解决结果
        if (config.getcSign() == 1) {
            DevWorkOrder order = new DevWorkOrder();
            //工单号
            if (config.getCon1() != null && config.getCon1() > 0) {
                order.setWorkorderNumber(ocrs.get(config.getCon1() - 1).getWords());
                String name = order.getWorkorderNumber() + ".jpg";
                File code = new File(path + name);
                FileUtils.copyFile(f, code);
                f.delete();
                map.put("path", fileUrl + company.getTotalIso() + "/ocr/" + name);
            }
            //订单号
            if (config.getCon2() != null && config.getCon2() > 0) {
                order.setOrderCode(ocrs.get(config.getCon2() - 1).getWords());
            }
            //产线/车间
            if (config.getCon3() != null && config.getCon3() > 0) {
                order.setParam1(ocrs.get(config.getCon3() - 1).getWords());
            }
            //编码
            if (config.getCon4() != null && config.getCon4() > 0) {
                order.setProductCode(ocrs.get(config.getCon4() - 1).getWords());
            }
            //生产数量
            if (config.getPrice() != null && config.getPrice() > 0) {
                order.setParam2(ocrs.get(config.getPrice() - 1).getWords());
            }
            //工价
//            if(config.getCon5() != null && config.getCon5() >0){
//                order.setParam3(ocrs.get(config.getCon5() -1).getWords());
//            }
            map.put("order", order);
        }
        map.put("ocr", ocrs);
        return map;
    }

    @Value("${baidu.appId}")
    private String appId;

    @Value("${baidu.appKey}")
    private String appKey;

    @Value("${baidu.secretKey}")
    private String secretKey;

    /**
     * 初始化OCR
     *
     * @return
     */
    @Override
    @Transactional
    public int initOcrConfig() {
        User user = JwtUtil.getTokenUser(ServletUtils.getRequest());
        ImportConfig config = configMapper.selectImportConfigByType(user.getCompanyId(), 3);
        if (config != null) {
            configMapper.deleteImportConfigByType(user.getCompanyId(), 3);
        }
        config = new ImportConfig();
        config.setcSign(0);
        config.setcType(3);
        config.setAppId(appId);
        config.setApiKey(appKey);
        config.setSecretKey(secretKey);
        config.setcTime(new Date());
        config.setCompanyId(user.getCompanyId());
        return configMapper.insertImportConfig(config);
    }

    /**
     * 保存匹配配置
     *
     * @param config 匹配配置
     * @return
     */
    @Override
    public int saveInitOcrConfig(ImportConfig config) {
        User user = JwtUtil.getTokenUser(ServletUtils.getRequest());
        ImportConfig config1 = configMapper.selectImportConfigByType(user.getCompanyId(), 3);
        config.setAppId(appId);
        config.setApiKey(appKey);
        config.setSecretKey(secretKey);
        if (config1 != null) {
            config.setAppId(config1.getAppId());
            config.setApiKey(config1.getApiKey());
            config.setSecretKey(config1.getSecretKey());
            configMapper.deleteImportConfigByType(user.getCompanyId(), 3);
        }
        config.setcSign(1);
        config.setcType(3);
        config.setcTime(new Date());
        config.setCompanyId(user.getCompanyId());
        return configMapper.insertImportConfig(config);
    }

    /**
     * 保存OCR 解析的工单信息
     *
     * @param order 工单信息
     * @return
     * @throws Exception
     */
    @Override
    public int saveOcrWork(DevWorkOrder order) throws Exception {
        User u = JwtUtil.getTokenUser(ServletUtils.getRequest());
        if (order == null || u == null) return 0;
        order.setCompanyId(u.getCompanyId().intValue());
        //判断对应产线是否存在
        if (order.getWlSign() == 0) {
            ProductionLine line = productionLineMapper.selectProductionLineByName(u.getCompanyId(), order.getParam1());
            if (line == null)
                throw new Exception("对应产线不存在");
            order.setLineId(line.getId());
            order.setDeviceLiable(line.getDeviceLiable());
        }
        //判断车间是否存在
        if (order.getWlSign() == 1) {
            SingleWork work = singleWorkMapper.selectSingleWorkByWorkshopName(u.getCompanyId(), order.getParam1());
            if (work == null) throw new Exception("对应车间不存在");
            order.setLineId(work.getId());
        }
        //查询对应产品是否存在
        DevProductList productList = productListMapper.selectDevProductByCode(u.getCompanyId(), order.getProductCode());
        if (productList == null) throw new Exception("对应产品/半成品不存在");
        order.setProductCode(productList.getProductCode());
        order.setProductModel(productList.getProductModel());
        order.setProductName(productList.getProductName());
        order.setProductionStart(new Date());
        order.setProductionEnd(new Date());
        order.setProductStandardHour(productList.getStandardHourYield());
        order.setCreateTime(new Date());
        order.setMakeType(productList.getSign());
        //生产数量
        try {
            order.setProductNumber(Integer.parseInt(order.getParam2()));
        } catch (Exception e) {
            throw new Exception("生产数量错误，确保生产数量为数字");
        }
        //工价
//        try {
//            order.setWorkPrice(Float.parseFloat(order.getParam3()));
//        }catch (Exception e){
//            throw new Exception("工价错误，确保工价为数字");
//        }
        order.setCreateBy(u.getUserName());
        //查询对应工单是否存在
        DevWorkOrder workOrder = devWorkOrderMapper.checkWorkOrderNumber(order.getWorkorderNumber(), u.getCompanyId());
        if (workOrder != null) throw new Exception("工单已存在");
        return devWorkOrderMapper.insertDevWorkOrder(order);
    }


    /******************************************************************************************************
     *********************************** 工单MES操作逻辑 ***************************************************
     ******************************************************************************************************/
    /**
     * 初始化配置查询MES工单相关信息
     *
     * @param id 工单id
     * @return 结果
     */
    @Override
    public DevWorkOrder selectWorkOrderMesByWId(int id) {
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        if (StringUtils.isNotNull(workOrder)) {
            // 查询工单产品信息
            DevProductList product = productListMapper.selectProductByCode(workOrder.getProductCode());
            // 查询规则是否有效
            MesBatchRule mesRule = mesBatchRuleMapper.selectMesBatchRuleById(product.getRuleId());
            if (StringUtils.isNotNull(mesRule) && mesRule.getStatus() != null) {
                workOrder.setRuleStatus(mesRule.getStatus());
            }
            workOrder.setRuleId(product.getRuleId());
            // 查询已经配置的工单批次追踪信息
            List<MesBatch> mesBatchList = mesBatchMapper.selectMesBatchListByWorkCode(JwtUtil.getUser().getCompanyId(), workOrder.getWorkorderNumber());
            if (StringUtils.isNotEmpty(mesBatchList)) {
                List<MesBatchDetail> mesBatchDetailList = null;
                for (MesBatch mesBatch : mesBatchList) {
                    mesBatchDetailList = mesBatchDetailMapper.selectMesBatchDetailByBId(mesBatch.getId());
                    mesBatch.setMesBatchDetailList(mesBatchDetailList);
                }
                workOrder.setMesBatchList(mesBatchList);
            } else {
                // 查询生产产品的MES规则
                if (StringUtils.isNotNull(mesRule)) {
                    List<MesBatchRuleDetail> ruleDetailList = mesBatchRuleDetailMapper.selectMesBatchRuleDetailByRuleId(mesRule.getId());
                    workOrder.setMesRuleDetailList(ruleDetailList);
                }
            }
            return workOrder;
        }
        return null;
    }

    /**
     * 生产配置MES查询MES相关数据
     *
     * @param id 工单id
     * @return 结果
     */
    @Override
    public DevWorkOrder selectWorkMesOrderByWorkId(int id) {
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        if (StringUtils.isNotNull(workOrder)) {
            // 查询工单产品信息
            DevProductList product = productListMapper.selectProductByCode(workOrder.getProductCode());
            if (product != null) {
                // 查询规则是否有效
                MesBatchRule mesRule = mesBatchRuleMapper.selectMesBatchRuleById(product.getRuleId());
                if (StringUtils.isNotNull(mesRule) && mesRule.getStatus() != null) {
                    workOrder.setRuleStatus(mesRule.getStatus());
                }
            }
            // 查询MES数据
            List<MesBatch> mesBatchList = mesBatchMapper.selectMesBatchListByWorkCode(JwtUtil.getUser().getCompanyId(), workOrder.getWorkorderNumber());
            if (StringUtils.isNotEmpty(mesBatchList)) {
                List<MesBatchDetail> mesBatchDetailList = null;
                for (MesBatch mesBatch : mesBatchList) {
                    mesBatchDetailList = mesBatchDetailMapper.selectMesBatchDetailByBId(mesBatch.getId());
                    mesBatch.setMesBatchDetailList(mesBatchDetailList);
                }
                workOrder.setMesBatchList(mesBatchList);
            }
            return workOrder;
        }
        return null;
    }

    /**
     * 删除工单信息
     */
    @Override
    public int deleteDevWorkOrderById(Integer id) {
        User user = JwtUtil.getUser();
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(id);
        if (workOrder == null) {
            throw new BusinessException("工单不存在或者已经删除");
        }
        if (!workOrder.getWorkorderStatus().equals(WorkConstants.WORK_STATUS_NOSTART)) {
            throw new BusinessException("已经开始或结束的工单不能删除");
        }
        return devWorkOrderMapper.deleteDevWorkOrderById(id);
    }


    /***************************消息推送开始**********************************/

    @Value("${jpush.mastersecret}")
    private String MASTER_SECRET;

    @Value("${jpush.appkey}")
    private String APP_KEY;

    @Autowired
    private JpushInfoMapper jpushInfoMapper;

    @Autowired
    private WorkstationMapper workstationMapper;

    /**
     * 推送生产看板
     *
     * @param company
     */
    private void JPushWatchMsg(DevCompany company) {
        List<String> alias = jpushInfoMapper.selectJPushInfoList(company.getLoginNumber());
        JSONObject data = new JSONObject();
        data.put("msg", "1");
        //进行消息推送生产看板
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(data.toString()))
                .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            LOGGER.error("消息推送出现异常：" + e.getMessage());
            // e.printStackTrace();
        } catch (APIRequestException e) {
            LOGGER.error("消息推送出现异常：" + e.getMessage());
            // e.printStackTrace();
        }
    }

    /**
     * 推送ASOP
     *
     * @param order
     */
    private void JPushMsg(DevWorkOrder order) {
        if (order == null) {
            return;
        }
        List<String> alias = null;
        ProductionLine line = productionLineMapper.selectProductionLineById(order.getLineId());
        //2、查询对应产线所有配置SOP看板硬件的硬件编码
        if (line != null) {
            alias = workstationMapper.countLineKBCode(line.getCompanyId(), line.getId());
        }
        if (alias == null || alias.size() <= 0) {
            return;
        }
        JSONObject data = new JSONObject();
        data.put("msg", "1");
        //进行消息推送
        JPushClient jpushClient = new JPushClient("54c72e81c46f579783b64aec", "730860b2325df5af2335ee1c", null, ClientConfig.getInstance());
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(data.toString()))
                .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

    }

    /***************************消息推送结束**********************************/

    /**
     * 设置工单相关信息
     *
     * @param workOrder
     * @param user
     * @param product
     */
    private void setWorkInfo(DevWorkOrder workOrder, User user, DevProductList product) {
        // 制作类型
        workOrder.setMakeType(product.getSign());
        // 设置工单产品的名称
        workOrder.setProductName(product.getProductName());
        // 设置工单产品编码
        workOrder.setProductCode(product.getProductCode());
        // 设置工价
        workOrder.setWorkPrice(product.getLabourPrice());
        //产品型号
        workOrder.setProductModel(product.getProductModel());
        // 设置工单属于哪个公司
        workOrder.setCompanyId(user.getCompanyId());
        // 创建者
        workOrder.setCreateBy(user.getUserName());
    }

    /******************************************************************************************************
     *********************************** APP工单操作业务逻辑 ***********************************************
     ******************************************************************************************************/


    /**
     * app端新增工单
     *
     * @param workOrder 工单信息
     * @return 结果
     */
    @Override
    public int appSaveWorkOrder(DevWorkOrder workOrder){
        User user = JwtUtil.getUser();
        if (user == null) {
            return 0;
        }
        // 验证产品是否存在
        DevProductList product = productListMapper.selectDevProductByCode(user.getCompanyId(), workOrder.getProductCode());
        if (product == null) {
            throw new BusinessException("产品不存在或被删除");
        }
        setWorkInfo(workOrder, user, product);
        // 工单为单工位工单
        workOrder.setWlSign(WorkConstants.SING_SINGLE);
        return devWorkOrderMapper.insertDevWorkOrder(workOrder);
    }


    /**
     * 查询两条今日工单信息
     * @return 工单列表
     */
    @Override
    public List<DevWorkOrder> appSelectWorkListTwo() {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        List<DevWorkOrder> workOrders = devWorkOrderMapper.selectWorkOrderAllToday(user.getCompanyId(),WorkConstants.SING_SINGLE);
        return workOrders;
    }

    /**
     * app端修改工单信息
     *
     * @param devWorkOrder 工单信息
     * @return 结果
     */
    @Override
    public int appEditWorkInfo(DevWorkOrder devWorkOrder) {
        Integer userId = devWorkOrder.getUid();
        if (userId == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        User user = userMapper.selectUserInfoById(userId);
        if (user == null) {
            throw new BusinessException(UserConstants.NOT_LOGIN);
        }
        DevWorkOrder workOrder = devWorkOrderMapper.selectDevWorkOrderById(devWorkOrder.getId());
        if (workOrder == null) {
            throw new BusinessException("工单不存在或已经删除");
        }
        if (workOrder.getWorkSign().equals(WorkConstants.WORK_SIGN_YES)) {
            throw new BusinessException("已经提交数据的工单不能进行修改");
        }
        int one = 0;
        int tow = 0;
        if (workOrder.getWlSign() == 0) {
            ProductionLine productionLine = productionLineMapper.selectProductionLineById(workOrder.getLineId());
            one = productionLine.getDeviceLiable();
            tow = productionLine.getDeviceLiableTow();
        } else if (workOrder.getWlSign() == 1) {
            /**
             * 判断修改的工单车间是否已经分配了单工位
             */
            if (workOrder.getWorkorderStatus().equals(WorkConstants.WORK_STATUS_NOSTART)) {
                List<SingleWorkOrder> singleWorkOrders = singleWorkOrderMapper.selectSingleWorkByWorkIdAndPid(workOrder.getLineId(), workOrder.getId());
                if (StringUtils.isNotEmpty(singleWorkOrders)) {
                    throw new BusinessException("该工单已分配单工位不能修改车间");
                }
            }
            SingleWork work = singleWorkMapper.selectSingleWorkById(workOrder.getLineId());
            one = work.getLiableOne();
            tow = work.getLiableTwo();
        }
        // 不是工单负责人
        if (one != userId.intValue() && tow != userId.intValue()) {
            throw new BusinessException("不是工单负责人");
        }
        return devWorkOrderMapper.updateDevWorkOrder(devWorkOrder);
    }

    /**
     * app查工单信息
     */
    private void getLineOrHouseName(List<DevWorkOrder> workOrders, User user) {
        ProductionLine line = null;
        SingleWork house = null;
        int lineSign = 0;
        for (DevWorkOrder workOrder : workOrders) {
            if (workOrder.getWlSign() == 0) {
                line = productionLineMapper.selectProductionLineById(workOrder.getLineId());
                if (null != line) {
                    if (user.getUserId().intValue() == line.getDeviceLiable() || user.getUserId().intValue() == line.getDeviceLiableTow()) {
                        lineSign = 1;
                    }
                    workOrder.setLineSign(lineSign);
                    workOrder.setParam1(line.getLineName());
                }
            } else if (workOrder.getWlSign() == 1) {
                house = singleWorkMapper.selectSingleWorkById(workOrder.getLineId());
                if (null != house) {
                    if (house.getLiableOne() == user.getUserId().intValue() || house.getLiableTwo() == user.getUserId().intValue()) {
                        // 有权限可以分配工单操作
                        workOrder.setLineSign(1);
                    }
                    workOrder.setParam1(house.getWorkshopName());
                }
            }
        }
    }

    /**
     * app上报工单异常信息
     *
     * @param index 异常信息
     * @return 结果
     */
    @Override
    public Map<String, Object> appWorkExc(Index index) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                map.put("msg", "用户未登录或登录超时");
                map.put("code", 0);
                return map;
            }
            if (index == null || index.getWorkId() == null || StringUtils.isEmpty(index.getWorkExcTypeName()) || StringUtils.isEmpty(index.getWorkExcInfo())) {
                map.put("msg", "上报参数错误");
                map.put("code", 0);
                return map;
            }
            // 查询对应异常类型是否存在
            WorkExceptionType exceptionType = exceptionTypeMapper.selectByCompanyAndTypeName(user.getCompanyId(), index.getWorkExcTypeName());
            if (exceptionType == null) {
                exceptionType = new WorkExceptionType();
                exceptionType.setCompanyId(user.getCompanyId());
                exceptionType.setTypeName(index.getWorkExcTypeName());
                exceptionType.setDefId(0);
                exceptionType.setCreateTime(new Date());
                exceptionTypeMapper.insertWorkExceptionType(exceptionType);
            }
            // 绑定工单异常记录
            WorkExceptionList exceptionList = new WorkExceptionList();
            exceptionList.setCompanyId(user.getCompanyId());
            exceptionList.setExceStatut(0);
            exceptionList.setExceType(exceptionType.getId());
            exceptionList.setWorkId(index.getWorkId());
            exceptionList.setRemark(index.getWorkExcInfo());
            exceptionList.setCreateTime(new Date());
            workExcMapper.insertWorkExceptionList(exceptionList);
            map.put("msg", "工单异常上报成功");
            map.put("code", 1);
            return map;

        } catch (Exception e) {
        }
        map.put("msg", "上报失败");
        map.put("code", 0);
        return map;
    }

}

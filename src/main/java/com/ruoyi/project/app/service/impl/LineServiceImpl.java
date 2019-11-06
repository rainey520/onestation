package com.ruoyi.project.app.service.impl;

import com.ruoyi.common.constant.*;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.app.domain.LineData;
import com.ruoyi.project.app.service.ILineService;
import com.ruoyi.project.device.api.form.ApiWorkForm;
import com.ruoyi.project.device.api.form.WorkDataForm;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.device.devDeviceCounts.domain.DevDataLog;
import com.ruoyi.project.device.devDeviceCounts.mapper.DevDataLogMapper;
import com.ruoyi.project.device.devList.domain.DevList;
import com.ruoyi.project.device.devList.mapper.DevListMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;
import com.ruoyi.project.production.productionLine.mapper.ProductionLineMapper;
import com.ruoyi.project.production.singleWork.domain.SingleWork;
import com.ruoyi.project.production.singleWork.domain.SingleWorkOrder;
import com.ruoyi.project.production.singleWork.mapper.SingleWorkMapper;
import com.ruoyi.project.production.singleWork.mapper.SingleWorkOrderMapper;
import com.ruoyi.project.production.workstation.mapper.WorkstationMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LineServiceImpl implements ILineService {

    @Autowired
    private ProductionLineMapper lineMapper;

    @Autowired
    private WorkstationMapper workstationMapper;

    @Autowired
    private DevWorkOrderMapper workOrderMapper;

    @Autowired
    private DevListMapper devListMapper;

    @Autowired
    private DevCompanyMapper companyMapper;

    @Autowired
    private DevDataLogMapper devDataLogMapper;

    @Autowired
    private SingleWorkMapper singleWorkMapper;

    @Autowired
    private SingleWorkOrderMapper singleWorkOrderMapper;

    @Override
    public List<ProductionLine> selectAllLine() {
        User user = JwtUtil.getUser();
        //查询对应公司所以的产线信息
        List<ProductionLine> lines = lineMapper.selectAllProductionLineByCompanyId(user.getCompanyId());
        if (lines != null) {
            for (ProductionLine line : lines) {
                //查询对应产线的工位信息
                line.setWorkstations(workstationMapper.selectAllCodeNotNullByLineId(user.getCompanyId(), line.getId()));
                line.setStatus(0);
                //查询正在进行的工单
                DevWorkOrder workOrder = workOrderMapper.selectWorkByCompandAndLine(user.getCompanyId(), line.getId(), WorkConstants.SING_LINE);
                if (workOrder != null) {
                    line.setStatus(1);
                }
            }
            return lines;
        }
        return null;
    }

    /**
     * 拉取所有的产线信息
     *
     * @return 结果
     */
    @Override
    public List<ProductionLine> selectAllLineList() {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return lineMapper.selectAllProductionLineByCompanyId(user.getCompanyId());
    }

    /**
     * 硬件端拉取工单信息
     *
     * @param lineData 上传信息
     * @return 结果
     */
    @Override
    public Map<String, Object> getWorkInfo(LineData lineData) {
        Map<String, Object> map = new HashMap<>(16);
        if (lineData == null || StringUtils.isEmpty(lineData.getJsCode())) {
            map.put("status", 0);
            map.put("msg", "请求参数错误");
            return map;
        }
        // 查询计数器硬件信息
        DevList devInfo = devListMapper.selectDevListByDevId(lineData.getJsCode());
        if (devInfo == null || devInfo.getCompanyId() == null || devInfo.getSign().equals(DevConstants.DEV_SIGN_NOT_USE)) {
            map.put("status", 0);
            map.put("msg", "硬件不存在或未配置");
            return map;
        }
        // 查询公司信息
        DevCompany company = companyMapper.selectDevCompanyById(devInfo.getCompanyId());
        if (company == null) {
            map.put("status", 0);
            map.put("msg", "公司不存在或被删除");
            return map;
        }
        // 查询工位
        SingleWork station = singleWorkMapper.selectSingleWorkByCode(devInfo.getId(), 0, 0);
        if (station == null) {
            map.put("status", 0);
            map.put("msg", "工位不存在或被删除");
            return map;
        }
        SingleWork house = singleWorkMapper.selectSingleWorkById(station.getParentId());
        if (house == null) {
            map.put("status", 0);
            map.put("msg", "车间不存在或被删除");
            return map;
        }
        ApiWorkForm workInfo = new ApiWorkForm();

        // 返回相关工单信息
        workInfo.setCompanyId(company.getCompanyId());
        workInfo.setCompanyName(company.getComName());
        workInfo.setLineId(house.getId());
        workInfo.setLineName(house.getWorkshopName() + "-" + station.getWorkshopName());
        workInfo.setTag(WorkConstants.SING_SINGLE);
        // 查询该产线正在进行的工单信息
        DevWorkOrder order = workOrderMapper.selectWorkInHouseBeInBySingId(devInfo.getCompanyId(), house.getId(), WorkConstants.SING_SINGLE,
                station.getId(), WorkConstants.WORK_STATUS_STARTING);
        if (order == null) {
            map.put("status", 1);
            map.put("data", workInfo);
            map.put("msg", "无进行中工单");
            return map;
        }
        // 设置工单相关信息
        workInfo.setWorkId(order.getId());
        workInfo.setWorkCode(order.getWorkorderNumber());
        workInfo.setProductCode(order.getProductCode());
        // 工单实际产量
        workInfo.setActualNum(order.getCumulativeNumber());
        workInfo.setProductName(order.getProductName());
        workInfo.setWorkNumber(order.getProductNumber());
        workInfo.setWorkorderStatus(order.getOperationStatus());
        map.put("status", 2);
        map.put("data", workInfo);
        map.put("msg", "请求成功");
        return map;
    }

    /**
     * 计数器上传工单数据逻辑操作
     *
     * @param uploadInfo 上传信息
     * @return 结果
     */
    @Override
    public Map<String, Object> uploadWorkInfo(WorkDataForm uploadInfo) {
        Map<String, Object> map = new HashMap<>(16);
        if (uploadInfo == null || StringUtils.isEmpty(uploadInfo.getCode())) {
            map.put("status", 0);
            map.put("msg", "请求参数错误");
            return map;
        }
        // 查询计数器硬件信息
        DevList devInfo = devListMapper.selectDevListByDevId(uploadInfo.getCode());
        if (devInfo == null || devInfo.getCompanyId() == null || devInfo.getSign().equals(DevConstants.DEV_SIGN_NOT_USE)) {
            map.put("code", 0);
            map.put("msg", "硬件不存在或未配置");
            return map;
        }
        // 查询公司信息
        DevCompany company = companyMapper.selectDevCompanyById(devInfo.getCompanyId());
        if (company == null) {
            map.put("code", 0);
            map.put("msg", "公司不存在或被删除");
            return map;
        }
        // 查询产线信息
        ProductionLine line = lineMapper.selectProductionLineById(devInfo.getLineId());
        if (line == null || CompanyConstants.LINE_DELETED.equals(line.getDefId())) {
            map.put("code", 0);
            map.put("msg", "产线不存在或被删除");
            return map;
        }
        // 上传日志记录
        DevDataLog devDataLog = new DevDataLog();
        devDataLog.setCompanyId(devInfo.getCompanyId());
        devDataLog.setDataTotal(uploadInfo.getD1());
        devDataLog.setDevId(devInfo.getId());
        devDataLog.setDelData(0);
        devDataLog.setScType(WorkConstants.SING_LINE);
        devDataLog.setLineId(devInfo.getLineId());

        // 查询该产线正在进行的工单信息
        DevWorkOrder order = workOrderMapper.selectWorkByCompandAndLine(company.getCompanyId(), devInfo.getLineId(), WorkConstants.SING_LINE);
        // 存在进行的工单并且需要计数
        if (order != null && order.getPbSign().equals(WorkConstants.PB_SIGN_YES)) {
            // 工单属于进行状态
            if (WorkConstants.OPERATION_STATUS_STARTING.equals(order.getOperationStatus())) {
                countWorkNum(uploadInfo, devInfo, devDataLog, order);

                // 工单暂停中
            } else {
                order.setPbSign(WorkConstants.PB_SIGN_NO);
                countWorkNum(uploadInfo, devInfo, devDataLog, order);
            }
        } else {
            /**
             * 查询最近完成的工单信息
             */
            order = workOrderMapper.selectLatelyCompleteWork(devInfo.getCompanyId(), devInfo.getLineId(), WorkConstants.SING_LINE);
            if (order != null && order.getSign() == 1) {
                devDataLog.setWorkId(order.getId());
                order.setCumulativeNumber(uploadInfo.getD1());
                order.setSign(0);
                workOrderMapper.updateDevWorkOrder(order);
                //查询对应日志上传数据数据
                DevDataLog log = devDataLogMapper.selectLineWorkDevIo(order.getLineId(), order.getId(), devInfo.getId(), null, WorkConstants.SING_LINE);
                if (log != null) {
                    devDataLog.setDelData(devDataLog.getDataTotal() - log.getDataTotal());
                }
            }
        }
        devDataLog.setCreateDate(new Date());
        devDataLog.setCreateTime(new Date());
        devDataLogMapper.insertDevDataLog(devDataLog);


        map.put("code", 1);
        map.put("msg", "无工单");
        map.put("status", 0);
        map.put("num", 0);
        map.put("workCode", null);
        DevWorkOrder workOrder = workOrderMapper.selectWorkByCompandAndLine(devInfo.getCompanyId(), devInfo.getLineId(), WorkConstants.SING_LINE);
        if (workOrder != null) {
            map.put("code", 1);
            map.put("msg", "进行中");
            map.put("status", workOrder.getOperationStatus());
            map.put("num", workOrder.getCumulativeNumber());
            map.put("workCode", workOrder.getWorkorderNumber());
        }
        return map;
    }

    /**
     * 计算工单数量
     *
     * @param uploadInfo 上传信息
     * @param devInfo    硬件信息
     * @param devDataLog 上报日志
     * @param order      工单信息
     */
    private void countWorkNum(WorkDataForm uploadInfo, DevList devInfo, DevDataLog devDataLog, DevWorkOrder order) {
        devDataLog.setWorkId(order.getId());
        //查询对应日志上传数据数据
        DevDataLog log = devDataLogMapper.selectLineWorkDevIo(order.getLineId(), order.getId(), devInfo.getId(), null, WorkConstants.SING_LINE);
        if (log != null) {
            devDataLog.setDelData(devDataLog.getDataTotal() - log.getDataTotal());
        } else {
            devDataLog.setDelData(uploadInfo.getD1());
        }
        // 更新工单的累计产量
        order.setCumulativeNumber(uploadInfo.getD1());
        workOrderMapper.updateDevWorkOrder(order);
    }


    /**
     * 计数器硬件与产线关联配置
     *
     * @param lineData 关联数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> lineConfigJsCode(LineData lineData) {
        Map<String, Object> map = new HashMap<>(16);
        User user = JwtUtil.getUser();
        if (user == null) {
            map.put("code", 0);
            map.put("msg", UserConstants.NOT_LOGIN);
            return map;
        }
        if (lineData == null || StringUtils.isEmpty(lineData.getJsCode()) || lineData.getLineId() == null) {
            map.put("code", 0);
            map.put("msg", "配置参数错误");
            return map;
        }
        // 查询产线信息
        ProductionLine line = lineMapper.selectProductionLineById(lineData.getLineId());
        if (line == null) {
            map.put("code", 0);
            map.put("msg", "产线不存在或被删除");
            return map;
        }
        // 查询硬件信息
        DevList newDev = devListMapper.selectDevListByDevId(lineData.getJsCode());
        if (newDev == null) {
            map.put("code", 0);
            map.put("msg", "计数器硬件不存在");
            return map;
        }
        // 查询之前配置过的产线信息
        if (newDev.getLineId() != null) {
            if (DevConstants.DEV_CONFIRMTAG_NO.equals(lineData.getConfirmTag())) {
                map.put("code", 2);
                map.put("msg", "该硬件已经关联过产线");
                return map;
            } else {
                ProductionLine oldLine = lineMapper.selectProductionLineById(newDev.getLineId());
                if (oldLine != null) {
                    // 还原之前配置产线为手动
                    oldLine.setManual(CompanyConstants.LINE_COLLECT_MANUAL);
                    lineMapper.updateProductionLine(oldLine);
                }
            }
        }
        // 查询配置的产线是否已经关联计数器硬件信息
        DevList oldDev = devListMapper.selectDevListByLineId(lineData.getLineId());
        if (oldDev != null) {
            // 已经配置过
            if (DevConstants.DEV_CONFIRMTAG_NO.equals(lineData.getConfirmTag())) {
                map.put("code", 2);
                map.put("msg", "该产线已经关联过计数器编码");
                return map;

                // 更新之前硬件配置
            } else {
                devListMapper.updateDevLineTag(DevConstants.DEV_SIGN_NOT_USE, null, oldDev.getDeviceId(), null);
            }
        }
        line.setManual(CompanyConstants.LINE_COLLECT_AUTO);
        line.setRemark(line.getRemark());
        lineMapper.updateProductionLine(line);
        newDev.setCompanyId(user.getCompanyId());
        newDev.setSign(DevConstants.DEV_SIGN_USED);
        newDev.setLineId(line.getId());
        newDev.setDeviceName(line.getLineName() + "--计数硬件");
        devListMapper.updateDevList(newDev);
        map.put("code", 1);
        map.put("msg", "请求成功");
        return map;
    }

    /**
     * 单工位硬件编码关联配置
     *
     * @param lineData 关联数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> stationConfigJsCode(LineData lineData) {
        Map<String, Object> map = new HashMap<>(16);
        User user = JwtUtil.getUser();
        if (user == null) {
            map.put("code", 0);
            map.put("msg", UserConstants.NOT_LOGIN);
            return map;
        }
        // 参数校验
        if (lineData == null || StringUtils.isEmpty(lineData.getJsCode()) || lineData.getStationId() == null) {
            map.put("code", 0);
            map.put("msg", "配置参数错误");
            return map;
        }
        // 查询单工位信息
        SingleWork station = singleWorkMapper.selectSingleWorkById(lineData.getStationId());
        if (station == null || StationConstants.SIGN_HOUSE.equals(station.getSign())) {
            map.put("code", 0);
            map.put("msg", "单工位不存在或被删除");
            return map;
        }
        // 查询车间信息
        SingleWork house = singleWorkMapper.selectSingleWorkById(station.getParentId());
        if (house == null) {
            map.put("code", 0);
            map.put("msg", "车间不存在或被删除");
            return map;
        }
        DevList newDev = devListMapper.selectDevListByDevId(lineData.getJsCode());
        if (newDev == null) {
            map.put("code", 0);
            map.put("msg", "计数器硬件不存在");
            return map;
        }
        if (newDev.getSign().equals(DevConstants.DEV_SIGN_USED)) {
            map.put("code", 0);
            map.put("msg", "该硬件已被配置过");
            return map;
        }
        // 说明配置过
        if (station.getDevId() > 0) {
            if (!station.getDevId().equals(newDev.getId())) {
                // 更新之前硬件未配置
                devListMapper.updateDevSignAndType(station.getDevId(), DevConstants.DEV_SIGN_NOT_USE, null, null);
            }
        }
        // 更新设备的计数器编码配置
        singleWorkMapper.updateStationJsCode(station.getId(), newDev.getId(), newDev.getDeviceId());
        newDev.setRemark(newDev.getRemark());
        newDev.setDeviceName(house.getWorkshopName() + "-" + station.getWorkshopName());
        newDev.setCompanyId(user.getCompanyId());
        newDev.setSign(DevConstants.DEV_SIGN_USED);
        newDev.setDevType(DevConstants.DEV_TYPE_HOUSE);
        devListMapper.updateDevList(newDev);
        map.put("code", 1);
        map.put("msg", "配置成功");
        return map;
    }


    /**
     * 工单配置计数器，关联车间操作
     *
     * @param lineData 关联信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> workConfigJsCode(LineData lineData) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                map.put("code", 0);
                map.put("msg", UserConstants.NOT_LOGIN);
                return map;
            }
            if (lineData == null || lineData.getWorkId() == null || StringUtils.isEmpty(lineData.getJsCode())) {
                map.put("code", 0);
                map.put("msg", "配置参数错误");
                return map;
            }
            // 查询硬件信息
            DevList devList = devListMapper.selectDevListByCode(lineData.getJsCode());
            if (devList == null || DevConstants.BE_DELETED == devList.getDefId()) {
                map.put("code", 0);
                map.put("msg", "硬件不存在或被删除");
                return map;
            }
            // 查询工单信息
            DevWorkOrder workOrder = workOrderMapper.selectDevWorkOrderById(lineData.getWorkId());
            if (workOrder == null) {
                map.put("code", 0);
                map.put("msg", "工单不存在或被删除");
                return map;
            }
            if (WorkConstants.WORK_STATUS_END.equals(workOrder.getWorkorderStatus())) {
                map.put("code", 0);
                map.put("msg", "该工单已经结束");
                return map;
            }
            // 查询车间信息
            SingleWork house = singleWorkMapper.selectSingleWorkById(workOrder.getLineId());
            if (house == null) {
                map.put("code", 0);
                map.put("msg", "工单所在车间不存在或被删除");
                return map;
            }
            // 查询工单权限信息
            if (user.getUserId().intValue() != house.getLiableOne() && user.getUserId().intValue() != house.getLiableTwo()) {
                map.put("code", 0);
                map.put("msg", "不是工单所在车间责任人,没有权限");
                return map;
            }
            // 查询扫码硬件配置的工位信息
            SingleWork station = singleWorkMapper.selectSingleWorkByCode(devList.getId(), 0, 0);
            // 存在对应的工位信息
            if (station != null) {
                // 查询对应工位是否存在正在进行的工单信息
                DevWorkOrder uniOrder = workOrderMapper.selectWorkInHouseBeInBySingId(station.getCompanyId(), station.getParentId(), WorkConstants.SING_SINGLE,
                        station.getId(), WorkConstants.WORK_STATUS_STARTING);
                if (uniOrder != null) {
                    map.put("code", 0);
                    map.put("msg", "该硬件已存在未完成的工单");
                    return map;
                }
                // 查询对应工单是否已经绑定
                SingleWorkOrder uniqueWorkAndStation = singleWorkOrderMapper.selectUniqueWorkAndStation(workOrder.getId(), station.getId());
                if (uniqueWorkAndStation != null) {
                    map.put("code", 0);
                    map.put("msg", "该工单已绑定该工位,勿重复绑定");
                    return map;
                }

                // 更新工位关联车间信息
                station.setParentId(house.getId());
                singleWorkMapper.updateSingleWorkNotNull(station);

                // 修改硬件的配置信息
                devList.setCompanyId(workOrder.getCompanyId());
                devList.setSign(DevConstants.DEV_SIGN_USED);
                devList.setDevType(DevConstants.DEV_TYPE_HOUSE);
                devList.setDeviceName(house.getWorkshopName() + "-" + station.getWorkshopName());
                devListMapper.updateDevList(devList);

                // 新增工单工位关联配置
                saveWorkAndStation(workOrder, house, station);

            } else {
                // 新增工位信息
                station = new SingleWork();
                station.setCompanyId(workOrder.getCompanyId());
                station.setWorkshopName(lineData.getJsCode());
                // 类型值为工位
                station.setSign(WorkConstants.SING_SINGLE);
                station.setDevId(devList.getId());
                station.setDevCode(devList.getDeviceId());
                station.setcTime(new Date());
                station.setParentId(house.getId());
                singleWorkMapper.insertSingleWork(station);

                // 新增工单工位关联绑定
                saveWorkAndStation(workOrder, house, station);

                // 修改硬件为配置状态
                devList.setCompanyId(workOrder.getCompanyId());
                devList.setSign(DevConstants.DEV_SIGN_USED);
                devList.setDevType(DevConstants.DEV_TYPE_HOUSE);
                devList.setDeviceName(house.getWorkshopName() + "-" + lineData.getJsCode());
                devListMapper.updateDevList(devList);
            }
            map.put("code", 1);
            map.put("msg", "工单分配成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 2);
            map.put("msg", "系统异常");
            return map;
        }
    }

    /**
     * 用户扫码更新工位负责人
     *
     * @param lineData 关联信息
     * @return 结果
     */
    @Override
    public Map<String, Object> userConfigJsCode(LineData lineData) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                map.put("code", 0);
                map.put("msg", UserConstants.NOT_LOGIN);
                return map;
            }
            if (lineData == null || StringUtils.isEmpty(lineData.getJsCode())) {
                map.put("code", 0);
                map.put("msg", "配置参数错误");
                return map;
            }
            // 查询硬件信息
            DevList devList = devListMapper.selectDevListByCode(lineData.getJsCode());
            if (devList == null || DevConstants.BE_DELETED == devList.getDefId()) {
                map.put("code", 0);
                map.put("msg", "硬件不存在或被删除");
                return map;
            }
            if (DevConstants.DEV_SIGN_NOT_USE == devList.getSign()) {
                map.put("code", 0);
                map.put("msg", "硬件未被配置");
                return map;
            }
            // 查询工位信息
            SingleWork station = singleWorkMapper.selectSingleWorkByCode(devList.getId(), 0, 0);
            if (station == null) {
                map.put("code", 0);
                map.put("msg", "工位不存在或被删除");
                return map;
            }
            station.setLiableOne(user.getUserId().intValue());
            singleWorkMapper.updateSingleWorkNotNull(station);
            map.put("code", 1);
            map.put("msg", "人员配置成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 2);
            map.put("msg", "系统异常");
            return map;
        }
    }

    /**
     * 新增工单工位绑定信息
     *
     * @param workOrder 工单信息
     * @param house     车间信息
     * @param station   工位信息
     */
    private void saveWorkAndStation(DevWorkOrder workOrder, SingleWork house, SingleWork station) {
        SingleWorkOrder singleWorkOrder = new SingleWorkOrder();
        singleWorkOrder.setWorkId(workOrder.getId());
        singleWorkOrder.setWorkCode(workOrder.getWorkorderNumber());
        singleWorkOrder.setSingleId(station.getId());
        singleWorkOrder.setSingleP(house.getId());
        singleWorkOrder.setcTime(new Date());
        singleWorkOrderMapper.insertSingleWorkOrder(singleWorkOrder);
    }
}

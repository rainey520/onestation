package com.ruoyi.project.app.service.impl;

import com.ruoyi.common.constant.*;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.app.domain.LineData;
import com.ruoyi.project.app.service.ILineService;
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

/**
 * app 产线业务逻辑处理
 * @author rainey
 */
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

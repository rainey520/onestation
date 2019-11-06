package com.ruoyi.project.production.report.service;

import com.ruoyi.common.constant.CompanyConstants;
import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.PdfUtil;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.device.devCompany.domain.DevCompany;
import com.ruoyi.project.device.devCompany.mapper.DevCompanyMapper;
import com.ruoyi.project.production.devWorkOrder.domain.DevWorkOrder;
import com.ruoyi.project.production.devWorkOrder.mapper.DevWorkOrderMapper;
import com.ruoyi.project.production.report.domain.ComCost;
import com.ruoyi.project.production.report.domain.CostItem;
import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import com.ruoyi.project.production.timeRecord.mapper.TimeRecordMapper;
import com.ruoyi.project.system.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公司成本分析服务实现
 *
 * @Author: Rainey
 * @Date: 2019/10/23 12:01
 * @Version: 1.0
 **/
@Service
public class CostServiceImpl implements ICostService {

    /**
     * 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CostServiceImpl.class);

    @Autowired
    private DevCompanyMapper companyMapper;

    @Autowired
    private DevWorkOrderMapper workOrderMapper;

    @Autowired
    private TimeRecordMapper timeRecordMapper;


    private float getFloat3(float val) {
        return new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 数据检索
     *
     * @param comCost 搜索条件
     * @return 结果
     */
    @Override
    public Map<String, Object> searchData(ComCost comCost) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                map.put("code", 0);
                map.put("msg", "用户未登录或登录超时");
                return map;
            }
            DevCompany company = companyMapper.selectDevCompanyById(user.getCompanyId());
            if (company == null) {
                map.put("code", 0);
                map.put("msg", "用户公司不存在或被删除");
                return map;
            }
            if (CompanyConstants.VIP_SIGN_NO.equals(company.getSign())) {
                map.put("code", 0);
                map.put("msg", "非VIP用户");
                return map;
            }
            // 查询对应产线对应产品的所有达成率
            comCost.setCompanyId(user.getCompanyId());
            comCost.setStartTime(comCost.getStartTime() + " 00:00:00");
            comCost.setEndTime(comCost.getEndTime() + " 23:59:59");
            // 查询搜索条件内有工单的日期年月日
            List<CostItem> costItems = workOrderMapper.selectWorkListByDayTime(comCost);
            // 查询满足搜索条件的所有产线信息
            List<ComCost> lineList = workOrderMapper.selectWorkLineInfo(comCost);
            // 达成率
            Float reachRate = null;
            // 实际工时
            Float signHour = null;
            // 考勤工时
            Float workHour = null;
            TimeRecord timeRecord = null;
            // 检索信息
            CostItem item = new CostItem();
            // 图一展示数据
            List<String> xData = new ArrayList<>();
            List<Float> yData = new ArrayList<>();

            // 图二展示数据
            List<String> xDataTwo = new ArrayList<>();
            List<Float> yDataTwo = new ArrayList<>();

            for (CostItem costItem : costItems) {
                signHour =0.0F;
                workHour = 0.0F;
                reachRate = 0.0F;
                // 查询每天的工单信息
                List<DevWorkOrder> orders = workOrderMapper.selectOrderByLineIsSubmit(user.getCompanyId(), comCost.getProductCode(),
                        comCost.getLineId(), costItem.getDayTime() + " 00:00:00", costItem.getDayTime() + " 23:59:59", WorkConstants.SING_LINE);
                if (StringUtils.isNotEmpty(orders) && orders.size() > 0) {
                    for (DevWorkOrder order : orders) {
                        order.setReachRate(0F);
                        if (PdfUtil.IntegerNull(order.getCumulativeNumber()) > 0) {
                            float total = order.getProductStandardHour() * PdfUtil.floatNull(order.getSignHuor());
                            order.setReachRate(total == 0 ? 0 : getFloat3(((float) order.getCumulativeNumber() / total) * 100));
                        }
                        reachRate += order.getReachRate();

                        // 计算工单工时
                        if (order.getPeopleNumber() != null && order.getPeopleNumber() != 0) {
                            signHour += order.getSignHuor() * order.getPeopleNumber();
                        }
                    }
                    reachRate = getFloat3(reachRate / orders.size());
                }

                // 查询每天各条产线的总考勤
                for (ComCost cost : lineList) {
                    timeRecord = timeRecordMapper.selectTimeRecordByLineTime(user.getCompanyId(),cost.getLineId(),costItem.getDayTime());
                    if (timeRecord != null) {
                        workHour += PdfUtil.floatNull(timeRecord.getNormalHour()) * PdfUtil.IntegerNull(timeRecord.getNormalNumber());
                        workHour += PdfUtil.floatNull(timeRecord.getOvertimeHour()) * PdfUtil.IntegerNull(timeRecord.getOvertimeNumber());
                        workHour += PdfUtil.floatNull(timeRecord.getChangeHour());
                    }
                }

                if (signHour != 0.0F && workHour != 0.0F) {
                    xDataTwo.add(costItem.getDayTime());
                    yDataTwo.add(getFloat3((signHour/workHour)*100));
                }

                xData.add(costItem.getDayTime());
                yData.add(reachRate);
            }
            item.setxData(xData);
            item.setyData(yData);
            item.setxDataTwo(xDataTwo);
            item.setyDataTwo(yDataTwo);
            map.put("code", 1);
            map.put("msg", "请求成功");
            map.put("data", item);
            return map;
        } catch (Exception e) {
            LOGGER.error("公司成本分析服务实现数据检索出现异常：" + e.getMessage());
            map.put("code", 0);
            map.put("msg", "请求失败");
            return map;
        }
    }
}

package com.ruoyi.project.app.service;

import com.ruoyi.project.app.domain.LineData;
import com.ruoyi.project.device.api.form.WorkDataForm;
import com.ruoyi.project.production.productionLine.domain.ProductionLine;

import java.util.List;
import java.util.Map;

/**
 * 查询工位操作
 */
public interface ILineService {
    /**
     * 查询所以产线，及其工位信息
     * @return 结果
     */
    List<ProductionLine> selectAllLine();

    /**
     * 拉取所有的产线信息
     * @return 结果
     */
    List<ProductionLine> selectAllLineList();

    /**
     * 计数器硬件端拉取工单信息
     * @param lineData  上传信息
     * @return 结果
     */
    Map<String, Object> getWorkInfo(LineData lineData);

    /**
     * 计数器上传数据信息
     * @param uploadInfo 上传信息
     * @return 结果
     */
    Map<String, Object> uploadWorkInfo(WorkDataForm uploadInfo);

    /**
     * 计数器以硬件产线关联配置
     * @param lineData 关联数据
     * @return 结果
     */
    Map<String, Object> lineConfigJsCode(LineData lineData);

    /**
     * 单工位配置计数器编码
     * @param lineData 关联数据
     * @return 结果
     */
    Map<String, Object> stationConfigJsCode(LineData lineData);

    /**
     * 工单配置计数器，关联车间操作
     * @param lineData 关联信息
     * @return 结果
     */
    Map<String, Object> workConfigJsCode(LineData lineData);

    /**
     * 用户扫码更新工位负责人
     * @param lineData 关联信息
     * @return 结果
     */
    Map<String, Object> userConfigJsCode(LineData lineData);
}

package com.ruoyi.project.app.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.app.domain.LineData;
import com.ruoyi.project.app.service.ILineService;
import com.ruoyi.project.device.api.form.WorkDataForm;
import com.ruoyi.project.production.singleWork.domain.SingleWork;
import com.ruoyi.project.production.singleWork.service.ISingleWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * app端产线交互控制层
 *
 * @author zqm
 */
@RestController
@RequestMapping("/app")
public class LineController {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LineController.class);

    @Autowired
    private ILineService lineService;

    @Autowired
    private ISingleWorkService singleWorkService;

    /**
     * 拉取流水线信息
     *
     * @return 流水线列表
     */
    @RequestMapping("/line")
    public AjaxResult lineAll() {
        try {
            return AjaxResult.success(lineService.selectAllLine());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
     * 拉取车间信息或者单工位信息
     * 首次传入parentId 为 0  必传
     */
    @RequestMapping("/stationList")
    public AjaxResult stationList(@RequestBody SingleWork singleWork) {
        return AjaxResult.success(singleWorkService.selectSingleWorkList(singleWork));
    }


    /**
     * app拉取所有的产线信息
     */
    @RequestMapping("/lineList")
    public AjaxResult lineList() {
        try {
            return AjaxResult.success(lineService.selectAllLineList());
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 计数器硬件端拉取工单信息
     */
    @RequestMapping("/getWorkInfo")
    public Map<String, Object> getWorkInfo(@RequestBody LineData lineData) {
        return lineService.getWorkInfo(lineData);
    }

    /**
     * 计数器上传计数信息
     */
    @RequestMapping("/uploadWorkInfo")
    public Map<String, Object> uploadWorkInfo(@RequestBody WorkDataForm uploadInfo) {
        return lineService.uploadWorkInfo(uploadInfo);
    }

    /**
     * 产线计数器硬件关联配置
     */
    @RequestMapping("/lineConfigJsCode")
    public Map<String, Object> lineConfigJsCode(@RequestBody LineData lineData) {
        return lineService.lineConfigJsCode(lineData);
    }

    /**
     * 单工位配置计数器编码
     */
    @RequestMapping("/stationConfigJsCode")
    public Map<String, Object> stationConfigJsCode(@RequestBody LineData lineData) {
        return lineService.stationConfigJsCode(lineData);
    }

    /**
     * 工单配置计数器，关联车间操作
     */
    @RequestMapping("/workConfigJsCode")
    public Map<String, Object> workConfigJsCode(@RequestBody LineData lineData) {
        return lineService.workConfigJsCode(lineData);
    }

    /**
     * 用户扫码配置工位更新工位责任人
     */
    @RequestMapping("/userConfigJsCode")
    public Map<String, Object> userConfigJsCode(@RequestBody LineData lineData){
        return lineService.userConfigJsCode(lineData);
    }
}

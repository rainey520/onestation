package com.ruoyi.project.app.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.device.devNotice.domain.DevNotice;
import com.ruoyi.project.device.devNotice.service.IDevNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * app消息交互接口
 *
 * @Author: Rainey
 * @Date: 2019/9/27 11:30
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/appNotice")
public class AppNoticeController {

    @Autowired
    private IDevNoticeService noticeService;

    /**
     * app拉取消息列表
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestBody DevNotice notice) {
        Map<String, Object> map = new HashMap<>(16);
        if (notice != null) {
            map.put("data", noticeService.selectNoticeList(notice));
            map.put("code", 0);
            return map;
        }
        map.put("msg", "请求失败");
        map.put("code", 1);
        return map;
    }

    /**
     * 发送消息接口
     */
    @RequestMapping("/sendMsg")
    public AjaxResult sendMsg(@RequestBody DevNotice notice) {
        if (notice != null && StringUtils.isNotEmpty(notice.getNoticeContent())) {
            int i = noticeService.insertDevNotice(notice);
            return i > 0 ? AjaxResult.success() : AjaxResult.error();
        }
        return AjaxResult.error();
    }
}

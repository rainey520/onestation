package com.ruoyi.project.monitor.job.task;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.project.monitor.job.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask extends BaseController {

    @Autowired
    private IJobService jobService;

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    /**
     * 删除两个月都未登录的极光推送列表
     */
    public void deleteInvalidTime(){
        jobService.deleteInvalidTime();
    }
}

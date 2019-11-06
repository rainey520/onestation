package com.ruoyi.project.app.service;

import com.ruoyi.project.app.domain.Index;
import com.ruoyi.project.app.domain.WatchInfo;

import java.util.Map;

/**
 * 看板数据服务层接口
 * @Author: Rainey
 * @Date: 2019/9/27 9:32
 * @Version: 1.0
 **/
public interface IWatchService {

    /**
     * 查看看板数据
     * @param watchInfo
     * @return 结果
     */
    Map<String, Object> selectWatchInfo(WatchInfo watchInfo);

    /**
     * 看板登录验证
     * @param watchInfo 登录信息
     * @return 结果
     */
    Map<String, Object> watchLogin(WatchInfo watchInfo);

    /**
     * 异常上报
     * @param index 上报信息
     * @return 结果
     */
    Map<String, Object> workExc(Index index);
}

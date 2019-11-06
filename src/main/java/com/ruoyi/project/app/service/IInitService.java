package com.ruoyi.project.app.service;

import com.ruoyi.project.app.domain.Index;
import com.ruoyi.project.app.domain.Init;
import com.ruoyi.project.app.domain.LineData;
import com.ruoyi.project.system.menu.domain.Menu;

import java.util.List;
import java.util.Map;

public interface IInitService {
    /**
     * 获取当天工单、菜单权限、公司信息
     * @return
     */
    Init initIndex(Index index);

    /**
     * 获取菜单
     * @param index
     * @return
     */
    List<Menu> initMenu(Index index);

    /**
     * 获取工单号
     * @return 工单号
     */
    String getWorkCode();

    /**
     * 获取计数器硬件编码信息
     * @return 计数器编码
     */
    String getDevJsCode();

    /**
     * 计数器上传校验
     * @param lineData 上传信息
     * @return 结果
     */
    Map<String, Object> checkJsCode(LineData lineData);
}

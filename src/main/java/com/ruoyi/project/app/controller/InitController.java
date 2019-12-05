package com.ruoyi.project.app.controller;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.LoginService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.domain.HttpCode;
import com.ruoyi.project.app.domain.Index;
import com.ruoyi.project.app.domain.LineData;
import com.ruoyi.project.app.service.IInitService;
import com.ruoyi.project.device.api.form.WorkDataForm;
import com.ruoyi.project.system.user.domain.User;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class InitController extends BaseController {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private IInitService iInitService;

    @PostMapping("/login")
    public AjaxResult ajaxLogin(@RequestBody User user) {
        try {
            LOGGER.info("========== APP端登录用户：" + user.getLoginName() + "  登录时间：" + DateUtils.getDate() + "  登录IP地址：" + IpUtils.getIpAddr(ServletUtils.getRequest())+" ==========");
            return loginService.login(user.getLoginName(), user.getPassword(), user.getLangVersion());
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        } catch (BaseException b) {
            return error(b.getMessage());
        }
    }

    /**
     * 获取当天工单、菜单权限、公司信息
     *
     * @return
     */
    @PostMapping("/index")
    public AjaxResult initIndex(@RequestBody Index index) {
        try {
            return AjaxResult.success(iInitService.initIndex(index));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 获取菜单
     *
     * @param index
     * @return
     */
    @PostMapping("/menu")
    public AjaxResult initMenu(@RequestBody Index index) {
        try {
            return AjaxResult.success(iInitService.initMenu(index));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 获取工单号的接口
     */
    @RequestMapping("/getWorkCode")
    public AjaxResult getWorkCode() {
        return AjaxResult.success(iInitService.getWorkCode());
    }


    /**
     * 获取计数器硬件编码信息
     */
    @RequestMapping("/getDevJsCode")
    public AjaxResult getDevJsCode() {
        try {
            return AjaxResult.api(HttpCode.SUCCESS, iInitService.getDevJsCode());
        } catch (BusinessException e) {
            return AjaxResult.api(HttpCode.FAILED, e.getMessage());
        }
    }

    /**
     * 计数器接口校验
     */
    @RequestMapping("/checkJsCode")
    public Map<String, Object> checkJsCode(@RequestBody LineData lineData) {
        return iInitService.checkJsCode(lineData);
    }


    /******************************************************************************************************
     *********************************** 计数器接口上传测试 ************************************************
     ******************************************************************************************************/

    /**
     * 计数器数据上传
     *
     * @param dataForm 上传信息
     * @return 结果
     */
    @RequestMapping("/uploadNum")
    public Map<String, Object> uploadNum(@RequestBody WorkDataForm dataForm) {
        return iInitService.uploadNum(dataForm);
    }


    /**
     * 计数器数据获取
     *
     * @param lineData 上传信息
     * @return 结果
     */
    @RequestMapping("/getNum")
    public Map<String, Object> getNum(@RequestBody WorkDataForm lineData) {
        return iInitService.getNum(lineData);
    }
}

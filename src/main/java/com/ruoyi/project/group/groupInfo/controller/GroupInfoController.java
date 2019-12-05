package com.ruoyi.project.group.groupInfo.controller;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.group.groupInfo.domain.GroupInfo;
import com.ruoyi.project.group.groupInfo.service.IGroupInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小组 信息操作处理
 *
 * @author sj
 * @date 2019-11-30
 */
@Controller
@RequestMapping("/group/groupInfo")
public class GroupInfoController extends BaseController {

    private String prefix = "group/groupInfo";

    @Autowired
    private IGroupInfoService groupInfoService;


    @RequiresPermissions("group:groupInfo:view")
    @GetMapping()
    public String groupInfo() {
        return prefix + "/groupInfo";
    }

    /**
     * 查询小组列表
     */
    @RequiresPermissions("group:groupInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GroupInfo groupInfo) {
        startPage();
        List<GroupInfo> list = groupInfoService.selectGroupInfoList(groupInfo);
        return getDataTable(list);
    }


    /**
     * 新增小组
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存小组
     */
    @RequiresPermissions("group:groupInfo:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody GroupInfo groupInfo) {
        return toAjax(groupInfoService.insertGroupInfo(groupInfo));
    }

    /**
     * 修改小组
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        GroupInfo groupInfo = groupInfoService.selectGroupInfoById(id);
        // 查询该小组的员工信息
        mmap.put("configUser", groupInfoService.selectGroupUserConfig(id));
        // 查询其他员工信息
        mmap.put("otherUser", groupInfoService.selectGroupUserOther(id));
        mmap.put("groupInfo", groupInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存小组
     */
    @RequiresPermissions("group:groupInfo:add")
    @Log(title = "小组", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody GroupInfo groupInfo) {
        return toAjax(groupInfoService.updateGroupInfo(groupInfo));
    }

    /**
     * 删除小组
     */
    @RequiresPermissions("group:groupInfo:add")
    @Log(title = "小组", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Integer id) {
        return toAjax(groupInfoService.deleteGroupInfoById(id));
    }

    /**
     * 校验小组用户名称唯一性
     */
    @PostMapping("/checkGroupNameUnique")
    @ResponseBody
    public String checkGroupNameUnique(GroupInfo groupInfo) {
        return groupInfoService.checkGroupNameUnique(groupInfo);
    }

    /**
     * 查看下发到该小组的工单信息
     */
    @GetMapping("/showWorkList")
    public String showWorkList(Integer id, ModelMap map) {
        map.put("groupId", id);
        return prefix + "/workList";
    }

    /**
     * 打印二维码
     */
    @GetMapping("/printCode")
    public String printCode(ModelMap map){
        // 查询所有的小组信息
        map.put("itemList",groupInfoService.selectGroupListByComId());
        return prefix + "/printCode";
    }

}

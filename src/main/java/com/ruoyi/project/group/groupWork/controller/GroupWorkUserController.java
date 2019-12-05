package com.ruoyi.project.group.groupWork.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.group.groupWork.domain.GroupWorkUser;
import com.ruoyi.project.group.groupWork.service.IGroupWorkUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工单分配小组人员历史 信息操作处理
 * 
 * @author sj
 * @date 2019-11-30
 */
@Controller
@RequestMapping("/group/groupWorkUser")
public class GroupWorkUserController extends BaseController
{
    private String prefix = "group/groupWorkUser";
	
	@Autowired
	private IGroupWorkUserService groupWorkUserService;
	
	@RequiresPermissions("group:groupWorkUser:view")
	@GetMapping()
	public String groupWorkUser()
	{
	    return prefix + "/groupWorkUser";
	}
	
	/**
	 * 查询工单分配小组人员历史列表
	 */
	@RequiresPermissions("group:groupWorkUser:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GroupWorkUser groupWorkUser)
	{
		startPage();
        List<GroupWorkUser> list = groupWorkUserService.selectGroupWorkUserList(groupWorkUser);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出工单分配小组人员历史列表
	 */
	@RequiresPermissions("group:groupWorkUser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupWorkUser groupWorkUser)
    {
    	List<GroupWorkUser> list = groupWorkUserService.selectGroupWorkUserList(groupWorkUser);
        ExcelUtil<GroupWorkUser> util = new ExcelUtil<GroupWorkUser>(GroupWorkUser.class);
        return util.exportExcel(list, "groupWorkUser");
    }
	
	/**
	 * 新增工单分配小组人员历史
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存工单分配小组人员历史
	 */
	@RequiresPermissions("group:groupWorkUser:add")
	@Log(title = "工单分配小组人员历史", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GroupWorkUser groupWorkUser)
	{		
		return toAjax(groupWorkUserService.insertGroupWorkUser(groupWorkUser));
	}

	/**
	 * 修改工单分配小组人员历史
	 */
	@GetMapping("/edit/{gwId}")
	public String edit(@PathVariable("gwId") Integer gwId, ModelMap mmap)
	{
		GroupWorkUser groupWorkUser = groupWorkUserService.selectGroupWorkUserById(gwId);
		mmap.put("groupWorkUser", groupWorkUser);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存工单分配小组人员历史
	 */
	@RequiresPermissions("group:groupWorkUser:edit")
	@Log(title = "工单分配小组人员历史", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GroupWorkUser groupWorkUser)
	{		
		return toAjax(groupWorkUserService.updateGroupWorkUser(groupWorkUser));
	}
	
	/**
	 * 删除工单分配小组人员历史
	 */
	@RequiresPermissions("group:groupWorkUser:remove")
	@Log(title = "工单分配小组人员历史", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(groupWorkUserService.deleteGroupWorkUserByIds(ids));
	}
	
}

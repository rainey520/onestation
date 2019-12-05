package com.ruoyi.project.group.groupInfo.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import com.ruoyi.project.group.groupInfo.service.IGroupUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 小组员工关联 信息操作处理
 * 
 * @author sj
 * @date 2019-11-30
 */
@Controller
@RequestMapping("/group/groupUser")
public class GroupUserController extends BaseController
{
    private String prefix = "group/groupUser";
	
	@Autowired
	private IGroupUserService groupUserService;
	
	@RequiresPermissions("group:groupUser:view")
	@GetMapping()
	public String groupUser()
	{
	    return prefix + "/groupUser";
	}
	
	/**
	 * 查询小组员工关联列表
	 */
	@RequiresPermissions("group:groupUser:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GroupUser groupUser)
	{
		startPage();
        List<GroupUser> list = groupUserService.selectGroupUserList(groupUser);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出小组员工关联列表
	 */
	@RequiresPermissions("group:groupUser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupUser groupUser)
    {
    	List<GroupUser> list = groupUserService.selectGroupUserList(groupUser);
        ExcelUtil<GroupUser> util = new ExcelUtil<GroupUser>(GroupUser.class);
        return util.exportExcel(list, "groupUser");
    }
	
	/**
	 * 新增小组员工关联
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存小组员工关联
	 */
	@RequiresPermissions("group:groupUser:add")
	@Log(title = "小组员工关联", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GroupUser groupUser)
	{		
		return toAjax(groupUserService.insertGroupUser(groupUser));
	}


	/**
	 * 修改保存小组员工关联
	 */
	@RequiresPermissions("group:groupUser:edit")
	@Log(title = "小组员工关联", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GroupUser groupUser)
	{		
		return toAjax(groupUserService.updateGroupUser(groupUser));
	}
	
	/**
	 * 删除小组员工关联
	 */
	@RequiresPermissions("group:groupUser:remove")
	@Log(title = "小组员工关联", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(groupUserService.deleteGroupUserByIds(ids));
	}
	
}

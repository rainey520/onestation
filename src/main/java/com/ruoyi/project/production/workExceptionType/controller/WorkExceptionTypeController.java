package com.ruoyi.project.production.workExceptionType.controller;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.production.workExceptionType.domain.WorkExceptionType;
import com.ruoyi.project.production.workExceptionType.service.IWorkExceptionTypeService;
import com.ruoyi.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 工单工单异常类型 信息操作处理
 * 
 * @author zqm
 * @date 2019-04-16
 */
@Controller
@RequestMapping("/production/workExceptionType")
public class WorkExceptionTypeController extends BaseController
{
    private String prefix = "production/workExceptionType";
	
	@Autowired
	private IWorkExceptionTypeService workExceptionTypeService;
	
	@RequiresPermissions("production:workExceptionList:add")
	@GetMapping()
	public String workExceptionType()
	{
		User user = JwtUtil.getUser();
		if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
			return prefix + "/workExceptionTypeEn";
		}
		return prefix + "/workExceptionType";
	}
	
	/**
	 * 查询工单工单异常类型列表
	 */
	@RequiresPermissions("production:workExceptionList:add")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(WorkExceptionType workExceptionType, HttpServletRequest request)
	{
		startPage();
        List<WorkExceptionType> list = workExceptionTypeService.selectWorkExceptionTypeList(workExceptionType,request);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出工单工单异常类型列表
	 */
	@RequiresPermissions("production:workExceptionType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WorkExceptionType workExceptionType, HttpServletRequest request)
    {
    	List<WorkExceptionType> list = workExceptionTypeService.selectWorkExceptionTypeList(workExceptionType,request);
        ExcelUtil<WorkExceptionType> util = new ExcelUtil<WorkExceptionType>(WorkExceptionType.class);
        return util.exportExcel(list, "workExceptionType");
    }
	
	/**
	 * 新增工单工单异常类型
	 */
	@GetMapping("/add")
	public String add()
	{
		User user = JwtUtil.getUser();
		if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
			return prefix + "/addEn";
		}
		return prefix + "/add";
	}
	
	/**
	 * 新增保存工单工单异常类型
	 */
	@RequiresPermissions("production:workExceptionList:add")
	@Log(title = "工单工单异常类型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(WorkExceptionType workExceptionType, HttpServletRequest request)
	{		
		return toAjax(workExceptionTypeService.insertWorkExceptionType(workExceptionType,request));
	}

	/**
	 * 修改工单工单异常类型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		WorkExceptionType workExceptionType = workExceptionTypeService.selectWorkExceptionTypeById(id);
		mmap.put("workExceptionType", workExceptionType);
		User user = JwtUtil.getUser();
		if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
			return prefix + "/editEn";
		}
		return prefix + "/edit";
	}
	
	/**
	 * 修改保存工单工单异常类型
	 */
	@RequiresPermissions("production:workExceptionList:add")
	@Log(title = "工单工单异常类型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(WorkExceptionType workExceptionType)
	{		
		return toAjax(workExceptionTypeService.updateWorkExceptionType(workExceptionType));
	}
	
	/**
	 * 删除工单工单异常类型
	 */
	@RequiresPermissions("production:workExceptionList:add")
	@Log(title = "工单工单异常类型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(workExceptionTypeService.deleteWorkExceptionTypeByIds(ids));
	}

	/**
	 * 校验异常名称的唯一性
	 */
	@PostMapping("/checkExcTypeNameUnique")
	@ResponseBody
	public String checkExcTypeNameUnique(WorkExceptionType workExceptionType){
		return workExceptionTypeService.checkExcTypeNameUnique(workExceptionType);
	}
	
}

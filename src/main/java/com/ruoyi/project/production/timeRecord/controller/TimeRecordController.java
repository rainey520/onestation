package com.ruoyi.project.production.timeRecord.controller;

import com.ruoyi.common.constant.CompanyConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.production.timeRecord.domain.TimeRecord;
import com.ruoyi.project.production.timeRecord.service.ITimeRecordService;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司考勤记录 信息操作处理
 * 
 * @author sj
 * @date 2019-10-24
 */
@Controller
@RequestMapping("/production/timeRecord")
public class TimeRecordController extends BaseController
{
    private String prefix = "production/timeRecord";
	
	@Autowired
	private ITimeRecordService timeRecordService;

	@GetMapping()
	public String timeRecord(Integer lineId,ModelMap map)
	{
		map.put("lineId",lineId);
		// 查看当天产线工单信息是否存在
		User user = JwtUtil.getUser();
		TimeRecord timeRecord = timeRecordService.selectTimeRecordByLineIdToday(lineId);
		if (timeRecord == null) {
			map.put("daySign", CompanyConstants.DAY_IS_TODAY);
			if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
				return prefix + "/addEn";
			}
			return prefix + "/add";
		} else {
			map.put("daySign", CompanyConstants.DAY_IS_TODAY);
			map.put("timeRecord", timeRecord);
			if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
				return prefix + "/editEn";
			}
			return prefix + "/edit";
		}
	}
	
	/**
	 * 查询公司考勤记录列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TimeRecord timeRecord)
	{
		startPage();
        List<TimeRecord> list = timeRecordService.selectTimeRecordList(timeRecord);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出公司考勤记录列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TimeRecord timeRecord)
    {
    	List<TimeRecord> list = timeRecordService.selectTimeRecordList(timeRecord);
        ExcelUtil<TimeRecord> util = new ExcelUtil<TimeRecord>(TimeRecord.class);
        return util.exportExcel(list, "timeRecord");
    }
	
	/**
	 * 新增公司考勤记录
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存公司考勤记录
	 */
	@Log(title = "公司考勤记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TimeRecord timeRecord)
	{		
		return toAjax(timeRecordService.insertTimeRecord(timeRecord));
	}

	/**
	 * 修改公司考勤记录
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		TimeRecord timeRecord = timeRecordService.selectTimeRecordById(id);
		mmap.put("timeRecord", timeRecord);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存公司考勤记录
	 */
	@Log(title = "公司考勤记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TimeRecord timeRecord)
	{		
		return toAjax(timeRecordService.updateTimeRecord(timeRecord));
	}
	
	/**
	 * 删除公司考勤记录
	 */
	@Log(title = "公司考勤记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(timeRecordService.deleteTimeRecordByIds(ids));
	}

	/**
	 * 校验昨天考勤信息
	 */
	@PostMapping("/selectTimeRecord")
	@ResponseBody
	public AjaxResult selectTimeRecord(Integer lineId){
		// 确认昨天的考勤信息
		TimeRecord timeRecord = timeRecordService.selectTimeRecordByLineIdYesterday(lineId);
		if (CompanyConstants.LINE_TIME_RECORD_NOT_CONFIRM.equals(timeRecord.getInputFlag())) {
		    return error();
		} else {
			return success();
		}
	}

	/**
	 * 考勤确认
	 */
	@GetMapping("/confirmTime")
	public String confirmTime(Integer lineId,ModelMap map){
		map.put("lineId",lineId);
		// 查看当天产线工单信息是否存在
		User user = JwtUtil.getUser();
		TimeRecord timeRecord = timeRecordService.selectTimeRecordByLineIdYesterday(lineId);
		if (CompanyConstants.LINE_TIME_RECORD_NOT_CONFIRM.equals(timeRecord.getInputFlag()) && timeRecord.getId() == null) {
			map.put("daySign", CompanyConstants.DAY_IS_YESTERDAY);
			if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
				return prefix + "/addEn";
			}
			return prefix + "/add";
		} else {
			map.put("timeRecord", timeRecord);
			map.put("daySign", CompanyConstants.DAY_IS_YESTERDAY);
			if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
				return prefix + "/editEn";
			}
			return prefix + "/edit";
		}
	}
}

package com.ruoyi.project.production.filesource.controller;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.production.filesource.domain.FileSourceInfo;
import com.ruoyi.project.production.filesource.service.IFileSourceInfoService;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件素材管理
 *
 * @author zqm
 * @date 2019-05-09
 */
@Controller
@RequestMapping("/iso/fileSource")
public class FileSourceInfoController extends BaseController {

    private String prefix = "production/fileSourceInfo";

    @Autowired
    private IFileSourceInfoService fileSourceService;

    /**
     * 查看文件列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FileSourceInfo fileSourceInfo) {
        startPage();
        List<FileSourceInfo> list = fileSourceService.selectFileSourceInfoList(fileSourceInfo);
        return getDataTable(list);
    }

    /**
     * 新增文件管理
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("avatarfile") MultipartFile file,
                              @RequestParam("saveType") int saveType) {
        try {
            return toAjax(fileSourceService.saveFileInfo(file,saveType));
        } catch (BusinessException e) {
            return error(e.getMessage());
        } catch (Exception e){
            return error("上传失败");
        }
    }

    /**
     * 修改文件名
     */
    @GetMapping("/editFileName")
    public String editFileName(Integer id, String fileName, ModelMap map){
        map.put("id",id);
        map.put("fileName",fileName.substring(0,fileName.lastIndexOf(".")));
        User user = JwtUtil.getUser();
        if (UserConstants.LANGUAGE_EN.equals(user.getLangVersion())) {
            return prefix + "/editFileNameEn";
        }
        return prefix + "/editFileName";
    }

    /**
     * 修改保存文件名
     */
    @PostMapping("/saveFileName")
    @ResponseBody
    public AjaxResult saveFileName(FileSourceInfo fileSourceInfo){
        try {
            return toAjax(fileSourceService.saveFileName(fileSourceInfo));
        } catch (IOException e) {
            return error();
        }
    }

    /**
     * 检验文件名是否重复
     */
    @PostMapping("/checkFileNameNameUnique")
    @ResponseBody
    public String checkFileNameNameUnique(FileSourceInfo  fileSourceInfo) {
        return fileSourceService.checkFileNameNameUnique(fileSourceInfo);
    }


    /**
     * 删除文件
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fileSourceService.deleteFileInfoByIds(ids));
    }

}

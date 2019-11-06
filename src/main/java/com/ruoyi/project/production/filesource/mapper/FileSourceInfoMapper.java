package com.ruoyi.project.production.filesource.mapper;


import com.ruoyi.project.production.filesource.domain.FileSourceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件素材管理 数据层
 * 
 * @author zqm
 * @date 2019-05-09
 */
public interface FileSourceInfoMapper 
{

	/**
	 * 查询文件素材管理信息
	 *
	 * @param id 文件素材管理ID
	 * @return 文件素材管理信息
	 */
	public FileSourceInfo selectFileInfoById(Integer id);
	/**
	 * 查看文件列表
	 * @param fileSourceInfo 文件信息
	 * @return 结果
	 */
	List<FileSourceInfo> selectFileSourceInfoList(FileSourceInfo fileSourceInfo);

	/**
	 * 保存文件
	 * @param fileInfo 文件信息
	 * @return 结果
	 */
    int insertFileInfo(FileSourceInfo fileInfo);

	/**
	 * 通过文件名查询文件信息
	 * @param companyId 公司id
	 * @param fileName 文件名称
	 * @return 结果
	 */
	FileSourceInfo selectFileInfoByFileName(@Param("companyId") Integer companyId, @Param("fileName") String fileName);

	/**
	 * 批量删除文件素材管理
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteFileSourceInfoByIds(String[] ids);

	/**
	 * 通过类型查询文件列表
	 * @param companyId 公司id
	 * @param saveType 保存类型
	 * @return 结果
	 */
	List<FileSourceInfo> selectFileListBySaveType(@Param("companyId") Integer companyId, @Param("saveType") Integer saveType);

	/**
	 * 通过文件名查询文件是否存在
	 * @param companyId 公司id
	 * @param saveType 保存类型
	 * @param fileName 文件名
	 * @return 结果
	 */
	FileSourceInfo selectFileSourceByFileName(@Param("companyId") Integer companyId, @Param("saveType") Integer saveType, @Param("fileName") String fileName);

	/**
	 * 通过文件保存id删除文件信息
	 * @param id 文件主键id
	 * @return 结果
	 */
	int deleteFileSourceInfoById(@Param("id") Integer id);

	/**
	 * 修改文件信息
	 * @param fileSourceInfo 文信信息
	 * @return 结果
	 */
	int updateFileInfo(FileSourceInfo fileSourceInfo);
}
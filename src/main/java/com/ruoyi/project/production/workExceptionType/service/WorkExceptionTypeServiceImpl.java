package com.ruoyi.project.production.workExceptionType.service;

import com.ruoyi.common.constant.WorkConstants;
import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.production.workExceptionType.domain.WorkExceptionType;
import com.ruoyi.project.production.workExceptionType.mapper.WorkExceptionTypeMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单工单异常类型 服务层实现
 *
 * @author zqm
 * @date 2019-04-16
 */
@Service("exceType")
public class WorkExceptionTypeServiceImpl implements IWorkExceptionTypeService {
    @Autowired
    private WorkExceptionTypeMapper workExceptionTypeMapper;

    /**
     * 查询工单工单异常类型信息
     *
     * @param id 工单工单异常类型ID
     * @return 工单工单异常类型信息
     */
    @Override
    public WorkExceptionType selectWorkExceptionTypeById(Integer id) {
        return workExceptionTypeMapper.selectWorkExceptionTypeById(id);
    }

    /**
     * 查询工单工单异常类型列表
     *
     * @param workExceptionType 工单工单异常类型信息
     * @return 工单工单异常类型集合
     */
    @Override
    public List<WorkExceptionType> selectWorkExceptionTypeList(WorkExceptionType workExceptionType, HttpServletRequest request) {
        User u = JwtUtil.getTokenUser(request);
        workExceptionType.setCompanyId(u.getCompanyId());
        return workExceptionTypeMapper.selectWorkExceptionTypeList(workExceptionType);
    }

    /**
     * 新增工单工单异常类型
     *
     * @param workExceptionType 工单工单异常类型信息
     * @return 结果
     */
    @Override
    public int insertWorkExceptionType(WorkExceptionType workExceptionType, HttpServletRequest request) {
        workExceptionType.setCompanyId(JwtUtil.getTokenUser(request).getCompanyId()); // 设置异常类型所属公司
        workExceptionType.setCreateTime(new Date()); // 设置异常类型的创建时间
        return workExceptionTypeMapper.insertWorkExceptionType(workExceptionType);
    }

    /**
     * 修改工单工单异常类型
     *
     * @param workExceptionType 工单工单异常类型信息
     * @return 结果
     */
    @Override
    public int updateWorkExceptionType(WorkExceptionType workExceptionType) {
        return workExceptionTypeMapper.updateWorkExceptionType(workExceptionType);
    }

    /**
     * 删除工单工单异常类型对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWorkExceptionTypeByIds(String ids) {
        return workExceptionTypeMapper.deleteWorkExceptionTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询所有的异常类型
     * @return
     */
    public List<WorkExceptionType> findExceTypeAll(Cookie[] cookies){
        return workExceptionTypeMapper.findExceTypeAll(JwtUtil.getTokenCookie(cookies).getCompanyId());
    }

    /**
     * 校验异常类型名称的唯一性
     * @param workExceptionType 异常类型对象
     * @return 结果
     */
    @Override
    public String checkExcTypeNameUnique(WorkExceptionType workExceptionType) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return WorkConstants.EXC_TYPE_NAME_NOT_UNIQUE;
        }
        String typeName = workExceptionType.getTypeName();
        if (StringUtils.isNotEmpty(typeName)) {
            WorkExceptionType excTypeUnique = workExceptionTypeMapper.selectByCompanyAndTypeName(user.getCompanyId(), typeName);
            if (StringUtils.isNotNull(excTypeUnique) && !excTypeUnique.getId().equals(workExceptionType.getId())) {
                return WorkConstants.EXC_TYPE_NAME_NOT_UNIQUE;
            }
        }
        return WorkConstants.EXC_TYPE_NAME_UNIQUE;
    }

    /**
     * 查看公司所有的异常类型列表
     * @return 结果
     */
    @Override
    public Map<String, Object> selectWorkExcTypeList() {
        Map<String,Object> map = new HashMap<>(16);
        try {
            User user = JwtUtil.getUser();
            if (user == null) {
                map.put("code",0);
                map.put("msg","用户未登录或者登录超时");
                return map;
            }
            List<WorkExceptionType> typeList = workExceptionTypeMapper.selectWorkExcTypeListByComId(user.getCompanyId());
            map.put("code",1);
            map.put("data",typeList);
            map.put("msg","请求成功");
            return map;

        } catch (Exception e) {

        }
        map.put("code",0);
        map.put("msg","获取失败");
        return map;
    }
}

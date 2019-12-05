package com.ruoyi.project.group.groupWork.service;

import com.ruoyi.common.support.Convert;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.group.groupWork.domain.GroupWorkInfo;
import com.ruoyi.project.group.groupWork.mapper.GroupWorkInfoMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 工单产品建档 服务层实现
 *
 * @author sj
 * @date 2019-11-30
 */
@Service
public class GroupWorkInfoServiceImpl implements IGroupWorkInfoService {
    @Autowired
    private GroupWorkInfoMapper groupWorkInfoMapper;

    /**
     * 查询工单产品建档信息
     *
     * @param id 工单产品建档ID
     * @return 工单产品建档信息
     */
    @Override
    public GroupWorkInfo selectGroupWorkInfoById(Integer id) {
        return groupWorkInfoMapper.selectGroupWorkInfoById(id);
    }

    /**
     * 查询工单产品建档列表
     *
     * @param groupWorkInfo 工单产品建档信息
     * @return 工单产品建档集合
     */
    @Override
    public List<GroupWorkInfo> selectGroupWorkInfoList(GroupWorkInfo groupWorkInfo) {
        return groupWorkInfoMapper.selectGroupWorkInfoList(groupWorkInfo);
    }

    /**
     * 新增工单产品建档
     *
     * @param groupWorkInfo 工单产品建档信息
     * @return 结果
     */
    @Override
    public int insertGroupWorkInfo(GroupWorkInfo groupWorkInfo) {
        return groupWorkInfoMapper.insertGroupWorkInfo(groupWorkInfo);
    }

    /**
     * 修改工单产品建档
     *
     * @param groupWorkInfo 工单产品建档信息
     * @return 结果
     */
    @Override
    public int updateGroupWorkInfo(GroupWorkInfo groupWorkInfo) {
        return groupWorkInfoMapper.updateGroupWorkInfo(groupWorkInfo);
    }

    /**
     * 删除工单产品建档对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroupWorkInfoByIds(String ids) {
        return groupWorkInfoMapper.deleteGroupWorkInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 通过工单id删除未扫码的建档信息
     *
     * @param workId 工单id
     * @param status 扫码状态
     * @return 结果
     */
    @Override
    public AjaxResult removeWorkInfoNotScan(Integer workId, String status) {
        groupWorkInfoMapper.deleteGroupWorkInfoByWorkId(workId, status);
        return AjaxResult.api(0,"请求成功",null);
    }

    /**
     * 通过工单id查询建档信息
     * @param workId 工单id
     * @return 结果
     */
    @Override
    public List<GroupWorkInfo> selectGroupWorkInfoByWorkId(Integer workId) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return groupWorkInfoMapper.selectGroupWorkInfoByWorkId(workId,null);
    }
}

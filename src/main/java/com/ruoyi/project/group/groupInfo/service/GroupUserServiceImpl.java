package com.ruoyi.project.group.groupInfo.service;

import com.ruoyi.common.support.Convert;
import com.ruoyi.project.group.groupInfo.domain.GroupUser;
import com.ruoyi.project.group.groupInfo.mapper.GroupUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小组员工关联 服务层实现
 *
 * @author sj
 * @date 2019-11-30
 */
@Service
public class GroupUserServiceImpl implements IGroupUserService {
    @Autowired
    private GroupUserMapper groupUserMapper;


    /**
     * 查询小组员工关联列表
     *
     * @param groupUser 小组员工关联信息
     * @return 小组员工关联集合
     */
    @Override
    public List<GroupUser> selectGroupUserList(GroupUser groupUser) {
        return groupUserMapper.selectGroupUserList(groupUser);
    }

    /**
     * 新增小组员工关联
     *
     * @param groupUser 小组员工关联信息
     * @return 结果
     */
    @Override
    public int insertGroupUser(GroupUser groupUser) {
        return groupUserMapper.insertGroupUser(groupUser);
    }

    /**
     * 修改小组员工关联
     *
     * @param groupUser 小组员工关联信息
     * @return 结果
     */
    @Override
    public int updateGroupUser(GroupUser groupUser) {
        return groupUserMapper.updateGroupUser(groupUser);
    }

    /**
     * 删除小组员工关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroupUserByIds(String ids) {
        return groupUserMapper.deleteGroupUserByIds(Convert.toStrArray(ids));
    }

}

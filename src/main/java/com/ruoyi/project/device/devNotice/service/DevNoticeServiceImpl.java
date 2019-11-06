package com.ruoyi.project.device.devNotice.service;

import com.ruoyi.common.constant.NoticeConstants;
import com.ruoyi.common.support.Convert;
import com.ruoyi.framework.jwt.JwtUtil;
import com.ruoyi.project.device.devNotice.domain.DevNotice;
import com.ruoyi.project.device.devNotice.mapper.DevNoticeMapper;
import com.ruoyi.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 公司消息通知 服务层实现
 *
 * @author zqm
 * @date 2019-04-18
 */
@Service("notice")
public class DevNoticeServiceImpl implements IDevNoticeService {
    @Autowired
    private DevNoticeMapper devNoticeMapper;

    /**
     * 查询公司消息通知信息
     *
     * @param id 公司消息通知ID
     * @return 公司消息通知信息
     */
    @Override
    public DevNotice selectDevNoticeById(Integer id) {
        return devNoticeMapper.selectDevNoticeById(id);
    }

    /**
     * 查询公司消息通知列表
     *
     * @param notice 公司消息通知信息
     * @return 公司消息通知集合
     */
    @Override
    public List<DevNotice> selectDevNoticeList(DevNotice notice, HttpServletRequest request) {
        User u = JwtUtil.getTokenUser(request);
        if (u == null) {
            return Collections.emptyList();
        }
        notice.setCompanyId(u.getCompanyId());
        List<DevNotice> devNotices = devNoticeMapper.selectDevNoticeList(notice);
        return devNotices;
    }

    /**
     * 新增公司消息通知
     *
     * @param devNotice 公司消息通知信息
     * @return 结果
     */
    @Override
    public int insertDevNotice(DevNotice devNotice) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return 0;
        }
        devNotice.setCreateId(user.getUserId().intValue());
        devNotice.setCompanyId(user.getCompanyId());
        devNotice.setCreateTime(new Date());
        devNotice.setNoticeStatus(NoticeConstants.NOTICE_NO_PUBLISH);
        return devNoticeMapper.insertDevNotice(devNotice);
    }

    /**
     * 修改公司消息通知
     *
     * @param devNotice 公司消息通知信息
     * @return 结果
     */
    @Override
    public int updateDevNotice(DevNotice devNotice) {
        return devNoticeMapper.updateDevNotice(devNotice);
    }

    /**
     * 删除公司消息通知对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDevNoticeByIds(String ids) {
        return devNoticeMapper.deleteDevNoticeByIds(Convert.toStrArray(ids));
    }

    /**
     * 发布消息
     * @param id
     * @return
     */
    @Override
    public int publish(Integer id) {
        DevNotice notice = devNoticeMapper.selectDevNoticeById(id);
        notice.setNoticeStatus(NoticeConstants.NOTICE_PUBLISH); // 设置消息状态为已经发布
        return devNoticeMapper.updateDevNotice(notice);
    }

    /**
     * 下线消息
     * @param id
     * @return
     */
    @Override
    public int publishEnd(Integer id) {
        DevNotice notice = devNoticeMapper.selectDevNoticeById(id);
        notice.setNoticeStatus(NoticeConstants.NOTICE_PUBLISH_END); // 设置消息状态为已经下线
        return devNoticeMapper.updateDevNotice(notice);
    }

    /**
     * 查询公司所有的消息
     * @return
     */
    public List<DevNotice> selectAllNotice(Cookie[] cookies){
        User user = JwtUtil.getTokenCookie(cookies);
        List<DevNotice> devNotices = devNoticeMapper.selectAllNotice(user.getCompanyId());
        return devNotices;
    }

    /**
     * 拉取消息列表
     * @param notice 消息信息
     * @return 结果
     */
    @Override
    public List<DevNotice> selectNoticeList(DevNotice notice) {
        User user = JwtUtil.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        notice.setCompanyId(user.getCompanyId());
        return devNoticeMapper.selectDevNoticeList(notice);
    }
}

package com.ruoyi.project.page.layout.service;

import com.ruoyi.project.page.layout.domain.Layout;
import com.ruoyi.project.page.layout.mapper.LayoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面类型布局 逻辑层
 */
@Service("layout")
public class LayoutServiceImpl implements ILayoutService {

    private static final Logger log = LoggerFactory.getLogger(LayoutServiceImpl.class);

    @Autowired
    private LayoutMapper layoutMapper;

    @Override
    public List<Layout> selectLayoutList(Layout layout) {

        return layoutMapper.selectLayoutList(layout);
    }

    @Override
    public List<Layout> selectLayoutAll(int a) {
        return layoutMapper.selectLayoutAll(a);
    }

    @Override
    public int addLayoutInfo(Layout layout) {
        return layoutMapper.addLayoutInfo(layout);
    }


    @Override
    public int updateUser(Layout layout) {
        return layoutMapper.updateUser(layout);
    }

    @Override
    public int delLayoutById(int id) {
        return layoutMapper.delLayoutById(id);
    }

    /**
     * 改变状态
     * @param layout
     * @return
     */
    @Override
    public int changeStatus(Layout layout) {
        return layoutMapper.changeStatus(layout);
    }

    /**
     * 根据编号查询页面类型
     * @param id 编号
     * @return
     */
    @Override
    public Layout selectLayoutById(int id) {
        return layoutMapper.selectLayoutById(id);
    }
}

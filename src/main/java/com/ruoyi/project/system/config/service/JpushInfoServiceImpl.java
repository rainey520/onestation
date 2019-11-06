package com.ruoyi.project.system.config.service;

import com.ruoyi.project.system.config.mapper.JpushInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 极光推送逻辑
 * @Author: Rainey
 * @Date: 2019/10/15 15:04
 * @Version: 1.0
 **/
@Service
public class JpushInfoServiceImpl implements IJpushInfoService {

    @Autowired
    private JpushInfoMapper jpushInfoMapper;
}

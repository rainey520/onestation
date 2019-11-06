package com.ruoyi.common.constant;

/**
 * 公司常量信息
 *
 * @ProjectName deviceManage
 * @PackageName com.ruoyi.common.constant
 * @Author: Administrator
 * @Date: 2019/6/10 14:32
 * @Description //TODO
 * @Version: 1.0
 **/
public class CompanyConstants {

    /**
     * 公司是否为VIP标识 1、为VIP，0、普通会员
     */
    public final static Integer VIP_SIGN_YES = 1;
    public final static Integer VIP_SIGN_NO = 0;
    /**
     * 公司名称是否唯一的返回结果码
     */
    public final static String COM_NAME_UNIQUE = "0";
    public final static String COM_NAME_NOT_UNIQUE = "1";

    /**
     * 公司看板账号是否唯一的返回结果码
     */
    public final static String COM_LOGIN_NUM_UNIQUE = "0";
    public final static String COM_LOGIN_NUM_NOT_UNIQUE = "1";

    /**
     * 产线考勤确认状态标记
     * 0、未确认，1、已经确认
     */
    public final static Integer LINE_TIME_RECORD_CONFIRM = 1;
    public final static Integer LINE_TIME_RECORD_NOT_CONFIRM = 0;

    /**
     * 昨天今天标记常量描述
     * 0、今天状态，1、昨天状态
     */
    public final static Integer DAY_IS_TODAY = 0;
    public final static Integer DAY_IS_YESTERDAY = 1;

    /**
     * 公司产线自动采集标记常量
     * 0、自动采集，1、手动采集
     */
    public final static Integer LINE_COLLECT_AUTO = 0;
    public final static Integer LINE_COLLECT_MANUAL= 1;

    /**
     * 公司产线删除状态常量
     * 0、未删除默认值，1、已经删除
     */
    public final static Integer LINE_UNDELETED = 0;
    public final static Integer LINE_DELETED= 1;
}

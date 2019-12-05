package com.ruoyi.common.utils;

import java.text.NumberFormat;

/**
 * 数字处理工具类
 *
 * @Author: Rainey
 * @Date: 2019/12/4 15:06
 * @Version: 1.0
 **/
public class NumberUtils {

    /**
     * 计算两数值的百分比
     *
     * @param molecule    分子
     * @param denominator 分母
     * @param point       保存小数点的位数
     * @return 百分比 不包含百分号
     */
    public static String getPercent(Integer molecule, Integer denominator, int point) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后位数
        numberFormat.setMaximumFractionDigits(point);
        if (molecule == null || denominator == null || denominator == 0 || molecule == 0) {
            return "0";
        }
        return numberFormat.format((float) molecule / (float) denominator * 100);
    }
}

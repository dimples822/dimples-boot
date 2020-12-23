package com.dimples.core.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.dimples.core.exception.BizException;
import com.dimples.core.exception.DataException;

import java.text.NumberFormat;
import java.text.ParseException;

import cn.hutool.core.util.StrUtil;

/**
 * 数学相关工具
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/12/23
 */
public class NumUtil {

    /**
     * 百分数格式转为小数
     *
     * @param percent ###.#%
     * @return #.###
     */
    public static double formatPercent(String percent) {
        if (!StrUtil.contains(percent, StringPool.PERCENT)) {
            throw new DataException("待转换的字符不是一个百分数表示");
        }
        try {
            return (Double) NumberFormat.getPercentInstance().parse(percent);
        } catch (ParseException e) {
            throw new BizException("百分数转小数失败");
        }
    }

}

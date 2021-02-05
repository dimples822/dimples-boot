package com.dimples.core.util;

import com.dimples.core.constant.StrPool;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 日期工具类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
@SuppressWarnings("MagicConstant")
@Slf4j
public class DUtil extends DateUtils {

    /***
     * 判断字符串是否是yyyy-MM-dd HH:mm:ss格式
     * @param mes 字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isDateTime(String mes) {
        if (!StrUtil.contains(mes, StrPool.SPACE)) {
            return false;
        }
        String[] dateTime = StrUtil.split(mes, StrPool.SPACE);
        return isDate(dateTime[0]) && isTime(dateTime[1]);
    }

    /***
     * 判断字符串是否是yyyy-MM-dd格式
     * @param mes 字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isDate(String mes) {
        String format = "([0-9]{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(mes);
        if (matcher.matches()) {
            String dateReg = "(\\d{4})-(\\d{2})-(\\d{2})";
            pattern = Pattern.compile(dateReg);
            matcher = pattern.matcher(mes);
            if (matcher.matches()) {
                int y = Integer.parseInt(matcher.group(1));
                int m = Integer.parseInt(matcher.group(2));
                int d = Integer.parseInt(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    //每个月的最大天数
                    c.set(y, m - 1, 1);
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
            }
            return true;
        }
        return false;

    }

    /***
     * 判断字符串是否是HH:mm:ss格式
     * @param mes 字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isTime(String mes) {
        if (mes.length() != 8 && StrUtil.contains(mes, StrPool.COLON)) {
            return false;
        }
        mes = StrUtil.removeAll(mes, StrPool.COLON);
        String regex = "^\\d+$";
        if (!mes.matches(regex)) {
            return false;
        }
        int h;
        int m;
        int s;
        try {
            h = Integer.parseInt(mes.substring(0, 2));
            m = Integer.parseInt(mes.substring(2, 4));
            s = Integer.parseInt(mes.substring(4));
            if (h > 23 || h < 0 || m > 59 || m < 0 || s > 59 || s < 0) {
                return false;
            }
        } catch (Exception e) {
            log.info(mes + "不是HH:mm:ss时间格式的字符串");
            return false;
        }
        return true;
    }
}

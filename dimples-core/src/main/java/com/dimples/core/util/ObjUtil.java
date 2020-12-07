package com.dimples.core.util;

import com.dimples.core.exception.BizException;

import java.lang.reflect.Field;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 自定义的对象操作类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/12/7
 */
public class ObjUtil {

    /**
     * 增强版对象判空
     * 判断其中的属性是否为null或者""
     *
     * @param obj          被检测的对象
     * @param excludeField 排开不判断的属性
     * @return 对象是否为空
     */
    public static boolean isEmpty(Object obj, List<String> excludeField) {
        if (ObjectUtil.isEmpty(obj)) {
            return true;
        }
        boolean target = true;
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            String name = f.getName();
            // 判断属性名称是否在排除属性值中
            if (!CollUtil.contains(excludeField, name)) {
                //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                try {
                    Object o = f.get(obj);
                    if (ObjectUtil.isNotEmpty(o)) {
                        target = false;
                        break;
                    }
                } catch (IllegalAccessException e) {
                    throw new BizException("对象属性解析异常", e);
                }
            }
        }
        return target;
    }
}

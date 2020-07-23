package com.dimples.core.util;

import com.dimples.core.exception.DataException;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;

/**
 * 高频方法集合类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
public class CommonUtil {

    /**
     * 判断一个字符是否是中文
     *
     * @param c char
     * @return boolean
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str String
     * @return boolean
     */
    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> getParams(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit;
        String strUrlParam = getParamsStr(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = StrUtil.split(strUrlParam, "&");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = StrUtil.split(strSplit, "=");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (!arrSplitEqual[0].equals("")) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param url url地址
     * @return url请求参数部分
     * @author lzf
     */
    public static String getParamsStr(String url) {
        String strAllParam = null;
        String[] arrSplit;

        if (StrUtil.containsIgnoreCase(url, "?")
                && !(StrUtil.indexOf(url, '?') == StrUtil.length(url) - 1)) {
            arrSplit = StrUtil.split(url, "?");
            if (ArrayUtil.length(arrSplit) > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 验证接口的连通性
     *
     * @param method 请求方式
     * @param api    接口全地址
     */
    public static void verifyApiConnect(String method, String api) {
        switch (method) {
            case "POST":
                HttpRequest request = HttpUtil.createRequest(Method.POST, api);
                HttpResponse response = request.execute();
                if (StrUtil.isBlank(response.body())) {
                    throw new DataException("接口连通性测试失败，未能请求到数据，请检查");
                }
                break;
            case "GET":
                break;
            default:
        }
    }
}
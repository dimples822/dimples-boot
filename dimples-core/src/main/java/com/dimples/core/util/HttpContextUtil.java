package com.dimples.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;

/**
 * http工具
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
@Slf4j
public class HttpContextUtil {

    private static final String UNKNOWN = "unknown";

    private static final String LOOPBACK_ADDRESS = "127.0.0.1";
    private static final String LOCAL_ADDRESS = "0:0:0:0:0:0:0:1";

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-
     * For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIp() {
        HttpServletRequest request = HttpContextUtil.getRequest();
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StrUtil.isBlank(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (LOOPBACK_ADDRESS.equals(ip)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if (inet != null) {
                        ip = inet.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR:", e);
        }
        return ip;
    }

    /**
     * 根据IP地址获取mac地址
     *
     * @param ipAddress 127.0.0.1
     * @return String
     * @throws SocketException      SocketException
     * @throws UnknownHostException UnknownHostException
     */
    public static String getLocalMac(String ipAddress) throws SocketException, UnknownHostException {
        // TODO Auto-generated method stub
        String str;
        String macAddress = "";
        // 如果为127.0.0.1,则获取本地MAC地址。
        if (LOOPBACK_ADDRESS.equals(ipAddress) || LOCAL_ADDRESS.equals(ipAddress)) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            // 貌似此方法需要JDK1.6。
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
                    .getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            macAddress = sb.toString().trim().toUpperCase();
            return macAddress;
        } else {
            // 获取非本地IP的MAC地址
            try {
                System.out.println(ipAddress);
                Process p = Runtime.getRuntime()
                        .exec("nbtstat -A " + ipAddress);
                System.out.println("===process==" + p);
                InputStreamReader ir = new InputStreamReader(p.getInputStream());

                BufferedReader br = new BufferedReader(ir);

                while ((str = br.readLine()) != null) {
                    if (str.indexOf("MAC") > 1) {
                        macAddress = str.substring(str.indexOf("MAC") + 9);
                        macAddress = macAddress.trim();
                        System.out.println("macAddress:" + macAddress);
                        break;
                    }
                }
                p.destroy();
                br.close();
                ir.close();
            } catch (IOException e) {
                log.error("获取MAC地址：{}", e.getMessage());
            }
            return macAddress;
        }
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        return request;
    }

    /**
     * 获取 HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            response = requestAttributes.getResponse();
        }
        return response;
    }

    /**
     * 获取发起请求的浏览器名称
     */
    public static String getBrowserName(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 获取发起请求的浏览器版本号
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取浏览器版本号
        Version version = browser.getVersion(header);
        return version.getVersion();
    }

    /**
     * 获取发起请求的操作系统名称
     */
    public static String getOsName(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        return operatingSystem.getName();
    }
}

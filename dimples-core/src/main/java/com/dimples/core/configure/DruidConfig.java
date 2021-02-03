package com.dimples.core.configure;

import com.alibaba.druid.util.Utils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import javax.servlet.Filter;

/**
 * druid 数据源
 *
 * @author ruoyi
 */
@Configuration
public class DruidConfig {

    /**
     * 去除监控页面底部的广告
     *
     * @return FilterRegistrationBean
     */
    @SuppressWarnings({"rawtypes"})
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true")
    public FilterRegistrationBean removeDruidFilterRegistrationBean() throws IOException {
        // 获取common.js内容
        String text = Utils.readFromResource("support/http/resources/js/common.js");

        // 屏蔽 this.buildFooter(); 直接替换为空字符串,让js没机会调用
        final String newJs = text.replace("this.buildFooter();", "");

        // 新建一个过滤器注册器对象
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

        // 注册common.js文件的过滤器
        registration.addUrlPatterns("/druid/js/common.js");

        // 添加一个匿名的过滤器对象,并把改造过的common.js文件内容写入到浏览器
        registration.setFilter((servletRequest, servletResponse, filterChain) -> {

            // 重置缓冲区，响应头不会被重置
            servletResponse.resetBuffer();

            // 把改造过的common.js文件内容写入到浏览器
            servletResponse.getWriter().write(newJs);

        });

        return registration;
    }
}

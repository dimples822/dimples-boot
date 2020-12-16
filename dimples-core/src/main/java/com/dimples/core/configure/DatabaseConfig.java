package com.dimples.core.configure;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.dimples.core.page.PageInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/21
 */
@Configuration
public class DatabaseConfig {

    /**
     * Mybatis-Plus
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }

    /**
     * 自定义
     *
     * @return PageInterceptor
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setDialectType("cache");
        pageInterceptor.setDialectClazz("com.dimples.core.page.CacheDialect");
        return pageInterceptor;
    }

}

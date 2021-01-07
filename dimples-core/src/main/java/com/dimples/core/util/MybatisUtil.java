package com.dimples.core.util;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;

/**
 * 数据库操作相关工具
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2021/1/7
 */
public class MybatisUtil {

    /**
     * 获取SqlSession
     *
     * @param sqlSessionTemplate 直接注入即可
     * @return SqlSession
     */
    public static SqlSession getSqlSession(SqlSessionTemplate sqlSessionTemplate) {
        return SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator());
    }

}

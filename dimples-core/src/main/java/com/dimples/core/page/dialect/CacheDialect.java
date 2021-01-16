package com.dimples.core.page.dialect;

import com.dimples.core.page.metadata.OrderItem;
import com.dimples.core.page.metadata.Page;
import com.google.common.collect.Lists;

import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Cache数据库分页支持
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/10/21
 */
public class CacheDialect implements IDialect {

    /**
     * select top 10 %vid AS vid,* from(
     * select top all * from (
     * select *
     * from tb_xxx tm
     * WHERE aa = 'bbb'
     * )order by ccc ASC
     * ) where %vid > 10
     *
     * @param originalSql 原始语句
     * @param page        分页对象
     * @return String
     */
    @Override
    public String buildPaginationSql(String originalSql, Page<?> page) {

        long offset = page.getSize();
        long limit = (page.getCurrent() - 1) * page.getSize();

        StringBuilder result = new StringBuilder(originalSql.length() + 200);
        result.append("SELECT TOP ");
        if (page.searchAll()) {
            result.append("ALL");
        } else {
            result.append(offset);
        }
        result.append(" %vid AS rowId,* FROM ( ")
                .append("SELECT TOP ALL * FROM (")
                .append(originalSql)
                .append(")");

        if (CollUtil.isNotEmpty(page.getOrders())) {
            List<String> orderList = Lists.newArrayList();
            List<OrderItem> orders = page.getOrders();
            for (OrderItem order : orders) {
                if (order.isAsc()) {
                    orderList.add(order.getColumn() + " ASC");
                } else {
                    orderList.add(order.getColumn() + " DESC");
                }
            }
            String join = StrUtil.join(",", orderList);
            result.append(" ORDER BY ").append(join);
        }

        result.append(" ) AS tvl WHERE %vid > ")
                .append(limit);


        return result.toString();
    }
}

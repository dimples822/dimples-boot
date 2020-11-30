package com.dimples.core.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;

import java.util.List;

import cn.hutool.core.util.StrUtil;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/5/13
 */
public class CacheDialect implements IDialect {

    /**
     * select top 10 %vid AS vid,* from(
     *  select top all * from (
     *      select *
     *      from EMR_USER.tb_menu tm
     *      WHERE tm.menu_type = '菜单'
     *      AND tm.belong_system = '1'
     *  )order by menu_order ASC
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
        result.append("SELECT TOP ")
                .append(offset)
                .append(" %vid AS rowId,* FROM ( ")
                .append("SELECT TOP ALL * FROM (")
                .append(originalSql)
                .append(")");

        if (CollectionUtils.isNotEmpty(page.getOrders())) {
            List<String> orderList = Lists.newArrayList();
            List<OrderItem> orders = page.getOrders();
            for (OrderItem order : orders) {
                if (order.isAsc()) {
                    orderList.add(order.getColumn() + " ASC");
                } else {
                    orderList.add(order.getColumn() + " DESC");
                }
            }
            String join = StrUtil.join(StringPool.COMMA, orderList);
            result.append(" ORDER BY ").append(join);
        }

        result.append(" ) AS tvl WHERE %vid > ")
                .append(limit);


        return result.toString();
    }
}

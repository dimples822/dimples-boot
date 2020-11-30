package com.dimples.core.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.ToString;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/5/13
 */
@SuppressWarnings("all")
@ToString
public class Page<T> {

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0L;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10L;

    /**
     * 当前页
     */
    private long current = 1L;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 当前分页总页数
     */
    private long pages;

    /**
     * 是否查询总数
     */
    private boolean searchCount;


    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Page(int current, int size) {
        this(current, size, 0);
    }

    public Page(QueryRequest queryRequest) {
        if (ObjectUtil.isEmpty(queryRequest)) {
            queryRequest = new QueryRequest();
        }
        if (queryRequest.getPageNumber() > 1) {
            this.current = queryRequest.getPageNumber();
        }
        this.size = queryRequest.getPageSize();

        if (StrUtil.isNotBlank(queryRequest.getField())) {
            this.addOrder(new OrderItem().setColumn(queryRequest.getField()).setAsc(!StrUtil.equalsIgnoreCase("DESC", queryRequest.getOrder())));
        }
        this.setSearchCount(true);
    }

    public Page(QueryRequest queryRequest, boolean searchCount) {
        if (ObjectUtil.isEmpty(queryRequest)) {
            queryRequest = new QueryRequest();
        }
        if (queryRequest.getPageNumber() > 1) {
            this.current = queryRequest.getPageNumber();
        }
        this.size = queryRequest.getPageSize();

        if (StrUtil.isNotBlank(queryRequest.getField())) {
            this.addOrder(new OrderItem().setColumn(queryRequest.getField()).setAsc(!StrUtil.equalsIgnoreCase("DESC", queryRequest.getOrder())));
        }
        this.setSearchCount(searchCount);
    }


    public Page(int current, int size, int total) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.setSearchCount(true);
    }

    public List<T> getRecords() {
        return this.records;
    }

    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public Page<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    /**
     * 当前分页总页数
     */
    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 内部什么也不干
     * <p>只是为了 json 反序列化时不报错</p>
     */
    public Page<T> setPages(long pages) {
        // to do nothing
        return this;
    }

    /**
     * 移除符合条件的条件
     *
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public Page<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public Page<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }

    public List<OrderItem> orders() {
        return getOrders();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        if (ObjectUtil.isEmpty(searchCount)) {
            searchCount = true;
        }
        this.searchCount = searchCount;
    }
}

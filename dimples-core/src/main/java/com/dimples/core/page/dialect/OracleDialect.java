package com.dimples.core.page.dialect;

import com.dimples.core.page.metadata.Page;
import com.dimples.core.page.util.PageUtil;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/5/19
 */
public class OracleDialect implements IDialect {

    @Override
    public String buildPaginationSql(String originalSql, Page<?> page) {
        long startIndex = (page.getCurrent() - 1) * page.getSize();
        long endIndex = page.getCurrent() * page.getSize();

        StringBuilder result = new StringBuilder(originalSql.length() + 200);
        result.insert(0, "SELECT * FROM ( SELECT TMP.*, ROWNUM ROW_ID FROM ( ").append(originalSql);

        result.append(" ) TMP ");
        if (!page.searchAll()) {
            result.append(" WHERE ROWNUM <=")
                    .append(endIndex);
        }
        result.append(" ) WHERE ROW_ID > ")
                .append(startIndex);

        PageUtil.buildOrder(page, result);

        return result.toString();
    }

}

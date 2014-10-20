package com.letv.common.util;

import java.util.Map;

/**
 * @author zxwu
 */
public final class PagingUtil {
    private PagingUtil() {
    }

    /**
     * 分页支持
     * 
     * @param pageSize 每页记录数
     * @param recordCount 总记录数
     * @param page 第几页
     * @param pageOffset 显示页码范围
     * @param map map
     * @return 2个元素的数组，包括begin and end
     */
    public static int[] addPagingSupport(int pageSize, Integer recordCount, Integer page, Integer pageOffset,
            Map<String, Object> map) {
        recordCount = recordCount == null ? 0 : recordCount;
        int pageCount = recordCount / pageSize;
        if (recordCount % pageSize > 0) {
            pageCount++;
        }

        if (page == null) {
            page = 1;
        }
        if (page > pageCount) {
            page = pageCount;
        }
        if (page < 1) {
            page = 1;
        }

        map.put("recordCount", recordCount); // 总记录数
        map.put("page", page); // 第几页
        map.put("pageCount", pageCount); // 总页数
        map.put("pageSize", pageSize); // 每页记录数

        if (page > 1) {
            map.put("prevPage", page - 1); // 上一页
        }
        if (page < pageCount) {
            map.put("nextPage", page + 1); // 下一页
        }

        if (pageOffset != null) {
            int pageBegin = page - pageOffset;
            map.put("pageBegin", pageBegin < 1 ? 1 : pageBegin); // 显示页码开始

            int pageEnd = page + pageOffset;
            map.put("pageEnd", pageEnd > pageCount ? pageCount : pageEnd); // 显示页码范围结束
        }
        
        int recordBegin = pageSize * (page - 1) + 1; // 记录开始数
        int recordEnd = pageSize * page; // 记录结束数

        return new int[] { recordBegin, recordEnd };
    }
}

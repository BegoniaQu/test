package com.yc.fresh.common;



import lombok.Getter;

import java.util.List;

/**
 * Created by quyang on 2018/4/1.
 */
@Getter
public class PageResult<T>{

    private long total;
    private int page_count;
    private long page_size;
    private long page_no;
    private List<T> page_list;

    /**
     * @param page_list page页list
     * @param page_no
     * @param page_size
     * @param total
     */
    public PageResult(List<T> page_list, long page_no, long page_size,
                      long total) {
        this.total = total;
        this.page_size = page_size;
        this.page_no = page_no;
        this.page_list = page_list;

        page_count = (int) Math.ceil((double)total/page_size);
    }

}

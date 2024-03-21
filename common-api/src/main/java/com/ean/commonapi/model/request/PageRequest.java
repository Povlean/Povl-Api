package com.ean.commonapi.model.request;

import lombok.Data;

/**
 * @author:ean
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 内容
     */
    private String content;

}

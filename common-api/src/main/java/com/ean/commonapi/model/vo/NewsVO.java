package com.ean.commonapi.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author:ean
 */
@Data
@Builder
public class NewsVO {

    private String name;

    private String hot;

    private String url;


}

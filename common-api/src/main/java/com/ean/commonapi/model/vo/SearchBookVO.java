package com.ean.commonapi.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:ean
 */
@Data
@Builder
public class SearchBookVO implements Serializable {

    private String isbn;

    private String bookName;

    private String author;

    private String press;

    private String pressDate;

    private String pressPlace;

}

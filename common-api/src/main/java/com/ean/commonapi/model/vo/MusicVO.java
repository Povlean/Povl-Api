package com.ean.commonapi.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:ean
 */
@Data
@Builder
public class MusicVO implements Serializable {

    /**
     * 歌名
     */
    private String music;

    /**
     * 歌手
     */
    private String singer;

    /**
     * 类型
     */
    private String category;

    /**
     * 描述
     */
    private String description;

}


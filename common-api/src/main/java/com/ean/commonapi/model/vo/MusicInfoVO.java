package com.ean.commonapi.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author:ean
 */
@Data
@Builder
public class MusicInfoVO {
    
    private String id;
    
    private String cover;

    private String sings;

    private String songs;

    private String album;

}

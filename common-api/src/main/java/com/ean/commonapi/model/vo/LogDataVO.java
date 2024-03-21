package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:ean
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LogDataVO {

    private String operation;

    private String userAccount;

    private String createTime;

}

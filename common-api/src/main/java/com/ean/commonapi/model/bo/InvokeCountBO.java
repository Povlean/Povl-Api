package com.ean.commonapi.model.bo;

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
public class InvokeCountBO {

    private Long userId;

    private Long interfaceInfoId;

    private Integer count;
}

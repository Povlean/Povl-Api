package com.ean.commonapi.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:ean
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NameCountBO {
    private String interfaceName;
    private Integer count;
}

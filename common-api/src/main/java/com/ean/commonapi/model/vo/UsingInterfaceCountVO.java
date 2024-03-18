package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:ean
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsingInterfaceCountVO implements Serializable {

    private String date;

    private String count;

}

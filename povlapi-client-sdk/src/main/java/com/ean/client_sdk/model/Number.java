package com.ean.client_sdk.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:ean
 */
@Data
@Builder
public class Number implements Serializable {

    @NotNull
    private String number1;

    @NotNull
    private String number2;

    @NotNull
    private String accessKey;

    @NotNull
    private String secretKey;

}

package com.ean.commonapi.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author:ean
 */
@Data
@Builder
public class StockVO {

    private String symbol;

    private String current;

    private String percent;

    private String chg;

    private String timestamp;

    private String volume;

    private String amount;

    private String market_capital;

    private String float_market_capital;

    private String turnover_rate;

    private String amplitude;

    private String open;

    private String last_close;

    private String high;

    private String low;

    private String avg_price;

    private String trade_volume;
}

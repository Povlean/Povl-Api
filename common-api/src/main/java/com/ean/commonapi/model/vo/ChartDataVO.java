package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:ean
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChartDataVO {

    private List<String> date;

    private List<Integer> xAxis;

    private List<Integer> yAxis;
}

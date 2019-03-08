package com.yun.report.model.chart;

import lombok.Data;

@Data
public class DataSets{
    private String[] labels;
    private DataValue[] datasets;
}

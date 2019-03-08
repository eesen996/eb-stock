package com.yun.report.model.chart;

import lombok.Data;

@Data
public class DataValue{
    private String label;
    private int[] data;
    private String[] backgroundColor;
    private String[] borderColor;
    private int borderWidth=1;
}

package com.yun.report.model.chart;

import lombok.Data;

@Data
public class ChartBar {
    private String type="bar";
    private DataSets data=new DataSets();
}
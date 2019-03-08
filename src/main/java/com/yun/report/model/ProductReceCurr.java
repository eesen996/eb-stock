package com.yun.report.model;

import lombok.Data;

@Data
public class ProductReceCurr {

    private int prodId;
    private int prodParamId;
    private String prodName;
    private String prodParamName;
    private int prodReceNum;
    private int prodCurrNum;
}

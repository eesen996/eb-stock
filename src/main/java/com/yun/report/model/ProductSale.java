package com.yun.report.model;

import lombok.Data;

@Data
public class ProductSale {

    private int prodId;
    private String prodName;
    private int prodParamId;
    private String prodParamName;
    private int saleNum;
}

package com.yun.product.model;

import lombok.Data;

import java.util.List;

@Data
public class Product {

    private Integer prodId;
    private String prodName;
    private String prodNo;
    private ProductType productType;
    private Integer prodState;

    private List<ProductParameter> productParameterList;
}

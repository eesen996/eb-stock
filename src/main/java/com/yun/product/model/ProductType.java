package com.yun.product.model;

import lombok.Data;

import java.util.List;

/**
 * create table product_type(
 * prod_type_id int primary key auto_increment,
 * prod_type_name varchar(64),
 * parent_id int
 * );
 */
@Data
public class ProductType {

    private int prodTypeId;

    private String prodTypeName;

    private ProductType parentProdType;

    private List<ProductType> childNodeList;

}

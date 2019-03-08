package com.yun.stock.model;

import com.yun.product.model.Product;
import com.yun.product.model.ProductParameter;
import lombok.Data;

/**
 * 待收库存
 */
@Data
public class ProductReceiveStock {

    private Integer prodReceStockId;
    private Product product;
    private ProductParameter productParameter;
    private Integer stockNum;
}

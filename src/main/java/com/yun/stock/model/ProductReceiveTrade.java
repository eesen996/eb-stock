package com.yun.stock.model;

import com.yun.product.model.Product;
import com.yun.product.model.ProductParameter;
import com.yun.user.model.UserInfo;
import lombok.Data;

import java.util.Date;

@Data
public class ProductReceiveTrade {
    private Integer prodReceTradeId;
    private Date tradeDate;
    private Product product;
    private ProductParameter productParameter;
    private Integer beginNum;
    private Integer tradeAdd;
    private Integer tradeDel;
    private Integer endNum;
    private UserInfo oper;


}

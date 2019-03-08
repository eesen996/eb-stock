package com.yun.product.service;

import com.yun.common.Page;
import com.yun.product.dao.ProductDao;
import com.yun.product.dao.ProductParameterDao;
import com.yun.product.dao.ProductTypeDao;
import com.yun.product.model.Product;
import com.yun.product.model.ProductParameter;
import com.yun.product.model.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Slf4j
@Transactional
@Service
public class ProductService {

    @Autowired
    private ProductTypeDao productTypeDao;

    @Autowired
    private ProductDao productDao;

    /**
     * 查询产品类别目录树
     * @return
     */
    public List<ProductType> findProductTypeRoot(int parentId){
        return productTypeDao.findProductTypeRoot(parentId);
    }

    /**
     * 保存产品类别
     * @param productType
     */
    public void insertProductType(ProductType productType) {
        productTypeDao.insertProductType(productType);
    }
    /**
     * 修改产品类别
     * @param productType
     */
    public void updateProductType(ProductType productType) {
        productTypeDao.updateProductType(productType);
    }

    /**
     * 查询一个产品类别
     * @param prodTypeId
     * @return
     */
    public ProductType findProductTypeById(Integer prodTypeId) {
       return productTypeDao.findProductTypeById(prodTypeId);
    }

    /**
     * 删除产品类别
     * @param prodTypeId
     */
    public void deleteProductType(Integer prodTypeId) {
        productTypeDao.deleteProductType(prodTypeId);
    }

    //--------------------------物品---------------------
    public List<Product> findProductByParam(Map param, Page page){
        List<Product> productList=productDao.findProductByParam(param,page);
        for(Product product:productList){
            product.setProductParameterList(productParameterDao.findProductParameterListByProductId(product.getProdId()));
        }
        return productList;
    }


    public int findProductRowByParam(Map param) {
        return productDao.findProductRowByParam(param);
    }

    public void insertProduct(Product product) {
        productDao.insertProduct(product);
    }

    public List<Product> findProductByProdType(Integer prodTypeId) {
        return productDao.findProductByProdType(prodTypeId);
    }

    public void deleteProduct(int productId) {
        productDao.deleteProduct(productId);
    }

    public Product findProductById(int productId) {
        return productDao.findProductById(productId);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    //--------------------物品参数

    @Autowired
    private ProductParameterDao productParameterDao;

    public List<ProductParameter> findProductParameterListByProductId(int productId){
        return productParameterDao.findProductParameterListByProductId(productId);
    }

    /**
     * 批量插入产品规格参数
     * @param prodId
     * @param productParameters
     */
    public void insertProductParameter(int prodId,String[] productParameters) {
        if(null!=productParameters){
            for(String prodParamName:productParameters){
                ProductParameter pp=new ProductParameter();
                pp.setProdId(prodId);
                pp.setProdParamName(prodParamName);
                productParameterDao.insertProductParameter(pp);
            }
        }
    }

    public void deleteProductParameter(int prodParamId) {
        productParameterDao.deleteProductParameter(prodParamId);
    }
    public void deleteProductParameter(String[] param) {
        if(null!=param){
            for(String pid:param) {
                productParameterDao.deleteProductParameter(Integer.parseInt(pid));
            }
        }

    }

    public List<Product> productListForStock() {
        return productDao.productListForStock();
    }


}

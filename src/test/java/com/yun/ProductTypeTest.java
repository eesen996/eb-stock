package com.yun;

import com.yun.product.dao.ProductTypeDao;
import com.yun.product.model.ProductType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTypeTest {

    @Autowired
    private ProductTypeDao productTypeDao;

    @Test
    public void testFindProductTypeRoot(){
        List<ProductType> list=productTypeDao.findProductTypeRoot(0);
        System.out.println(list);
    }
}

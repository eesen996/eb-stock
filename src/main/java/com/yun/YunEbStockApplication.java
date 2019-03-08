package com.yun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
public class YunEbStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunEbStockApplication.class, args);
    }
}

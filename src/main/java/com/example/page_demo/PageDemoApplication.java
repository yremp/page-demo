package com.example.page_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.page_demo.mapper")
@SpringBootApplication
public class PageDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageDemoApplication.class, args);
    }

}

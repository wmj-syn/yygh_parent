package com.atguigu.yygh.cmn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hp
 * @create 2023-05-28-19:20
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.yygh.cmn.mapper")
public class ServiceCmnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }

}

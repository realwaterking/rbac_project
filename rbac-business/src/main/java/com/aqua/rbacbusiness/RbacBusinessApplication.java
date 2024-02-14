package com.aqua.rbacbusiness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.aqua.rbacbusiness.mapper", "com.aqua.rbaccore.mapper"})
@ComponentScan(basePackages = {"com.aqua.rbaccore", "com.aqua.rbacbusiness"})
public class RbacBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacBusinessApplication.class, args);
    }

}

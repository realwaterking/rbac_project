package com.aqua.rbacbusiness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.aqua.rbacbusiness.mapper")
public class RbacBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacBusinessApplication.class, args);
    }

}

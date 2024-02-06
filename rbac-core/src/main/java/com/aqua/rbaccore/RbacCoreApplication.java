package com.aqua.rbaccore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.aqua.rbaccore.mapper")
public class RbacCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacCoreApplication.class, args);
    }

}

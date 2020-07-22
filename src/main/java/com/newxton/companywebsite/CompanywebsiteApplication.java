package com.newxton.companywebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.newxton.companywebsite.dao")
@SpringBootApplication
public class CompanywebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanywebsiteApplication.class, args);
    }

}

package com.newxton.companywebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan("com.newxton.companywebsite.dao")
@SpringBootApplication
public class CompanywebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanywebsiteApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Hongkong"));
    }
}

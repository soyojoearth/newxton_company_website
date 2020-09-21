package com.newxton.supercompanyapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan("com.newxton.supercompanyapi.dao")
@SpringBootApplication
public class SupercompanyapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupercompanyapiApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Hongkong"));
    }

}

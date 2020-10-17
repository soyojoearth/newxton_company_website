package com.newxton.nxtframework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan("com.newxton.nxtframework.dao")
@SpringBootApplication
public class NxtframeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(NxtframeworkApplication.class, args);
    }

    @PostConstruct
    void started() {
        //设定东八区
        TimeZone.setDefault(TimeZone.getTimeZone("Hongkong"));
    }

}

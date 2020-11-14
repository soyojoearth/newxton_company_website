package com.newxton.nxtframework;

import com.github.yt.web.EnableYtWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan("com.newxton.nxtframework.dao")
@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableYtWeb
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

package com.fly.simpletools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan//防止 @WebListener 无效
@SpringBootApplication
public class SimpleToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleToolsApplication.class, args);
    }
}

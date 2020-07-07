package com.puhui.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMailApplication.class, args);
    }

}

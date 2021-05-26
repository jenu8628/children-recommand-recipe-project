package com.webapp.formychild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement
public class ForMyChildApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ForMyChildApplication.class, args);
    }

}

/**
 * < swagger >
 * http://localhost:7080/formychild/swagger-ui.html
 */


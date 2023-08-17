package com.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.social.repository")
@SpringBootApplication
public class SocialMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApplication.class, args);
    }
}
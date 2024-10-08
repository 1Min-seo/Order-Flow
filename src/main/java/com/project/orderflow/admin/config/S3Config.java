package com.project.orderflow.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Bean
    public String bucketName() {
        return "orderflow-bk";
    }
}

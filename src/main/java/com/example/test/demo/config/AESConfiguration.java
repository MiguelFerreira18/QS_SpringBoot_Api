package com.example.test.demo.config;

import com.example.test.demo.util.AESUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AESConfiguration {
    @Bean
    public AESUtil aesUtil() {
        return new AESUtil();
    }
}

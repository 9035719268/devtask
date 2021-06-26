package com.gvozdev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Bean
    public ApplicationConfig getConfig() {
        return new ApplicationConfig();
    }
}

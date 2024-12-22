package com.inventrymanagement.inventry.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadConfig {

    @Bean
    public Executor executor(){
        return Executors.newCachedThreadPool();
    }
}

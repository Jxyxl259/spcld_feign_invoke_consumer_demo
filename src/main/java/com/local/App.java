package com.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients(basePackages = {"com.yaic.app"})
//@ComponentScan(basePackages = {"com.local","com.yaic.app"})
@EnableFeignClients(basePackages = {"com.local"})
public class App
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}

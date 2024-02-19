package com.order.system.customer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.order.system")
public class CustomerServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}

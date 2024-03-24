package com.order.system.container.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@EnableJpaRepositories(basePackages = { "com.order.system.domain", "com.order.system" })
//@EntityScan(basePackages = { "com.order.system.domain","com.order.system.dataaccess", "com.order.system"})
//@SpringBootApplication(scanBasePackages = "com.order.system")

@EnableJpaRepositories(basePackages = {
        "com.order.system.order.service.dataaccess",
        "com.order.system.dataaccess",
"com.order.system.data.customer.repository",
"com.order.system.data.order.repository"})
@EntityScan(basePackages = {
        "com.order.system.order.service.dataaccess",
        "com.order.system.dataaccess",
"com.order.system.data.customer.entity",
"com.order.system.data.order.entity"})
@SpringBootApplication(scanBasePackages = "com.order.system")
public class OrderServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

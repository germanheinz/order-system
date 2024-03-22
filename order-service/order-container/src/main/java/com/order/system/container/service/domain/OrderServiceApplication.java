package com.order.system.container.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.order.system", "com.order.system.dataaccess.restaurante.repository", "com.order.system.dataaccess.restaurante.repository"})
@EntityScan(basePackages = "com.order.system")
@SpringBootApplication(scanBasePackages ={ "com.order.system", "com.order.system.dataaccess.restaurante.repository","com.order.system.dataaccess.restaurante.repository"})
public class OrderServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

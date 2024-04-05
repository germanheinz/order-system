package com.order.system.restaurant.service.domain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = { "com.order.system.*", "com.order.system.restaurant.service.dataaccess.*","com.order.system.restaurant.service.dataaccess.*", "com.order.system.dataaccess"})
@EntityScan(basePackages = { "com.order.system.*","com.order.system.restaurant.service.dataaccess.*","com.order.system.dataaccess.*" })
@SpringBootApplication(scanBasePackages = "com.order.system.*")
public class RestaurantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
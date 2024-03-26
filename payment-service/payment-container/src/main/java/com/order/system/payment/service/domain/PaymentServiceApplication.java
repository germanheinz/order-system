package com.order.system.payment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.order.system", "com.order.system","com.order.system.data.customer" })
@EntityScan(basePackages = {"com.order.system", "com.order.system", "com.order.system.data.customer"})
@SpringBootApplication(scanBasePackages = {"com.order.system", "com.order.system"})
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}

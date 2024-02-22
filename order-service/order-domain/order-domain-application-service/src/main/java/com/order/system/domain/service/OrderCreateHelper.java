package com.order.system.domain.service;

import com.order.system.domain.core.OrderDomainService;
import com.order.system.domain.core.entity.Customer;
import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Restaurant;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.exception.OrderDomainException;
import com.order.system.domain.service.dto.create.CreateOrderCommand;
import com.order.system.domain.service.mapper.OrderDataMapper;
import com.order.system.domain.service.ports.ouput.repository.CustomerRepository;
import com.order.system.domain.service.ports.ouput.repository.OrderRepository;
//import com.order.system.domain.service.ports.ouput.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

//    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
//                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
//        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
//        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, null);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId());
        return orderCreatedEvent;
    }

//    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
//        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
//        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
//        if (optionalRestaurant == null) {
//            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId());
//            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
//                    createOrderCommand.getRestaurantId());
//        }
//        return optionalRestaurant.get();
//    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer == null) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customer);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId());
        return orderResult;
    }
}

package com.order.system.domain.service.mapper;


import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.entity.Product;
import com.order.system.domain.core.entity.Restaurant;
import com.order.system.domain.core.valueobject.StreetAddress;
import com.order.system.domain.service.dto.create.CreateOrderCommand;
import com.order.system.domain.service.dto.create.CreateOrderResponse;
import com.order.system.domain.service.dto.create.OrderAddress;
import com.order.system.domain.service.dto.create.OrderItem;
import com.order.system.domain.service.dto.track.TrackOrderResponse;
import com.order.system.domain.valueobject.CustomerId;
import com.order.system.domain.valueobject.Money;
import com.order.system.domain.valueobject.ProductId;
import com.order.system.domain.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {

        Product product = new Product(new ProductId(UUID.fromString(String.valueOf(createOrderCommand.getItems().get(0).getProductId()))),"test",new Money(createOrderCommand.getItems().get(0).getPrice()));
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(productList)
//                .products(createOrderCommand.getItems().stream().map(orderItem ->
//                        new Product(new ProductId(orderItem.getProductId())))
//                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.Builder.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private List<com.order.system.domain.core.entity.OrderItem> orderItemsToOrderItemEntities(
            List<OrderItem> orderItems) {

        List<com.order.system.domain.core.entity.OrderItem> orderTest = new ArrayList<>();
        return orderTest;
//        return orderItems.stream()
//                .map(orderItem ->
//                        com.order.system.domain.core.entity.OrderItem.Builder
//                                .product(new Product(new ProductId(orderItem.getProductId())))
//                                .price(new Money(orderItem.getPrice()))
//                                .quantity(orderItem.getQuantity())
//                                .subTotal(new Money(orderItem.getSubTotal()))
//                                .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}

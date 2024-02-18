package com.order.system.messaging.mapper;


import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.event.OrderCancelledEvent;
import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.domain.service.dto.message.PaymentResponse;
import com.order.system.domain.service.dto.message.RestaurantApprovalResponse;
import com.order.system.kafka.order.avro.model.*;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(com.order.system.domain.valueobject.PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponse
    approvalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel
                                                        restaurantApprovalResponseAvroModel) {
        return RestaurantApprovalResponse.builder()
                .id(restaurantApprovalResponseAvroModel.getId())
                .sagaId(restaurantApprovalResponseAvroModel.getSagaId())
                .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId())
                .orderId(restaurantApprovalResponseAvroModel.getOrderId())
                .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(com.order.system.domain.valueobject.OrderApprovalStatus.valueOf(
                        restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
                .build();
    }

//    public PaymentRequestAvroModel orderPaymentEventToPaymentRequestAvroModel(String sagaId, OrderPaymentEventPayload
//                                                                              orderPaymentEventPayload) {
//        return PaymentRequestAvroModel.newBuilder()
//                .setId(UUID.randomUUID().toString())
//                .setSagaId(sagaId)
//                .setCustomerId(orderPaymentEventPayload.getCustomerId())
//                .setOrderId(orderPaymentEventPayload.getOrderId())
//                .setPrice(orderPaymentEventPayload.getPrice())
//                .setCreatedAt(orderPaymentEventPayload.getCreatedAt().toInstant())
//                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(orderPaymentEventPayload.getPaymentOrderStatus()))
//                .build();
//    }

//    public RestaurantApprovalRequestAvroModel
//    orderApprovalEventToRestaurantApprovalRequestAvroModel(String sagaId, OrderApprovalEventPayload
//            orderApprovalEventPayload) {
//        return RestaurantApprovalRequestAvroModel.newBuilder()
//                .setId(UUID.randomUUID().toString())
//                .setSagaId(sagaId)
//                .setOrderId(orderApprovalEventPayload.getOrderId())
//                .setRestaurantId(orderApprovalEventPayload.getRestaurantId())
//                .setRestaurantOrderStatus(RestaurantOrderStatus
//                        .valueOf(orderApprovalEventPayload.getRestaurantOrderStatus()))
//                .setProducts(orderApprovalEventPayload.getProducts().stream().map(orderApprovalEventProduct ->
//                        com.order.system.kafka.order.avro.model.Product.newBuilder()
//                                .setId(orderApprovalEventProduct.getId())
//                                .setQuantity(orderApprovalEventProduct.getQuantity())
//                                .build()).collect(Collectors.toList()))
//                .setPrice(orderApprovalEventPayload.getPrice())
//                .setCreatedAt(orderApprovalEventPayload.getCreatedAt().toInstant())
//                .build();
//    }

//    public CustomerModel customerAvroModeltoCustomerModel(CustomerAvroModel customerAvroModel) {
//        return CustomerModel.builder()
//                .id(customerAvroModel.getId())
//                .username(customerAvroModel.getUsername())
//                .firstName(customerAvroModel.getFirstName())
//                .lastName(customerAvroModel.getLastName())
//                .build();
//    }

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent){
        Order order = orderCreatedEvent.getOrder();
                return PaymentRequestAvroModel.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setSagaId("")
                        .setCustomerId(order.getCustomerId().toString())
                        .setOrderId(order.getId().toString())
                        .setPrice(order.getPrice().getAmount())
                        .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                        .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                        .build();
    }

    public PaymentRequestAvroModel orderCancelEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelEvent){
        Order order = orderCancelEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().toString())
                .setOrderId(order.getId().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }

    public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent){
        Order order = orderPaidEvent.getOrder();

        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.getId().toString())
                .setRestaurantId(order.getRestaurantId().toString())
                .setOrderId(order.getId().toString())
                .setRestaurantOrderStatus(RestaurantOrderStatus.valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem -> com.order.system.kafka.order.avro.model.Product.newBuilder()
                        .setId(orderItem.getProduct().getId().toString())
                        .setQuantity(orderItem.getQuantity())
                        .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                .build();
    }
}

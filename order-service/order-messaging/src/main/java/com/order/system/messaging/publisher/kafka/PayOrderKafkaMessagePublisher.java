package com.order.system.messaging.publisher.kafka;

import com.order.system.domain.core.event.OrderPaidEvent;
import com.order.system.domain.service.config.OrderServiceConfigData;
import com.order.system.domain.service.ports.ouput.message.publisher.payment.OrderPaidRestaurantRequestMessagePublisher;
import com.order.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.order.system.kafka.producer.OrderKafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;


    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().toString();

        RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel = orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);

        //TODO
        kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                orderId,
                restaurantApprovalRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                                restaurantApprovalRequestAvroModel,
                                orderId,
                                "RestaurantApprovalRequestAvroModel"));

        log.info("RestaurantApprovalRequestAvroModel sent to kafka for order id: {}", orderId);
    }
}

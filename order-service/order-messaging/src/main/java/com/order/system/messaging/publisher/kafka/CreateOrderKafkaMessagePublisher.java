package com.order.system.messaging.publisher.kafka;

import com.order.system.domain.core.event.OrderCreatedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.domain.service.config.OrderServiceConfigData;
import com.order.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.order.system.kafka.producer.OrderKafkaMessageHelper;
import com.order.system.kafka.producer.service.KafkaProducer;
import com.order.system.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderKafkaMessagePublisher implements DomainEventPublisher<OrderCreatedEvent> {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public CreateOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {

        String orderId = domainEvent.getOrder().getId().toString();
        log.info("Receiving orderCreateEvent for order id: {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);

            kafkaProducer.send(
                    orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(
                            orderServiceConfigData.getPaymentResponseTopicName(),
                            paymentRequestAvroModel, orderId, "PaymentRequestAvroModel"));

            log.info("PaymentRequestAvroModel sent to Kafka for Order id: {}", paymentRequestAvroModel.getOrderId());
        }catch (Exception e){
            log.info("e", e);
        }
    }
}

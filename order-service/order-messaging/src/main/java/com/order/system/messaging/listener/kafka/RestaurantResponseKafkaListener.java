package com.order.system.messaging.listener.kafka;

import com.order.system.domain.service.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import com.order.system.kafka.comsumer.KafkaConsumer;
import com.order.system.kafka.order.avro.model.OrderApprovalStatus;
import com.order.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.order.system.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.order.system.domain.core.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
public class RestaurantResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public RestaurantResponseKafkaListener(RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.restaurantApprovalResponseMessageListener = restaurantApprovalResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }


    @Override
    @KafkaListener(id = "{kafka-consumer-config.restaurant-approval-consumer-group-id}",
               topics = "{order-service.restaurant-approval-response-topic-name}")
    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of restaurant approval responses received with keys: {}, partitions> {} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets
                );

        messages.forEach(restaurantApprovalResponseAvroModel -> {
            if(OrderApprovalStatus.APPROVED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()){
                log.info("Processing succesful Restaurant for order id: {}", restaurantApprovalResponseAvroModel.getOrderId());

                restaurantApprovalResponseMessageListener.orderApproved(orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));

            } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()){
                log.info("Processing rejected order for order id: {}, with failure messages: {}", restaurantApprovalResponseAvroModel.getOrderId(), String.join(FAILURE_MESSAGE_DELIMITER, restaurantApprovalResponseAvroModel.getFailureMessages()));
                restaurantApprovalResponseMessageListener.orderRejected(orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
            }
        });
    }

}

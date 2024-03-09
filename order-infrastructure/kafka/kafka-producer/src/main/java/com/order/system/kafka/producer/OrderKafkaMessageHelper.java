package com.order.system.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderKafkaMessageHelper {

    public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallback(String requestTopicName, T requestAvroModel, String orderId, String requestAvroModelName) {

        return (result, ex) -> {
            if(ex == null){
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from kafka for order id: {}" +
                        "Topics> {} Partition: {} Offset> {} Timestamp: {}",
                        orderId,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            } else {
                log.info("Error while sending" + requestAvroModelName + "message {} to topic {}", requestAvroModel.toString(), requestTopicName, ex);
            }
        };

    }

}

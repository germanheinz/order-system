package com.order.system.messaging.publisher.kafka;

import com.order.system.kafka.order.avro.model.PaymentRequestAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderKafkaMessageHelper {

    public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallback(String paymentRequestTopicName, T requestAvroModel, String orderId, String requestAvroModelName) {

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
                log.info("Error while sending" + requestAvroModelName + "message {} to topic {}", requestAvroModel.toString(), paymentRequestTopicName, ex);
            }
        };

    }

}

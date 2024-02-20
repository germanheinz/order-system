package com.order.system.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.system.domain.core.exception.OrderDomainException;
import com.order.system.kafka.order.avro.model.CustomerAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaMessageHelper {

    private final ObjectMapper objectMapper;

    public KafkaMessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new OrderDomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }

    public BiConsumer<SendResult<String, CustomerAvroModel>, Throwable>
    getKafkaCallback(String responseTopicName, CustomerAvroModel message){
//                     BiConsumer<U, OutboxStatus> outboxCallback,

          return (result, ex) -> {
            if(ex == null){
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for" +
                                " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            } else {
                log.error("Error while sending with message: {} to topic {}",
                        message.toString(), responseTopicName, ex);
            }
        };
    }
}

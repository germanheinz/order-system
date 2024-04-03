package com.order.system.domain.service.outbox.scheduler.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.order.system.config.kafka.SagaStatus;
import com.order.system.domain.core.exception.OrderDomainException;
import com.order.system.domain.service.outbox.model.payment.OrderPaymentEventPayload;
import com.order.system.domain.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.order.system.domain.service.ports.ouput.repository.PaymentOutboxRepository;
import com.order.system.domain.valueobject.OrderStatus;
import com.order.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.order.system.config.kafka.SagaConstants.ORDER_SAGA_NAME;


@Slf4j
@Component
public class PaymentOutboxHelper {

    private final PaymentOutboxRepository paymentOutboxRepository;
    private final ObjectMapper objectMapper;

    public PaymentOutboxHelper(PaymentOutboxRepository paymentOutboxRepository,
                               ObjectMapper objectMapper) {
        this.paymentOutboxRepository = paymentOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<List<OrderPaymentOutboxMessage>> getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME,
                outboxStatus,
                sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<OrderPaymentOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID sagaId,
                                                                                            SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    public void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
       OrderPaymentOutboxMessage response = paymentOutboxRepository.save(orderPaymentOutboxMessage);
       if (response == null) {
           log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}", orderPaymentOutboxMessage.getId());
           throw new OrderDomainException("Could not save OrderPaymentOutboxMessage with outbox id: " +
                   orderPaymentOutboxMessage.getId());
       }
       log.info("OrderPaymentOutboxMessage saved with outbox id: {}", orderPaymentOutboxMessage.getId());
    }

    @Transactional
    public void savePaymentOutboxMessage(OrderPaymentEventPayload paymentEventPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(OrderPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentEventPayload.getCreatedAt())
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(paymentEventPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .version(1).build());
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatus) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatus);
    }

    private String createPayload(OrderPaymentEventPayload paymentEventPayload) {
        try {
            return objectMapper.writeValueAsString(paymentEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderPaymentEventPayload object for order id: {}",
                    paymentEventPayload.getOrderId(), e);
            throw new OrderDomainException("Could not create OrderPaymentEventPayload object for order id: " +
                    paymentEventPayload.getOrderId(), e);
        }
    }

}
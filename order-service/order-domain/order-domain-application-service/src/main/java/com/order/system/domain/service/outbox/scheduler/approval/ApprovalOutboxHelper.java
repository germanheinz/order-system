package com.order.system.domain.service.outbox.scheduler.approval;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.order.system.config.kafka.SagaStatus;
import com.order.system.domain.core.exception.OrderDomainException;
import com.order.system.domain.service.outbox.model.approval.OrderApprovalEventPayload;
import com.order.system.domain.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.order.system.domain.service.ports.ouput.repository.ApprovalOutboxRepository;
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
public class ApprovalOutboxHelper {

    private final ApprovalOutboxRepository approvalOutboxRepository;
    private final ObjectMapper objectMapper;

    public ApprovalOutboxHelper(ApprovalOutboxRepository approvalOutboxRepository, ObjectMapper objectMapper) {
        this.approvalOutboxRepository = approvalOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<List<OrderApprovalOutboxMessage>>
    getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return approvalOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME,
                outboxStatus,
                sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<OrderApprovalOutboxMessage>
    getApprovalOutboxMessageBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatus) {
        return approvalOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    public void save(OrderApprovalOutboxMessage orderApprovalOutboxMessage) {
        OrderApprovalOutboxMessage response = approvalOutboxRepository.save(orderApprovalOutboxMessage);
        if (response == null) {
            log.error("Could not save OrderApprovalOutboxMessage with outbox id: {}",
                    orderApprovalOutboxMessage.getId());
            throw new OrderDomainException("Could not save OrderApprovalOutboxMessage with outbox id: " +
                    orderApprovalOutboxMessage.getId());
        }
        log.info("OrderApprovalOutboxMessage saved with outbox id: {}", orderApprovalOutboxMessage.getId());
    }

    @Transactional
    public void saveApprovalOutboxMessage(OrderApprovalEventPayload orderApprovalEventPayload,
                                          OrderStatus orderStatus,
                                          SagaStatus sagaStatus,
                                          OutboxStatus outboxStatus,
                                          UUID sagaId) {
        save(OrderApprovalOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(orderApprovalEventPayload.getCreatedAt())
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(orderApprovalEventPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .version(1).build());
    }

    @Transactional
    public void deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                       SagaStatus... sagaStatus) {
        approvalOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatus);
    }

    private String createPayload(OrderApprovalEventPayload orderApprovalEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderApprovalEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderApprovalEventPayload for order id: {}",
                    orderApprovalEventPayload.getOrderId(), e);
            throw new OrderDomainException("Could not create OrderApprovalEventPayload for order id: " +
                    orderApprovalEventPayload.getOrderId(), e);
        }
    }

}

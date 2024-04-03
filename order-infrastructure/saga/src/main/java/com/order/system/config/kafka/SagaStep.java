package com.order.system.config.kafka;

import com.order.system.domain.event.DomainEvent;

public interface SagaStep <T>{
    void process(T data);
    void rollback(T data);
}

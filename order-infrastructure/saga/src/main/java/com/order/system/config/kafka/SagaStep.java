package com.order.system.config.kafka;

import com.order.system.domain.event.DomainEvent;

public interface SagaStep <T, S extends DomainEvent, U extends DomainEvent>{
    S process(T data);
    U rollback(T data);
}

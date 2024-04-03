package com.order.system.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}

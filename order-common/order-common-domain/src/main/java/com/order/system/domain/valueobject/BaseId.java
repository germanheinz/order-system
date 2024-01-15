package com.order.system.domain.valueobject;

public abstract class BaseId<UUID> {
    private final UUID id;

    protected BaseId(UUID id) {
        this.id = id;
    }
}

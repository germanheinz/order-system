package com.order.system.domain.service.dto.create;

import com.order.system.domain.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    @NotNull
    private final UUID orderTackingId;
    @NotNull
    private final OrderStatus orderStatus;
    @NotNull
    private final String message;
}

package com.order.system.restaurant.service.domain;

import com.order.system.restaurant.service.domain.entity.Restaurant;
import com.order.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.order.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.order.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.order.system.domain.event.publisher.DomainEventPublisher;
import com.order.system.domain.valueobject.OrderApprovalStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.order.system.domain.DomainConstants.UTC;


@Slf4j
public class RestaurantDomainServiceImpl implements RestaurantDomainService {

    @Override
    public OrderApprovalEvent validateOrder(Restaurant restaurant, List<String> failureMessages) {
        restaurant.validateOrder(failureMessages);
        log.info("Validating order with id: {}", restaurant.getOrderDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info("Order is approved for order id: {}", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(restaurant.getOrderApproval(),
                    restaurant.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)));
        } else {
            log.info("Order is rejected for order id: {}", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.REJECTED);
            return new OrderRejectedEvent(restaurant.getOrderApproval(),
                    restaurant.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)));
        }
    }
}

package com.order.system.domain.service.ports.ouput.repository;



import com.order.system.domain.core.entity.Order;
import com.order.system.domain.core.valueobject.TrackingId;
import com.order.system.domain.valueobject.OrderId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId orderId);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}

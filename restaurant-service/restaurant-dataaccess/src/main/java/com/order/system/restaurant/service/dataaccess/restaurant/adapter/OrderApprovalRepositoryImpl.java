package com.order.system.restaurant.service.dataaccess.restaurant.adapter;

import com.order.system.restaurant.service.dataaccess.restaurant.repository.OrderApprovalJpaRepository;
import com.order.system.restaurant.service.domain.entity.OrderApproval;
import com.order.system.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import org.springframework.stereotype.Component;

import com.order.system.restaurant.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;


@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public OrderApprovalRepositoryImpl(OrderApprovalJpaRepository orderApprovalJpaRepository,
                                       RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.orderApprovalJpaRepository = orderApprovalJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        return restaurantDataAccessMapper
                .orderApprovalEntityToOrderApproval(orderApprovalJpaRepository
                        .save(restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)));
    }

}

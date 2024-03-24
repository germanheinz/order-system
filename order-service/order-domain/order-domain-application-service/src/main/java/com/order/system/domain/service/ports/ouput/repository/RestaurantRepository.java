package com.order.system.domain.service.ports.ouput.repository;

import com.order.system.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}

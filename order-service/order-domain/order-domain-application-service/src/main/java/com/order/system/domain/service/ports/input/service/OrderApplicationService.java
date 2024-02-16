package com.order.system.domain.service.ports.input.service;



import com.order.system.domain.service.dto.create.CreateOrderCommand;
import com.order.system.domain.service.dto.create.CreateOrderResponse;
import com.order.system.domain.service.dto.track.TrackOrderQuery;
import com.order.system.domain.service.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}

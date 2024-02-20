package com.order.system.data.customer.adapter;

import com.order.system.data.customer.mapper.CustomerDataAccessMapper;
import com.order.system.data.customer.repository.CustomerJpaRepository;
import com.order.system.domain.core.entity.Customer;
import com.order.system.domain.service.ports.ouput.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }

}

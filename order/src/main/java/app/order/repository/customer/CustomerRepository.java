package app.order.repository.customer;

import app.order.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<CustomerEntity> findAll();
    CustomerEntity save(CustomerEntity e);
    Optional<CustomerEntity> findByPhoneNumber(String nr);
}

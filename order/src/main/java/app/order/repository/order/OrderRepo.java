package app.order.repository.order;

import app.order.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepo {
    List<OrderEntity> findAll();
    OrderEntity save(OrderEntity e);
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
}

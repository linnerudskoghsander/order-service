package app.order.repository.order;

import app.order.entity.OrderDetailsEntity;

import java.util.List;

public interface OrderDetailsRepo {
    List<OrderDetailsEntity> findAll();
    OrderDetailsEntity save(OrderDetailsEntity e);
}

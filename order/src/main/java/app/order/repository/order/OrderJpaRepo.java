package app.order.repository.order;

import app.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepo extends OrderRepo, JpaRepository<OrderEntity, Long> {
}

package app.order.repository.order;

import app.order.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsJpaRepo extends OrderDetailsRepo, JpaRepository<OrderDetailsEntity, Long> {
}

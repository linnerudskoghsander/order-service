package app.order.repository.item;

import app.order.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJpaRepo extends ItemRepo, JpaRepository<ItemEntity, Long> {
}

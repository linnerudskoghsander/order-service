package app.order.repository.item;

import app.order.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<ItemEntity> findAll();
    ItemEntity save(ItemEntity e);
    Optional<ItemEntity> findByItemNumber(String itemNumber);
}

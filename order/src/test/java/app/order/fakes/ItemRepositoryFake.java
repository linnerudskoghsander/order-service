package app.order.fakes;

import app.order.entity.ItemEntity;
import app.order.repository.item.ItemRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ItemRepositoryFake implements ItemRepository {
    private final HashMap<Long, ItemEntity> db = new HashMap<>();

    @Override
    public List<ItemEntity> findAll() {
        return db.values().stream().toList();
    }

    // TODO: ikke fullt fungerende. Setter aldri PK
    public ItemEntity save(ItemEntity e) {
        db.put(nextId(), e);
        return e;
    }

    @Override
    public Optional<ItemEntity> findByItemNumber(String itemNumber) {
        return db.values().stream()
                .filter(e -> e.itemNumber().equals(itemNumber))
                .findFirst();
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}

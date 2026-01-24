package app.order.fakes;

import app.order.domain.item.Item;
import app.order.entity.ItemEntity;
import app.order.entity.OrderDetailsEntity;
import app.order.repository.item.ItemRepo;
import app.order.repository.item.ItemAdapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class ItemRepoFake implements ItemRepo {
    private final LinkedHashMap<Long, ItemEntity> db = new LinkedHashMap<>();

    @Override
    public List<ItemEntity> findAll() {
        return db.values().stream().toList();
    }

    public void save(Item d) {
        db.put(nextId(), ItemAdapter.toEntity(d));
    }

    @Override
    public ItemEntity save(ItemEntity e) {
        if (containsElement(e)) {
            db.replace(getId(e), e);
        } else {
            db.put(nextId(), e);
        }
        return e;
    }

    @Override
    public Optional<ItemEntity> findByItemNumber(String itemNumber) {
        return db.values().stream()
                .filter(e -> e.itemNumber().equals(itemNumber))
                .findFirst();
    }

    private boolean containsElement(ItemEntity e) {
        var o = db.values().stream()
                .filter(v -> v.itemNumber().equals(e.itemNumber()))
                .findFirst();
        return o.isPresent();
    }

    private Long getId(ItemEntity e) {
        return db.entrySet().stream()
                .filter(x -> x.getValue().itemNumber().equals(e.itemNumber()))
                .findFirst()
                .orElseThrow()
                .getKey();
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}

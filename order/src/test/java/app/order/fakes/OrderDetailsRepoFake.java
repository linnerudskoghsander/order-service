package app.order.fakes;

import app.order.entity.OrderDetailsEntity;
import app.order.repository.order.OrderDetailsRepo;

import java.util.HashMap;
import java.util.List;

public class OrderDetailsRepoFake implements OrderDetailsRepo {
    private final HashMap<Long, OrderDetailsEntity> db = new HashMap<>();

    @Override
    public List<OrderDetailsEntity> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public OrderDetailsEntity save(OrderDetailsEntity e) {
        return db.put(nextId(), e);
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}

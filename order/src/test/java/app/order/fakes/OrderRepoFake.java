package app.order.fakes;

import app.order.entity.OrderEntity;
import app.order.repository.order.OrderRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class OrderRepoFake implements OrderRepo {
    private final HashMap<Long, OrderEntity> db = new HashMap<>();

    @Override
    public List<OrderEntity> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public OrderEntity save(OrderEntity e) {
        return db.put(nextId(), e);
    }

    @Override
    public Optional<OrderEntity> findByOrderNumber(String orderNumber) {
        return db.values().stream()
                .filter(e -> e.orderNumber().equals(orderNumber))
                .findFirst();
    }


    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}

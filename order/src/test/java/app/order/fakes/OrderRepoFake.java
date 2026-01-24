package app.order.fakes;

import app.order.entity.ItemEntity;
import app.order.entity.OrderEntity;
import app.order.repository.order.OrderRepo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class OrderRepoFake implements OrderRepo {
    private final LinkedHashMap<Long, OrderEntity> db = new LinkedHashMap<>();

    @Override
    public List<OrderEntity> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public OrderEntity save(OrderEntity e) {
        if (containsElement(e)) {
            db.replace(getId(e), e);
        } else {
            db.put(nextId(), e);
        }
        return e;
    }

    @Override
    public Optional<OrderEntity> findByOrderNumber(String orderNumber) {
        return db.values().stream()
                .filter(e -> e.orderNumber().equals(orderNumber))
                .findFirst();
    }

    private boolean containsElement(OrderEntity e) {
        var o = db.values().stream()
                .filter(v -> v.orderNumber()
                        .equals(e.orderNumber())
                ).findFirst();
        return o.isPresent();
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }

    private Long getId(OrderEntity e) {
        return db.entrySet().stream()
                .filter(x -> x.getValue().orderNumber().equals(e.orderNumber()))
                .findFirst()
                .orElseThrow()
                .getKey();
    }
}

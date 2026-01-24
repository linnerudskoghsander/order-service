package app.order.fakes;

import app.order.entity.OrderDetailsEntity;
import app.order.repository.order.OrderDetailsRepo;

import java.util.LinkedHashMap;
import java.util.List;

public class OrderDetailsRepoFake implements OrderDetailsRepo {
    private final LinkedHashMap<Long, OrderDetailsEntity> db = new LinkedHashMap<>();

    @Override
    public List<OrderDetailsEntity> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public OrderDetailsEntity save(OrderDetailsEntity e) {
        if (containsElement(e)) {
            db.replace(getId(e), e);
        } else {
            db.put(nextId(), e);
        }
        return e;
    }

    private Long getId(OrderDetailsEntity e) {
        return db.entrySet().stream()
                .filter(x -> x.getValue().equals(e))
                .findFirst()
                .orElseThrow()
                .getKey();
    }

    private boolean containsElement(OrderDetailsEntity e) {
        var o = db.values().stream()
                .filter(v -> v.equals(e))
                .findFirst();
        return o.isPresent();
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}

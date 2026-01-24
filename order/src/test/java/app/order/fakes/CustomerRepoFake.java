package app.order.fakes;

import app.order.entity.CustomerEntity;
import app.order.repository.customer.CustomerRepo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class CustomerRepoFake implements CustomerRepo {
    private final LinkedHashMap<Long, CustomerEntity> db = new LinkedHashMap<>();

    @Override
    public List<CustomerEntity> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public CustomerEntity save(CustomerEntity e) {
        if (containsElement(e)) {
            db.replace(getId(e), e);
        } else {
            db.put(nextId(), e);
        }
        return e;
    }

    @Override
    public Optional<CustomerEntity> findByPhoneNumber(String nr) {
        return db.values().stream()
                .filter(e -> e.phoneNumber().equals(nr))
                .findFirst();
    }

    private boolean containsElement(CustomerEntity e) {
        var o = db.values().stream()
                .filter(v -> v.phoneNumber().equals(e.phoneNumber()))
                .findFirst();
        return o.isPresent();
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }

    private Long getId(CustomerEntity e) {
        return db.entrySet().stream()
                .filter(x -> x.getValue().phoneNumber().equals(e.phoneNumber()))
                .findFirst()
                .orElseThrow()
                .getKey();
    }
}

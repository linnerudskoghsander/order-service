package app.order.fakes;

import app.order.entity.CustomerEntity;
import app.order.repository.customer.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryFake implements CustomerRepository {
    private final HashMap<Long, CustomerEntity> db = new HashMap<>();

    @Override
    public List<CustomerEntity> findAll() {
        return db.values().stream().toList();
    }

    // TODO: ikke fullt fungerende. Setter aldri PK
    public CustomerEntity save(CustomerEntity e) {
        db.put(nextId(), e);
        return e;
    }

    @Override
    public Optional<CustomerEntity> findByPhoneNumber(String nr) {
        return db.values().stream()
                .filter(e -> e.phoneNumber().equals(nr))
                .findFirst();
    }

    private Long nextId() {
        return db.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}

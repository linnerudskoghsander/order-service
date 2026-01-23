package app.order.fakes;

import app.order.domain.customer.Customer;
import app.order.entity.CustomerEntity;
import app.order.repository.customer.CustomerRepo;
import app.order.repository.customer.CustomerAdapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class CustomerRepoFake implements CustomerRepo {
    private final LinkedHashMap<Long, CustomerEntity> db = new LinkedHashMap<>();

    @Override
    public List<CustomerEntity> findAll() {
        return db.values().stream().toList();
    }

    public void save(Customer d) {
        db.put(nextId(), CustomerAdapter.toEntity(d));
    }

    @Override
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

package app.order.service;

import app.order.domain.customer.ContactInfo;
import app.order.domain.customer.Customer;
import app.order.repository.customer.CustomerRepo;
import app.order.repository.customer.CustomerAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo repo;

    public CustomerService(CustomerRepo repo) {
        this.repo = repo;
    }

    public List<Customer> getAll() {
        return repo.findAll().stream()
                .map(CustomerAdapter::toDomain)
                .toList();
    }

    public void save(Customer d) {
        repo.save(CustomerAdapter.toEntity(d));
    }

    public Optional<Customer> find(ContactInfo.PhoneNumber nr) {
        return repo.findByPhoneNumber(nr.value())
                .map(CustomerAdapter::toDomain);
    }
}

package app.order.service;

import app.order.domain.customer.Customer;
import app.order.domain.customer.ContactInfo;
import app.order.domain.customer.Name;
import app.order.domain.customer.ShippingInfo;
import app.order.entity.CustomerEntity;
import app.order.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAll() {
        return repo.findAll().stream()
                .map(CustomerService::toDomain)
                .toList();
    }

    public void save(Customer d) {
        repo.save(toEntity(d));
    }

    public Optional<Customer> find(ContactInfo.PhoneNumber nr) {
        return repo.findByPhoneNumber(nr.value())
                .map(CustomerService::toDomain);
    }

    public static Customer toDomain(CustomerEntity e) {
        return new Customer(
                new Name(
                        requireNonNull(e.firstName()),
                        Optional.ofNullable(e.middleName()),
                        requireNonNull(e.lastName())
                ),
                new ContactInfo(
                        new ContactInfo.PhoneNumber(requireNonNull(e.phoneNumber())),
                        new ContactInfo.Email(requireNonNull(e.email()))
                ),
                new ShippingInfo(
                        new ShippingInfo.Address(requireNonNull(e.address())),
                        new ShippingInfo.StreetNumber(Integer.parseInt(requireNonNull(e.streetNumber()))),
                        new ShippingInfo.PostalCode(Integer.parseInt(requireNonNull(e.postalCode())))
                )
        );
    }

    public static CustomerEntity toEntity(Customer d) {
        return new CustomerEntity(
                requireNonNull(d.name().firstname()),
                d.name().middlename().orElse(null),
                requireNonNull(d.name().lastname()),
                requireNonNull(d.contactInfo().phoneNumber().value()),
                requireNonNull(d.contactInfo().email().value()),
                requireNonNull(d.shippingInfo().address().value()),
                String.valueOf(d.shippingInfo().streetNumber().value()),
                String.valueOf(d.shippingInfo().postalCode().value())
        );
    }
}

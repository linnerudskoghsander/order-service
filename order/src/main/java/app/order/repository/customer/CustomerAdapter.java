package app.order.repository.customer;

import app.order.domain.customer.ContactInfo;
import app.order.domain.customer.Customer;
import app.order.domain.customer.Name;
import app.order.domain.customer.ShippingInfo;
import app.order.entity.CustomerEntity;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class CustomerAdapter {

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
                requireNonNull(d.name().firstName()),
                d.name().middleName().orElse(null),
                requireNonNull(d.name().lastName()),
                requireNonNull(d.contactInfo().phoneNumber().value()),
                requireNonNull(d.contactInfo().email().value()),
                requireNonNull(d.shippingInfo().address().value()),
                String.valueOf(d.shippingInfo().streetNumber().value()),
                String.valueOf(d.shippingInfo().postalCode().value())
        );
    }
}


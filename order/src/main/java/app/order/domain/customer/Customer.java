package app.order.domain.customer;

public record Customer(
        Name name,
        ContactInfo contactInfo,
        ShippingInfo shippingInfo
) {
}

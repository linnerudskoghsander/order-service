package app.order.domain.customer;

public record ContactInfo(
        PhoneNumber phoneNumber,
        Email email
) {
    public record PhoneNumber(String value) {}
    public record Email(String value) {}
}

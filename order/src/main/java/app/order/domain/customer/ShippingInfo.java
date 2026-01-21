package app.order.domain.customer;

public record ShippingInfo(
        Address address,
        StreetNumber streetNumber,
        PostalCode postalCode
) {
    public record Address(String value){}
    public record StreetNumber(int value){}
    public record PostalCode(int value) {}
}

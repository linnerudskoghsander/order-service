package app.order.domain.customer;

import java.util.Optional;

public record Name(
        String firstName,
        Optional<String> middleName,
        String lastName
) {
}

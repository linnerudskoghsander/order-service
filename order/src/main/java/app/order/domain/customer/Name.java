package app.order.domain.customer;

import java.util.Optional;

public record Name(
        String firstname,
        Optional<String> middlename,
        String lastname
) {
}

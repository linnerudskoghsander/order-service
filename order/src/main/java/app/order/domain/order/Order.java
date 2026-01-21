package app.order.domain.order;

import app.order.domain.customer.Customer;
import app.order.domain.item.Item;

public record Order(
        Customer customer,
        Item item
) {
}

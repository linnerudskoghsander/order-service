package app.order.domain.order;

import app.order.domain.customer.Customer;

import java.util.List;

public record Order(
        OrderNumber number,
        OrderStatus status,
        Customer customer,
        List<OrderDetails> details
) {
}

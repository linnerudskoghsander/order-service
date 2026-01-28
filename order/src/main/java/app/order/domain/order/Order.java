package app.order.domain.order;

import app.order.config.OrderStatusException;
import app.order.domain.customer.Customer;

import java.util.List;

public record Order(
        OrderNumber number,
        OrderStatus status,
        Customer customer,
        List<OrderDetails> details
) {
    public static Order create(OrderNumber nr, Customer c, List<OrderDetails> ods) {
        return new Order(
                nr,
                OrderStatus.CREATED,
                c,
                ods
        );
    }

    public Order confirm() {
        return switch (this.status()) {
            case CREATED -> new Order(
                    number(),
                    OrderStatus.CONFIRMED,
                    customer(),
                    details()
            );
            case CANCELLED, CONFIRMED -> throw new OrderStatusException(
                    "Cannot confirm an order that got status %s".formatted(this.status())
            );
        };
    }

    public Order cancel() {
        return switch (this.status()) {
            case CREATED -> new Order(
                    number(),
                    OrderStatus.CANCELLED,
                    customer(),
                    details()
            );
            case CANCELLED, CONFIRMED -> throw new OrderStatusException(
                    "Cannot cancel an order that got status %s".formatted(this.status())
            );
        };
    }
}

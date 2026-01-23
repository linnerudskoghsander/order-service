package app.order.domain.order;

import app.order.domain.item.Item;

public record OrderDetails(
        Item item,
        int amount
) {
    public OrderDetails {
        if (amount < 0) throw new IllegalArgumentException("Order amount must be larger than 0 (%s)".formatted(amount));
    }
}

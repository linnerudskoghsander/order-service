package app.order.domain.order;

import app.order.config.OutOfStockException;
import app.order.domain.item.Item;

public record OrderDetails(
        Item item,
        int amount
) {
    public OrderDetails {
        if (amount < 0) throw new IllegalArgumentException("Order amount must be larger than 0 (%s)".formatted(amount));
    }

    public Item order() {
        if ((item().quantityLeft( ) - amount) < 0)
            throw new OutOfStockException("Not enough in stock of item: %s. Tried to order %s, but was only %s left."
                    .formatted(item().name().value(), amount, item().quantityLeft()));

        return new Item(
                item().itemNumber(),
                item().name(),
                item().description(),
                item().price(),
                item().quantityLeft() - amount
        );
    }

    public Item cancelOrder() {
        return new Item(
                item().itemNumber(),
                item().name(),
                item().description(),
                item().price(),
                item().quantityLeft() + amount
        );
    }
}

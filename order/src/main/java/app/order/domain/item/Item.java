package app.order.domain.item;

import app.order.config.OutOfStockException;

import java.math.BigDecimal;

public record Item(
        ItemNumber itemNumber,
        Name name,
        Description description,
        BigDecimal price,
        int quantityLeft
) {
    public Item {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be below 0: %s".formatted(price));
        }
        if (quantityLeft < 0) {
            throw new IllegalArgumentException("Quantity left cannot be below 0: %s".formatted(quantityLeft));
        }
    }

    public Item order(int amountOrdered) {
        if ((quantityLeft-amountOrdered) < 0)
            throw new OutOfStockException("Not enough in stock of item: %s. Tried to order %s, but was only %s left."
                .formatted(name().value(), amountOrdered, quantityLeft()));

        return new Item(
                itemNumber,
                name,
                description,
                price,
                quantityLeft-amountOrdered
        );
    }
}

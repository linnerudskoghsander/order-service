package app.order.domain.item;

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

    public Item reduceQuantity(int n) {
        int newQuantity = quantityLeft - n;
        if (newQuantity < 0) throw new IllegalArgumentException("Quantity left cannot be below 0: %s".formatted(newQuantity));
        return new Item(
                itemNumber,
                name,
                description,
                price,
                newQuantity
        );
    }

}

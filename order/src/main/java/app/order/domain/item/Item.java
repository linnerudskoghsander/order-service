package app.order.domain.item;

public record Item(
        ItemNumber itemNumber,
        Name name,
        Description description,
        double prize,
        int quantityLeft
) {
    public Item {
        if (prize < 0) {
            throw new IllegalArgumentException("Prize cannot be below 0: %s".formatted(prize));
        }
        if (quantityLeft < 0) {
            throw new IllegalArgumentException("Quantity left cannot be below 0: %s".formatted(quantityLeft));
        }
    }
}

package app.order.domain.order;

import app.order.domain.item.ItemNumber;

import java.math.BigDecimal;

public record OrderDetails(
        ItemNumber itemNumber,
        BigDecimal priceAtPurchase,
        int amount
) {
    public OrderDetails {
        if (amount <= 0) throw new IllegalArgumentException("Order amount must be larger than 0 (%s)".formatted(amount));
    }
}

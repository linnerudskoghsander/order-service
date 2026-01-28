package app.order.repository.order;

import app.order.domain.item.ItemNumber;
import app.order.domain.order.OrderDetails;
import app.order.entity.OrderDetailsEntity;

public class OrderDetailsAdapter {

    public static OrderDetails toDomain(OrderDetailsEntity e) {
        return new OrderDetails(
                new ItemNumber(e.itemNumber()),
                e.unitPrice(),
                e.amount()
        );
    }

    public static OrderDetailsEntity toEntity(OrderDetails d) {
        return new OrderDetailsEntity(
                d.itemNumber().value(),
                d.priceAtPurchase(),
                d.amount()
        );
    }
}
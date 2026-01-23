package app.order.repository.order;

import app.order.domain.order.OrderDetails;
import app.order.entity.OrderDetailsEntity;
import app.order.entity.OrderEntity;
import app.order.repository.item.ItemAdapter;

public class OrderDetailsAdapter {

    public static OrderDetails toDomain(OrderDetailsEntity e) {
        return new OrderDetails(
                ItemAdapter.toDomain(e.item()),
                e.amount()
        );
    }

    public static OrderDetailsEntity toEntity(OrderDetails d) {
        return new OrderDetailsEntity(
                ItemAdapter.toEntity(d.item()),
                d.amount()
        );
    }
}

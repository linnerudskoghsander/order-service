package app.order.repository.order;

import app.order.domain.order.Order;
import app.order.domain.order.OrderNumber;
import app.order.entity.OrderEntity;
import app.order.repository.customer.CustomerAdapter;

import static java.util.Objects.requireNonNull;

public class OrderAdapter {

    public static Order toDomain(OrderEntity e) {
        return new Order(
                new OrderNumber(requireNonNull(e.orderNumber())),
                requireNonNull(e.status()),
                CustomerAdapter.toDomain(requireNonNull(e.customer())),
                e.orderDetails().stream().map(OrderDetailsAdapter::toDomain).toList()
        );
    }

    public static OrderEntity toEntity(Order d) {
        var details = d.details().stream().map(OrderDetailsAdapter::toEntity).toList();
        var e = new OrderEntity(
                d.number().value(),
                d.status(),
                CustomerAdapter.toEntity(d.customer()),
                details
        );
        details.forEach(x -> x.putOnOrder(e));
        return e;
    }
}

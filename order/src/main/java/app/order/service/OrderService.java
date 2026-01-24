package app.order.service;

import app.order.config.ResourceNotFoundException;
import app.order.domain.customer.Customer;
import app.order.domain.order.Order;
import app.order.domain.order.OrderDetails;
import app.order.domain.order.OrderNumber;
import app.order.entity.OrderEntity;
import app.order.repository.order.OrderAdapter;
import app.order.repository.order.OrderDetailsAdapter;
import app.order.repository.order.OrderDetailsRepo;
import app.order.repository.order.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ItemService itemService;
    private final OrderRepo orderRepo;
    private final OrderDetailsRepo orderDetailsRepo;

    public OrderService(
            ItemService itemService,
            OrderRepo orderRepo,
            OrderDetailsRepo orderDetailsRepo
    ) {
        this.itemService = itemService;
        this.orderRepo = orderRepo;
        this.orderDetailsRepo = orderDetailsRepo;
    }

    @Transactional
    public Order createOrder(Customer c, List<OrderDetails> ds) {
        // Oppdaterer Item slik at stock blir up-to-date
        var updatedDs = ds.stream().map(d -> {
            var updatedItem = d.order();
            itemService.save(updatedItem);
            return new OrderDetails(updatedItem, d.amount());
        }).toList();

        // Oppretter Order, lagrer OrderEntity, og mapper OrderDetailsEntity til OrderEntity den tilhører
        var order = Order.create(c, updatedDs);
        OrderEntity entity = OrderAdapter.toEntity(order);
        orderRepo.save(entity);

        // Lagrer OrderDetailsEntity med kobling til riktig OrderEntity
        entity.orderDetails().forEach(orderDetailsRepo::save);
        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(OrderAdapter::toDomain)
                .toList();
    }

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepo.findAll().stream()
                .map(OrderDetailsAdapter::toDomain)
                .toList();
    }

    public List<OrderDetails> findOrderDetail(OrderNumber nr) {
        var order = orderRepo.findByOrderNumber(nr.value()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "No order found on ordernumber %s".formatted(nr.value())
                )
        );
        return order.orderDetails().stream().map(
                OrderDetailsAdapter::toDomain
        ).toList();
    }

    public Order confirmOrder(Order o) {
        var confirmedOrder = o.confirm();
        orderRepo.save(OrderAdapter.toEntity(confirmedOrder));
        return confirmedOrder;
    }

    public Optional<Order> findOrder(OrderNumber nr) {
        return orderRepo.findByOrderNumber(nr.value())
                .map(OrderAdapter::toDomain);
    }

    @Transactional
    public Order cancelOrder(Order o) {
        var cancelledOrder = o.cancel();
        // Reversere stock-infoen på items og oppdater OrderDetails-tabell i DB
        cancelledOrder.details().forEach(d -> {
            var i = d.cancelOrder();
            orderDetailsRepo.save(OrderDetailsAdapter.toEntity(new OrderDetails(i, d.amount())));
        });
        // Oppdaterer Order-tabell i DB
        orderRepo.save(OrderAdapter.toEntity(cancelledOrder));
        return cancelledOrder;
    }

}

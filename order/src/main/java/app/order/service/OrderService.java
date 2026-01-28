package app.order.service;

import app.order.config.OutOfStockException;
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
        // Sjekk at amountOrdered ikke vil overgå stock på Item
        ds.forEach(d -> {
            var item = itemService.find(d.itemNumber()).orElseThrow();
            if (d.amount() > item.quantityLeft()) throw new OutOfStockException("Amount (%s) can't exceed the stock quantity (%s)".formatted(d.amount(), item.quantityLeft()));
        });

        // Oppretter Order, lagrer OrderEntity, og mapper OrderDetailsEntity til OrderEntity den tilhører
        var order = Order.create(OrderNumberFactory.getInstance().generate(), c, ds);
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

    @Transactional
    public Order confirmOrder(OrderNumber nr) {
        var o = orderRepo.findByOrderNumber(nr.value()).orElseThrow(
                () -> new ResourceNotFoundException("Couldn't find order with the ordernumber %s".formatted(nr.value()))
        );
        var confirmedOrder = OrderAdapter.toDomain(o).confirm();

        // Reduserer stock quantity på Items og oppdaterer DB
        confirmedOrder.details().forEach(od -> {
            var item = itemService.find(od.itemNumber()).orElseThrow();
            var ui = item.reduceQuantity(od.amount());
            itemService.save(ui);
        });
        orderRepo.save(OrderAdapter.toEntity(confirmedOrder));
        return confirmedOrder;
    }

    public Optional<Order> findOrder(OrderNumber nr) {
        return orderRepo.findByOrderNumber(nr.value())
                .map(OrderAdapter::toDomain);
    }

    @Transactional
    public Order cancelOrder(OrderNumber nr) {
        var o = orderRepo.findByOrderNumber(nr.value()).orElseThrow(
                () -> new ResourceNotFoundException("Couldn't find order with the ordernumber %s".formatted(nr.value()))
        );
        var cancelledOrder = OrderAdapter.toDomain(o).cancel();
        // Oppdaterer Order-tabell i DB
        orderRepo.save(OrderAdapter.toEntity(cancelledOrder));
        return cancelledOrder;
    }

}

package app.order.service;

import app.order.config.ResourceNotFoundException;
import app.order.domain.customer.Customer;
import app.order.domain.order.Order;
import app.order.domain.order.OrderDetails;
import app.order.domain.order.OrderNumber;
import app.order.domain.order.OrderStatus;
import app.order.entity.OrderEntity;
import app.order.repository.order.OrderAdapter;
import app.order.repository.order.OrderDetailsAdapter;
import app.order.repository.order.OrderDetailsRepo;
import app.order.repository.order.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            var updatedItem = d.item().order(d.amount());
            itemService.save(updatedItem);
            return new OrderDetails(updatedItem, d.amount());
        }).toList();

        // Oppretter Order, lagrer OrderEntity, og mapper OrderDetailsEntity til OrderEntity den tilhører
        var order = new Order(
                OrderNumberFactory.getInstance().generate(),
                OrderStatus.CREATED,
                c,
                updatedDs
        );
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



}

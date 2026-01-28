package app.order.service;

import app.order.config.OrderStatusException;
import app.order.config.OutOfStockException;
import app.order.domain.order.OrderDetails;
import app.order.domain.order.OrderStatus;
import app.order.fakes.ItemRepoFake;
import app.order.fakes.OrderDetailsRepoFake;
import app.order.fakes.OrderRepoFake;
import app.order.helper.CustomerBuilder;
import app.order.helper.ItemBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceTest {
    private final OrderService orderService;
    private final ItemService itemService;

    public OrderServiceTest() {
        ItemRepoFake itemRepoFake = new ItemRepoFake();
        this.orderService = new OrderService(
                new ItemService(
                        itemRepoFake
                ),
                new OrderRepoFake(),
                new OrderDetailsRepoFake()
        );
        this.itemService = new ItemService(itemRepoFake);
    }

    @Test
    void createOrder() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        var o = orderService.createOrder(c, List.of(new OrderDetails(i.itemNumber(), i.price(), 10)));
        var os = orderService.getAllOrders();

        assertEquals(1, os.size());
        assertEquals(o, os.getFirst());
    }

    @Test
    void correctConnectionWithOrderAndDetails() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        var o = orderService.createOrder(c, List.of(new OrderDetails(i.itemNumber(), i.price(), 10)));
        var od = orderService.findOrderDetail(o.number());
        var ods = orderService.getAllOrderDetails();

        assertEquals(1, ods.size());
        assertEquals(od.getFirst(), ods.getFirst());
    }

    @Test
    void confirmOrder() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        var o = orderService.createOrder(c, List.of(new OrderDetails(i.itemNumber(), i.price(), 10)));

        orderService.confirmOrder(o.number());

        var co = orderService.findOrder(o.number());
        assertEquals(OrderStatus.CONFIRMED, co.orElseThrow().status());
    }

    @Test
    void cancelOrder() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        var o = orderService.createOrder(c, List.of(new OrderDetails(i.itemNumber(), i.price(), 10)));

        orderService.cancelOrder(o.number());

        var co = orderService.findOrder(o.number());
        assertEquals(OrderStatus.CANCELLED, co.orElseThrow().status());
    }

    @Test
    void errorConfirmingOrder() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        OrderDetails od = new OrderDetails(i.itemNumber(), i.price(), 10);
        var o1 = orderService.createOrder(c, List.of(od));
        var o2 = orderService.createOrder(c, List.of(od));

        // Sett o1 som CONFIRMED, og o2 som CANCELLED før jeg prøver å sette CONFIRMED på begge to
        orderService.confirmOrder(o1.number());
        orderService.cancelOrder(o2.number());

        assertThrows(OrderStatusException.class, () -> orderService.confirmOrder(o1.number()));
        assertThrows(OrderStatusException.class, () -> orderService.confirmOrder(o2.number()));
    }

    @Test
    void errorCancellingOrder() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        OrderDetails od = new OrderDetails(i.itemNumber(), i.price(), 10);
        var o1 = orderService.createOrder(c, List.of(od));
        var o2 = orderService.createOrder(c, List.of(od));

        // Sett o1 som CONFIRMED, og o2 som CANCELLED før jeg prøver å sette CANCELLE på begge to
        orderService.confirmOrder(o1.number());
        orderService.cancelOrder(o2.number());

        assertThrows(OrderStatusException.class, () -> orderService.cancelOrder(o1.number()));
        assertThrows(OrderStatusException.class, () -> orderService.cancelOrder(o2.number()));
    }

    @Test
    void outOfStock() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        itemService.save(i);

        assertThrows(OutOfStockException.class, () -> orderService.createOrder(
                c,
                List.of(new OrderDetails(i.itemNumber(), i.price(), 1000000000))
        ));
    }
}

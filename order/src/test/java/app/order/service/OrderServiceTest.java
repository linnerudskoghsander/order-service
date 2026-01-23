package app.order.service;

import app.order.domain.order.OrderDetails;
import app.order.fakes.ItemRepoFake;
import app.order.fakes.OrderDetailsRepoFake;
import app.order.fakes.OrderRepoFake;
import app.order.helper.CustomerBuilder;
import app.order.helper.ItemBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {
    private final OrderService service;

    public OrderServiceTest() {
        this.service = new OrderService(
                new ItemService(
                        new ItemRepoFake()
                ),
                new OrderRepoFake(),
                new OrderDetailsRepoFake()
        );
    }

    @Test
    void createOrder() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        var o = service.createOrder(c, List.of(new OrderDetails(i, 10)));
        var os = service.getAllOrders();

        assertEquals(1, os.size());
        assertEquals(o, os.getFirst());
    }

    @Test
    void correctConnectionWithOrderAndDetails() {
        var c = CustomerBuilder.Customer();
        var i = ItemBuilder.Item();
        var o = service.createOrder(c, List.of(new OrderDetails(i, 10)));
        var od = service.findOrderDetail(o.number());
        var ods = service.getAllOrderDetails();

        assertEquals(1, ods.size());
        assertEquals(od.getFirst(), ods.getFirst());
    }
}

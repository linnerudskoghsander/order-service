package app.order.controller;

import app.order.domain.customer.Customer;
import app.order.domain.order.Order;
import app.order.domain.order.OrderDetails;
import app.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> order(@RequestBody CreateOrderRequest req) {
        return ResponseEntity.ok(service.createOrder(req.customer(), req.orderDetails()));
    }

    public record CreateOrderRequest(Customer customer, List<OrderDetails> orderDetails) {}
}

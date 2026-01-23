package app.order.service;

import app.order.domain.order.OrderNumber;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

public class OrderNumberFactory {
    private static OrderNumberFactory instance;
    private final AtomicLong counter = new AtomicLong(1);

    private OrderNumberFactory() {}

    public static synchronized OrderNumberFactory getInstance() {
        if (instance == null) {
            instance = new OrderNumberFactory();
        }
        return instance;
    }

    public OrderNumber generate() {
        String orderNumberValue = "ORD-" + String.format("%09d", counter.getAndIncrement());
        return new OrderNumber(orderNumberValue);
    }
}


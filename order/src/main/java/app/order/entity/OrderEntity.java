package app.order.entity;

import app.order.domain.order.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailsEntity> orderDetails;

    protected OrderEntity() {}

    public OrderEntity(String orderNumber, OrderStatus status, CustomerEntity customer, List<OrderDetailsEntity> orderDetails) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    public Long id() {
        return id;
    }

    public String orderNumber() {
        return orderNumber;
    }

    public OrderStatus status() {
        return status;
    }

    public CustomerEntity customer() {
        return customer;
    }

    public List<OrderDetailsEntity> orderDetails() {
        return orderDetails;
    }
}


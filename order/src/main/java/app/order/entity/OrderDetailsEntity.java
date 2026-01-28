package app.order.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "item_number", nullable = false, length = 64)
    private String itemNumber;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int amount;

    protected OrderDetailsEntity() {}

    public OrderDetailsEntity(String itemNumber, BigDecimal unitPrice, int amount) {
        this.itemNumber = itemNumber;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public Long id() {
        return id;
    }

    public OrderEntity order() {
        return order;
    }

    public String itemNumber() {
        return itemNumber;
    }

    public BigDecimal unitPrice() {
        return unitPrice;
    }

    public int amount() {
        return amount;
    }

    public void putOnOrder(OrderEntity order) {
        this.order = order;
    }
}

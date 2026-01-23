package app.order.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(nullable = false)
    private int amount;

    protected OrderDetailsEntity() {}

    public OrderDetailsEntity(ItemEntity item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Long id() {
        return id;
    }

    public OrderEntity order() {
        return order;
    }

    public ItemEntity item() {
        return item;
    }

    public int amount() {
        return amount;
    }

    public void putOnOrder(OrderEntity order) {
        this.order = order;
    }
}

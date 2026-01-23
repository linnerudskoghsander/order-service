package app.order.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String itemNumber;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantityLeft;

    protected ItemEntity() {}

    public ItemEntity(String itemNumber, String name, String description, BigDecimal price, int quantityLeft) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityLeft = quantityLeft;
    }

    public Long id() { return id; }

    public String itemNumber() {
        return itemNumber;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public BigDecimal price() {
        return price;
    }

    public int quantityLeft() {
        return quantityLeft;
    }
}


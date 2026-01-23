package app.order.repository.item;

import app.order.domain.item.Description;
import app.order.domain.item.Item;
import app.order.domain.item.ItemNumber;
import app.order.domain.item.Name;
import app.order.entity.ItemEntity;

import static java.util.Objects.requireNonNull;

public class ItemAdapter {

    public static Item toDomain(ItemEntity e) {
        return new Item(
                new ItemNumber(requireNonNull(e.itemNumber())),
                new Name(requireNonNull(e.name())),
                new Description(requireNonNull(e.description())),
                e.price(),
                e.quantityLeft()
        );
    }

    public static ItemEntity toEntity(Item d) {
        return new ItemEntity(
                requireNonNull(d.itemNumber().value()),
                requireNonNull(d.name().value()),
                requireNonNull(d.description().value()),
                d.price(),
                d.quantityLeft()
        );
    }
}


package app.order.service;

import app.order.domain.item.Description;
import app.order.domain.item.Item;
import app.order.domain.item.ItemNumber;
import app.order.domain.item.Name;
import app.order.entity.ItemEntity;
import app.order.repository.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ItemService {
    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public List<Item> getAll() {
        return repo.findAll().stream()
                .map(ItemService::toDomain)
                .toList();
    }

    public void save(Item d) {
        repo.save(toEntity(d));
    }

    public Optional<Item> find(ItemNumber itemNumber) {
        return repo.findByItemNumber(itemNumber.value())
                .map(ItemService::toDomain);
    }

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
                d.prize(),
                d.quantityLeft()
        );
    }
}

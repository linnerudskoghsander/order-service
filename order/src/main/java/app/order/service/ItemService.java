package app.order.service;

import app.order.domain.item.Item;
import app.order.domain.item.ItemNumber;
import app.order.repository.item.ItemRepo;
import app.order.repository.item.ItemAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepo repo;

    public ItemService(ItemRepo repo) {
        this.repo = repo;
    }

    public List<Item> getAll() {
        return repo.findAll().stream()
                .map(ItemAdapter::toDomain)
                .toList();
    }

    public void save(Item d) {
        repo.save(ItemAdapter.toEntity(d));
    }

    public Optional<Item> find(ItemNumber itemNumber) {
        return repo.findByItemNumber(itemNumber.value())
                .map(ItemAdapter::toDomain);
    }
}

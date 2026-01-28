package app.order.controller;

import app.order.domain.item.Item;
import app.order.domain.item.ItemNumber;
import app.order.service.ItemService;
import app.order.config.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        var vs = service.getAll();
        return ResponseEntity.ok(vs);
    }

    @GetMapping("/{itemNumber}")
    public ResponseEntity<Item> find(@PathVariable String itemNumber) {
        return service.find(new ItemNumber(itemNumber))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemNumber));
    }
}


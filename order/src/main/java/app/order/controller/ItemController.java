package app.order.controller;

import app.order.domain.item.Item;
import app.order.domain.item.ItemNumber;
import app.order.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        try {
            var vs = service.getAll();
            return vs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vs);
        } catch (Exception e) {
            // TODO: behandle exception
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(params = "nr")
    public ResponseEntity<Item> find(@RequestParam("nr") String nr) {
        try {
            Optional<Item> item = service.find(new ItemNumber(nr));
            return item.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // TODO: behandle exception
            return ResponseEntity.internalServerError().build();
        }
    }
}


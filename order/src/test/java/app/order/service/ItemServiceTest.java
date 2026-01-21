package app.order.service;

import app.order.fakes.ItemRepositoryFake;
import app.order.helper.ItemBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemServiceTest {
    private final ItemService service;

    public ItemServiceTest() {
        this.service = new ItemService(new ItemRepositoryFake());
    }

    @Test
    void findAll() {
        var d1 = ItemBuilder.Item();
        var d2 = ItemBuilder.Item();
        service.save(d1);
        service.save(d2);

        assertEquals(2, service.getAll().size());
        assertEquals(List.of(d1, d2), service.getAll());
    }
}

package app.order;

import app.order.helper.ItemBuilder;
import app.order.helper.CustomerBuilder;
import app.order.service.ItemService;
import app.order.service.ItemServiceTest;
import app.order.service.CustomerService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MappingTest {

    @Test
    void item() {
        var iA = ItemBuilder.Item();
        var eA = ItemService.toEntity(iA);
        var iB = ItemService.toDomain(eA);
        var eB = ItemService.toEntity(iB);

        assertThat(iA).usingRecursiveComparison().isEqualTo(iB);
        assertThat(eA).usingRecursiveComparison().isEqualTo(eB);
    }

    @Test
    void customer() {
        var cA = CustomerBuilder.Customer();
        var eA = CustomerService.toEntity(cA);
        var cB = CustomerService.toDomain(eA);
        var eB = CustomerService.toEntity(cB);

        assertThat(cA).usingRecursiveComparison().isEqualTo(cB);
        assertThat(eA).usingRecursiveComparison().isEqualTo(eB);
    }
}



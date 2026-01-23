package app.order;

import app.order.helper.ItemBuilder;
import app.order.helper.CustomerBuilder;
import app.order.repository.item.ItemAdapter;
import app.order.repository.customer.CustomerAdapter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MappingTest {

    @Test
    void item() {
        var iA = ItemBuilder.Item();
        var eA = ItemAdapter.toEntity(iA);
        var iB = ItemAdapter.toDomain(eA);
        var eB = ItemAdapter.toEntity(iB);

        assertThat(iA).usingRecursiveComparison().isEqualTo(iB);
        assertThat(eA).usingRecursiveComparison().isEqualTo(eB);
    }

    @Test
    void customer() {
        var cA = CustomerBuilder.Customer();
        var eA = CustomerAdapter.toEntity(cA);
        var cB = CustomerAdapter.toDomain(eA);
        var eB = CustomerAdapter.toEntity(cB);

        assertThat(cA).usingRecursiveComparison().isEqualTo(cB);
        assertThat(eA).usingRecursiveComparison().isEqualTo(eB);
    }
}



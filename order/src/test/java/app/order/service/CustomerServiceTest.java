package app.order.service;

import app.order.fakes.CustomerRepoFake;
import app.order.helper.CustomerBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {
    private final CustomerService service;

    public CustomerServiceTest() {
        this.service = new CustomerService(new CustomerRepoFake());
    }

    @Test
    void findAll() {
        var c1 = CustomerBuilder.Customer();
        var c2 = CustomerBuilder.Customer();
        service.save(c1);
        service.save(c2);

        assertEquals(2, service.getAll().size());
        assertEquals(List.of(c1, c2), service.getAll());
    }
}


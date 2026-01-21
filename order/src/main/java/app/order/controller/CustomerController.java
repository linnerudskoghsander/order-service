package app.order.controller;

import app.order.domain.customer.ContactInfo;
import app.order.domain.customer.Customer;
import app.order.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        try {
            var vs = service.getAll();
            return vs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vs);
        } catch (Exception e) {
            // TODO: behandle exception
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(params = "nr")
    public ResponseEntity<Customer> find(@RequestParam("nr") String nr) {
        try {
            Optional<Customer> c = service.find(new ContactInfo.PhoneNumber(nr));
            return c.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // TODO: behandle exception
            return ResponseEntity.internalServerError().build();
        }
    }
}


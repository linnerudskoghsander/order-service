package app.order.controller;

import app.order.domain.customer.ContactInfo;
import app.order.domain.customer.Customer;
import app.order.service.CustomerService;
import app.order.config.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        var vs = service.getAll();
        return ResponseEntity.ok(vs);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Customer> find(@PathVariable String phoneNumber) {
        return service.find(new ContactInfo.PhoneNumber(phoneNumber))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with phone: " + phoneNumber));
    }
}


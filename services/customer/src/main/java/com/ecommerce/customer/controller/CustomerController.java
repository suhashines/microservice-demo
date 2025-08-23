package com.ecommerce.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customer.dto.CustomerCreateDto;
import com.ecommerce.customer.entity.Customer;
import com.ecommerce.customer.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor // creates a constructor for every field that is either not null or final
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerCreateDto dto) {
        
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    
    
    
}

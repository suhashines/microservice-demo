package com.ecommerce.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.customer.dto.CustomerCreateDto;
import com.ecommerce.customer.entity.Customer;
import com.ecommerce.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public String createCustomer(CustomerCreateDto dto) {
        Customer customer = Customer.builder().name(dto.name()).email(dto.email()).address(dto.address()).build();
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String id) {

        return customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("customer not found"));
    }
}

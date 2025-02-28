package com.sdm.invoicing.service;

import com.sdm.invoicing.dto.CustomerDto;
import com.sdm.invoicing.dto.CustomerRegistrationRequest;
import com.sdm.invoicing.entity.Customer;
import com.sdm.invoicing.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Boolean hasCustomerWithEmail(String email){
        return customerRepository.findByEmail(email).isPresent();
    }

    public Boolean hasCustomerWithMobile(String mobile){
        return customerRepository.findByMobile(mobile).isPresent();
    }

    public CustomerDto saveCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = new Customer();
        customer.setEmail(customerRegistrationRequest.getEmail());
        customer.setName(customerRegistrationRequest.getName());
        customer.setMobile(customerRegistrationRequest.getMobile());

        Customer saveCustomer = customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(saveCustomer.getId());
        customerDto.setEmail(saveCustomer.getEmail());
        customerDto.setName(saveCustomer.getName());
        customerDto.setMobile(saveCustomer.getMobile());

        return customerDto;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(Customer::getDto).collect(Collectors.toList());
    }

    public List<CustomerDto> searchCustomerByName(String name) {
        List<Customer> customers = customerRepository.findAllByNameContaining(name);
        return customers.stream().map(Customer::getDto).collect(Collectors.toList());
    }

    public CustomerDto updateCustomer(Long id, CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Customer found with the ID :: " + id));
        customer.setEmail(customerRegistrationRequest.getEmail());
        customer.setName(customerRegistrationRequest.getName());
        customer.setMobile(customerRegistrationRequest.getMobile());
        customer = customerRepository.save(customer);

        return customer.getDto();
    }

    public CustomerDto getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).isPresent() ? customerRepository.findById(id).get() : null;
        assert customer != null;
        return customer.getDto();
    }
}

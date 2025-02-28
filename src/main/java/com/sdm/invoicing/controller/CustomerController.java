package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.CustomerDto;
import com.sdm.invoicing.dto.CustomerRegistrationRequest;
import com.sdm.invoicing.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test/api/customer")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id){
        CustomerDto customerDto = customerService.getCustomer(id);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        if(customerService.hasCustomerWithMobile(customerRegistrationRequest.getMobile()) || customerService.hasCustomerWithEmail(customerRegistrationRequest.getEmail())) {
            return new ResponseEntity<>("Customer already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        CustomerDto customerDto = customerService.saveCustomer(customerRegistrationRequest);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }


    @GetMapping("/{name}")
    public ResponseEntity<List<CustomerDto>> getAllCustomersByName(@PathVariable String name){
        List<CustomerDto> customerDtos = customerService.searchCustomerByName(name);
        return ResponseEntity.ok(customerDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        CustomerDto customerDto = customerService.updateCustomer(id, customerRegistrationRequest);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }
}

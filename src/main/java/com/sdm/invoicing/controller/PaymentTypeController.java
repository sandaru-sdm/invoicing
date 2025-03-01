package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.CustomerDto;
import com.sdm.invoicing.dto.PaymentTypeDto;
import com.sdm.invoicing.dto.PaymentTypeSaveRequest;
import com.sdm.invoicing.service.PaymentTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test/api/payment-type")
@Validated
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentTypeDto>> getAllPaymentTypes(){
        List<PaymentTypeDto> paymentTypeDtos = paymentTypeService.getAllPaymentTypes();
        return ResponseEntity.ok(paymentTypeDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPaymentType(@PathVariable Long id){
        PaymentTypeDto paymentTypeDto = paymentTypeService.getPaymentType(id);
        return ResponseEntity.ok(paymentTypeDto);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePaymentTypes(@Valid @RequestBody PaymentTypeSaveRequest paymentTypesSaveRequest){
        if(paymentTypeService.hasPaymentTypeWithName(paymentTypesSaveRequest.getName())) {
            return new ResponseEntity<>("Payment Type already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        PaymentTypeDto paymentTypeDto = paymentTypeService.savePaymentType(paymentTypesSaveRequest);
        return new ResponseEntity<>(paymentTypeDto, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<PaymentTypeDto>> getAllPaymentTypes(@PathVariable String name){
        List<PaymentTypeDto> paymentTypeDtos = paymentTypeService.searchPaymentTypeByName(name);
        return ResponseEntity.ok(paymentTypeDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentTypes(@PathVariable Long id, @RequestBody PaymentTypeSaveRequest paymentTypeSaveRequest) {
        PaymentTypeDto paymentTypeDto = paymentTypeService.updatePaymentType(id, paymentTypeSaveRequest);
        return new ResponseEntity<>(paymentTypeDto, HttpStatus.OK);
    }
}

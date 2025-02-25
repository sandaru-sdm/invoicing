package com.sdm.invoicing.service;

import com.sdm.invoicing.dto.PaymentTypeDto;
import com.sdm.invoicing.dto.PaymentTypeSaveRequest;
import com.sdm.invoicing.entity.PaymentType;
import com.sdm.invoicing.repository.PaymentTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public List<PaymentTypeDto> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = paymentTypeRepository.findAll();
        return paymentTypes.stream().map(PaymentType::getDto).collect(Collectors.toList());
    }

    public boolean hasPaymentTypeWithName(String name) {
        return paymentTypeRepository.findByName(name).isPresent();
    }

    public PaymentTypeDto savePaymentType(PaymentTypeSaveRequest paymentTypesSaveRequest) {
        PaymentType paymentType = new PaymentType();
        paymentType.setName(paymentTypesSaveRequest.getName());
        PaymentType savePaymentType = paymentTypeRepository.save(paymentType);

        PaymentTypeDto paymentTypeDto = new PaymentTypeDto();
        paymentTypeDto.setId(savePaymentType.getId());
        paymentTypeDto.setName(savePaymentType.getName());
        return paymentTypeDto;
    }

    public List<PaymentTypeDto> searchPaymentTypeByName(String name) {
        List<PaymentType> paymentTypes = paymentTypeRepository.findAllByNameContaining(name);
        return paymentTypes.stream().map(PaymentType::getDto).collect(Collectors.toList());
    }

    public PaymentTypeDto updatePaymentType(Long id, PaymentTypeSaveRequest paymentTypeSaveRequest) {
        PaymentType paymentType = paymentTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Payment type found with the ID :: " + id));
        paymentType.setName(paymentTypeSaveRequest.getName());
        paymentType = paymentTypeRepository.save(paymentType);
        return paymentType.getDto();
    }
}

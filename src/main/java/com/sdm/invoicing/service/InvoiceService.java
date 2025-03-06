package com.sdm.invoicing.service;

import com.sdm.invoicing.dto.InvoiceDto;
import com.sdm.invoicing.dto.InvoiceItemDto;
import com.sdm.invoicing.dto.InvoicePaymentDto;
import com.sdm.invoicing.entity.*;
import com.sdm.invoicing.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoicePaymentRepository invoicePaymentRepository;
    private final CustomerRepository customerRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final ServiceRepository serviceRepository;
    private final DetailRepository detailRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository, InvoicePaymentRepository invoicePaymentRepository, CustomerRepository customerRepository, PaymentTypeRepository paymentTypeRepository, ServiceRepository serviceRepository, DetailRepository detailRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoicePaymentRepository = invoicePaymentRepository;
        this.customerRepository = customerRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.serviceRepository = serviceRepository;
        this.detailRepository = detailRepository;
    }

    @Transactional
    public Invoice saveInvoice(InvoiceDto invoiceDto) {

        Customer customer = customerRepository.findById(invoiceDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceDto.getInvoiceId());

        if (invoiceDto.getInvoiceDate() != null) {
            invoice.setInvoiceDate(invoiceDto.getInvoiceDate());
        } else {
            invoice.setInvoiceDate(LocalDateTime.now());
        }

        invoice.setCustomer(customer);
        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceItem> invoiceItems = invoiceDto.getInvoiceItems().stream().map(dto -> {
            InvoiceItem item = new InvoiceItem();
            item.setInvoice(savedInvoice);

            com.sdm.invoicing.entity.Service service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            Detail detail = detailRepository.findById(dto.getDetailId())
                    .orElseThrow(() -> new RuntimeException("Detail not found"));

            item.setService(service);
            item.setDetail(detail);

            item.setQty(dto.getQty());
            item.setRate(dto.getRate());
            item.setHeight(dto.getHeight());
            item.setWidth(dto.getWidth());
            return item;
        }).collect(Collectors.toList());

        invoiceItemRepository.saveAll(invoiceItems);

        if (invoiceDto.getPaymentAmount() != null && invoiceDto.getPaymentTypeId() != null) {
            PaymentType paymentType = paymentTypeRepository.findById(invoiceDto.getPaymentTypeId())
                    .orElseThrow(() -> new RuntimeException("Payment type not found"));

            InvoicePayment invoicePayment = new InvoicePayment();
            invoicePayment.setInvoice(savedInvoice);
            invoicePayment.setPaymentType(paymentType);
            invoicePayment.setPayment(invoiceDto.getPaymentAmount());
            invoicePayment.setBalance(invoiceDto.getBalance());

            invoicePaymentRepository.save(invoicePayment);
        }

        return savedInvoice;
    }

    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice invoice : invoices) {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setId(invoice.getId());
            invoiceDto.setInvoiceId(invoice.getInvoiceId());

            invoiceDto.setInvoiceDate(invoice.getInvoiceDate());

            invoiceDto.setCustomerId(invoice.getCustomer().getId());
            invoiceDto.setCustomerName(invoice.getCustomer().getName());

            BigDecimal totalAmount = BigDecimal.ZERO;
            List<InvoiceItemDto> invoiceItems = new ArrayList<>();
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                InvoiceItemDto itemDto = new InvoiceItemDto();
                itemDto.setServiceName(item.getService().getName());
                itemDto.setDetailName(item.getDetail().getName());
                itemDto.setRate(item.getRate());
                itemDto.setQty(item.getQty());
                itemDto.setHeight(item.getHeight());
                itemDto.setWidth(item.getWidth());
                BigDecimal itemTotal = item.getRate().multiply(BigDecimal.valueOf(item.getQty()));
                itemDto.setTotalAmount(itemTotal);
                invoiceItems.add(itemDto);

                totalAmount = totalAmount.add(itemTotal);
            }

            invoiceDto.setInvoiceItems(invoiceItems);
            invoiceDto.setTotalAmount(totalAmount);

            InvoicePayment invoicePayment = invoicePaymentRepository.findByInvoiceId(invoice.getId());
            if (invoicePayment != null) {
                InvoicePaymentDto paymentDto = new InvoicePaymentDto();
                paymentDto.setId(invoicePayment.getId());
                paymentDto.setInvoiceId(invoice.getId());
                paymentDto.setPayment(invoicePayment.getPayment());
                paymentDto.setBalance(invoicePayment.getBalance());

                PaymentType paymentType = invoicePayment.getPaymentType();
                paymentDto.setPaymentTypeId(paymentType.getId());
                paymentDto.setPaymentTypeName(paymentType.getName());

                invoiceDto.setInvoicePayment(paymentDto);
            }

            invoiceDtos.add(invoiceDto);
        }

        return invoiceDtos;
    }
}
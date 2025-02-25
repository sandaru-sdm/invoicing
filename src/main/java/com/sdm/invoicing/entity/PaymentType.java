package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.PaymentTypeDto;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_type")
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "paymentType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoicePayment> invoicePayments = new ArrayList<>();

    public PaymentTypeDto getDto() {
        PaymentTypeDto paymentTypeDto = new PaymentTypeDto();
        paymentTypeDto.setId(id);
        paymentTypeDto.setName(name);
        return paymentTypeDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InvoicePayment> getInvoicePayments() {
        return invoicePayments;
    }

    public void setInvoicePayments(List<InvoicePayment> invoicePayments) {
        this.invoicePayments = invoicePayments;
    }
}

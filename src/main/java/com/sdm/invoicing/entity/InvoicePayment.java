package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.InvoicePaymentDto;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_payment")
public class InvoicePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false, unique = true)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id", nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private BigDecimal payment;

    @Column(nullable = false)
    private BigDecimal balance;

    public InvoicePaymentDto getDto() {
        InvoicePaymentDto dto = new InvoicePaymentDto();
        dto.setId(id);
        if (invoice != null) {
            dto.setInvoiceId(invoice.getId());
        }
        if (paymentType != null) {
            dto.setPaymentTypeId(paymentType.getId());
            dto.setPaymentTypeName(paymentType.getName());
        }
        dto.setPayment(payment);
        dto.setBalance(balance);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

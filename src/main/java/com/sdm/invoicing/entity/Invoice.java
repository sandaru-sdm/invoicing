package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.InvoiceDto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_id", unique = true, nullable = false)
    private String invoiceId;

    @Column(name = "invoice_date", nullable = false)
    private LocalDateTime invoiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private InvoicePayment invoicePayment;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> invoiceItems;

    @PrePersist
    public void generateInvoiceId() {
        this.invoiceId = "INV-" + System.currentTimeMillis();
    }

    public InvoiceDto getDto() {
        InvoiceDto dto = new InvoiceDto();
        dto.setId(id);
        dto.setInvoiceId(invoiceId);
        dto.setInvoiceDate(invoiceDate);
        if (customer != null) {
            dto.setCustomerId(customer.getId());
            dto.setCustomerName(customer.getName());
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public InvoicePayment getInvoicePayment() {
        return invoicePayment;
    }

    public void setInvoicePayment(InvoicePayment invoicePayment) {
        this.invoicePayment = invoicePayment;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}

package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.InvoiceItemDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id", nullable = false)
    private Detail detail;

    @Column(nullable = false)
    private int qty;

    private BigDecimal height;

    private BigDecimal width;

    @Column(nullable = false)
    private BigDecimal rate;

    public InvoiceItemDto getDto() {
        InvoiceItemDto dto = new InvoiceItemDto();
        dto.setId(this.id);
        dto.setServiceId(this.service != null ? this.service.getId() : null);
        dto.setDetailId(this.detail != null ? this.detail.getId() : null);
        dto.setQty(this.qty);
        dto.setHeight(this.height);
        dto.setWidth(this.width);
        dto.setRate(this.rate);
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
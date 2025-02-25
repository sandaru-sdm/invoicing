package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.PriceDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id", nullable = false)
    private Detail detail;

    @Column(nullable = false)
    private BigDecimal price;

    public PriceDto getDto() {
        PriceDto dto = new PriceDto();
        dto.setId(id);
        dto.setServiceId(service != null ? service.getId() : null);
        dto.setDetailId(detail != null ? detail.getId() : null);
        dto.setPrice(price);

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

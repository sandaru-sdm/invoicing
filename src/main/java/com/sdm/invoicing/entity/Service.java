package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.ServiceDto;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<InvoiceItem> invoiceItems;

    public ServiceDto getDto() {
        ServiceDto dto = new ServiceDto();
        dto.setId(id);
        dto.setName(name);
        return dto;
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

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}

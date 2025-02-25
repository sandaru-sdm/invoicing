package com.sdm.invoicing.entity;

import com.sdm.invoicing.dto.DetailDto;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "detail")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL)
    private List<InvoiceItem> invoiceItems;

    public DetailDto getDto() {
        DetailDto dto = new DetailDto();
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

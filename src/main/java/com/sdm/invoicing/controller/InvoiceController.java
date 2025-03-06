package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.InvoiceDto;
import com.sdm.invoicing.entity.Invoice;
import com.sdm.invoicing.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceDto> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDto invoiceDto) {
        Invoice invoice = invoiceService.saveInvoice(invoiceDto);
        return ResponseEntity.ok(invoice);
    }
}

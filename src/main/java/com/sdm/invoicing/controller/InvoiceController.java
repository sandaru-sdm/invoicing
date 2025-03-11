package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.*;
import com.sdm.invoicing.entity.Invoice;
import com.sdm.invoicing.service.EmailService;
import com.sdm.invoicing.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("test/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final EmailService emailService;

    public InvoiceController(InvoiceService invoiceService, EmailService emailService) {
        this.invoiceService = invoiceService;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceDto> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @PostMapping
    public ResponseEntity<Long> createInvoice(@RequestBody InvoiceDto invoiceDto) {
        Invoice invoice = invoiceService.saveInvoice(invoiceDto);
        return ResponseEntity.ok(invoice.getId());
    }

    @GetMapping("items/{id}")
    public ResponseEntity<List<InvoiceItemDto>> getInvoiceItemsByInvoiceId(@PathVariable Long id) {
        List<InvoiceItemDto> invoiceItems = invoiceService.getInvoiceItemsByInvoiceId(id);
        return ResponseEntity.ok(invoiceItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        InvoiceDto invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<InvoicePaymentDto> getInvoicePaymentByInvoiceId(@PathVariable Long id) {
        InvoicePaymentDto paymentDto = invoiceService.getInvoicePaymentByInvoiceId(id);
        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping("/send-invoice")
    public String sendInvoice(@RequestBody EmailRequest request) {
        try {
            emailService.sendInvoiceEmail(request.getEmail(), request.getCustomerName(), request.getInvoiceHtml());
            return "Invoice sent successfully!";
        } catch (Exception e) {
            return "Failed to send email";
        }
    }

    @PostMapping("/send-invoice-pdf")
    public ResponseEntity<String> sendInvoicePdf(
            @RequestParam String email,
            @RequestParam String customerName,
            @RequestParam MultipartFile invoiceFile) {

        try {
            emailService.sendEmailWithAttachment(email, customerName, invoiceFile);
            return ResponseEntity.ok("Invoice sent successfully!");
        }  catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send email: " + e.getMessage());
    }

}
}

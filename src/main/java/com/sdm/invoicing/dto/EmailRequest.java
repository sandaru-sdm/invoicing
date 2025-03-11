package com.sdm.invoicing.dto;

public class EmailRequest {
    private String email;
    private String customerName;
    private String invoiceHtml;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInvoiceHtml() {
        return invoiceHtml;
    }

    public void setInvoiceHtml(String invoiceHtml) {
        this.invoiceHtml = invoiceHtml;
    }
}

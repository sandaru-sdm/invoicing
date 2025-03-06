package com.sdm.invoicing.repository;

import com.sdm.invoicing.entity.InvoicePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicePaymentRepository extends JpaRepository<InvoicePayment, Long> {
    InvoicePayment findByInvoiceId(Long id);
}

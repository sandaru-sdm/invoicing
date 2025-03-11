package com.sdm.invoicing.service;

import com.sdm.invoicing.entity.Invoice;
import com.sdm.invoicing.entity.InvoiceItem;
import com.sdm.invoicing.repository.InvoiceItemRepository;
import com.sdm.invoicing.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.time.ZonedDateTime;

@Service
public class DashboardService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public DashboardService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public Integer getTotalInvoices() {
        return Math.toIntExact(invoiceRepository.count());
    }

    public BigDecimal getTotalIncome() {
        List<Invoice> allInvoices = invoiceRepository.findAll();
        List<InvoiceItem> allInvoiceItems = invoiceItemRepository.findByInvoiceIn(allInvoices);
        return calculateIncome(allInvoiceItems);
    }

    public BigDecimal getTodayIncome() {
        LocalDate today = LocalDate.now();
        ZonedDateTime startOfDay = today.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endOfDay = today.atTime(23, 59, 59, 999999999).atZone(ZoneId.of("UTC"));

        startOfDay = startOfDay.withNano(0);
        endOfDay = endOfDay.withNano(0);

        return getIncomeByDateRange(startOfDay.toLocalDateTime(), endOfDay.toLocalDateTime());
    }

    public BigDecimal getYesterdayIncome() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        ZonedDateTime startOfDay = yesterday.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endOfDay = yesterday.atTime(23, 59, 59, 999999999).atZone(ZoneId.of("UTC"));

        startOfDay = startOfDay.withNano(0);
        endOfDay = endOfDay.withNano(0);

        return getIncomeByDateRange(startOfDay.toLocalDateTime(), endOfDay.toLocalDateTime());
    }

    public BigDecimal getThisMonthIncome() {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfMonth.plusMonths(1);
        return getIncomeByDateRange(firstDayOfMonth.atStartOfDay(), firstDayOfNextMonth.atStartOfDay());
    }

    private BigDecimal getIncomeByDateRange(LocalDateTime start, LocalDateTime end) {
        System.out.println("Fetching invoices between " + start + " and " + end);

        List<Invoice> invoices = invoiceRepository.findByInvoiceDateBetween(start, end);
        if (invoices.isEmpty()) {
            System.out.println("No invoices found for this date range");
        }

        List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceIn(invoices);
        return calculateIncome(invoiceItems);
    }

    private BigDecimal calculateIncome(List<InvoiceItem> invoiceItems) {
        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceItem invoiceItem : invoiceItems) {
            BigDecimal itemTotal;

            if (invoiceItem.getHeight() != null && invoiceItem.getWidth() != null
                    && invoiceItem.getHeight().compareTo(BigDecimal.ZERO) > 0
                    && invoiceItem.getWidth().compareTo(BigDecimal.ZERO) > 0) {
                itemTotal = invoiceItem.getHeight()
                        .multiply(invoiceItem.getWidth())
                        .multiply(invoiceItem.getRate())
                        .multiply(BigDecimal.valueOf(invoiceItem.getQty()));
            } else {
                itemTotal = invoiceItem.getRate().multiply(BigDecimal.valueOf(invoiceItem.getQty()));
            }

            total = total.add(itemTotal);
        }

        return total;
    }
}
package com.sdm.invoicing.controller;

import com.sdm.invoicing.service.DashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("test/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/invoices-count")
    private Integer getTotalInvoices() {
        return dashboardService.getTotalInvoices();
    }

    @GetMapping("/total-income")
    private BigDecimal getTotalIncome() {
        return dashboardService.getTotalIncome();
    }

    @GetMapping("/today-income")
    private BigDecimal getTodayIncome() {
        return dashboardService.getTodayIncome();
    }

    @GetMapping("/yesterday-income")
    private BigDecimal getYesterdayIncome() {
        return dashboardService.getYesterdayIncome();
    }

    @GetMapping("/this-month-income")
    private BigDecimal getThisMonthIncome() {
        return dashboardService.getThisMonthIncome();
    }
}

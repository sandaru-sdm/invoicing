package com.sdm.invoicing.dto;

import jakarta.validation.constraints.NotBlank;

public class DetailSaveRequest {
    @NotBlank(message = "Detail name is mandatory")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

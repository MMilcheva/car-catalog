package com.example.carcatalog.dto;

import java.time.LocalDate;

public class PriceResponse {
    private String carMaintenanceName;

    private Double amount;

    private LocalDate validFrom;

    private LocalDate validTo;

    public PriceResponse() {
    }

    public String getCarMaintenanceName() {
        return carMaintenanceName;
    }

    public void setCarMaintenanceName(String carMaintenanceName) {
        this.carMaintenanceName = carMaintenanceName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}

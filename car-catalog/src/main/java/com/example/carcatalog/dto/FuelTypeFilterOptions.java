package com.example.carcatalog.dto;

import java.util.Optional;

public class FuelTypeFilterOptions {

    private Optional<String> FuelTypeName;
    private Optional<String> sortBy;

    private Optional<String> sortOrder;

    public FuelTypeFilterOptions(Optional<String> fuelTypeName, Optional<String> sortBy, Optional<String> sortOrder) {
        FuelTypeName = fuelTypeName;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public Optional<String> getFuelTypeName() {
        return FuelTypeName;
    }

    public void setFuelTypeName(Optional<String> fuelTypeName) {
        FuelTypeName = fuelTypeName;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }
}

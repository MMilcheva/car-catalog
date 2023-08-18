package com.example.carcatalog.dto;

import java.util.Optional;

public class FuelTypeFilterOptions {

    private Optional<String> fuelTypeName;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public FuelTypeFilterOptions(String fuelTypeName, String sortBy, String sortOrder) {
        this.fuelTypeName = Optional.ofNullable(fuelTypeName);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }
    public FuelTypeFilterOptions() {
        this(null, null, null);
    }

    public Optional<String> getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(Optional<String> fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
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

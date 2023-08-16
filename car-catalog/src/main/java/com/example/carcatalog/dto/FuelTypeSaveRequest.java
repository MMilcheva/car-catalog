package com.example.carcatalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FuelTypeSaveRequest {
    @NotNull(message = "Fuel type name cannot be empty")
    @Size(min = 3, max = 15, message = "Fuel type has to be between 3 and 15 chars.")
    private String fuelTypeName;

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }
}

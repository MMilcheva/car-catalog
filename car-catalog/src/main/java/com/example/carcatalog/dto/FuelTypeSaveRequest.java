package com.example.carcatalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeSaveRequest {
    @NotNull(message = "Fuel type name cannot be empty")
    @Size(min = 3, max = 45, message = "Fuel type has to be between 3 and 45 chars.")
    private String fuelTypeName;

}

package com.example.carcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private String modelName;
    private String brandName;
    private String vin;
    private LocalDate registrationDate;
    private String fuelTypeName;
    private String transmissionTypeName;

    private Double price;
    private String remarks;
    private String userName;
}

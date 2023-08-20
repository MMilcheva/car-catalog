package com.example.carcatalog.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarFilterDto {

    private Long carId;
    private Long userId;
    private String modelName;
    private String brandName;
    private String fuelType;
    private String transmissionType;
    private Double price;
    private LocalDate registrationDate;

    private String sortBy;
    private String sortOrder;

}

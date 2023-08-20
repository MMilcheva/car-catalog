package com.example.carcatalog.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarSaveRequest {

    //    private Model model;
    @NotNull(message = "Model name cannot be empty")
    @Size(min = 1, max = 45, message = "Model name must be between 1 and 45 symbols")
    private String modelName;

    @NotNull(message = "VIN cannot be empty")
    @Size(min = 17, max = 17, message = "VIN has to be exactly 17 chars. Numbers and letters only!")
    private String vin;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Fuel type name cannot be empty")
    @Size(min = 3, max = 45, message = "Fuel type has to be between 3 and 45 chars.")
    private String fuelTypeName;

    @NotNull(message = "Transmission type cannot be empty")
    @Size(min = 3, max = 45, message = "Fuel type has to be between 3 and 45 chars.")
    private String transmissionTypeName;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Date of registration cannot be empty")
    @PastOrPresent(message = "Date of registration cannot be in the future")
    private LocalDate registrationDate;

    private String userName;

    private String remarks;

}

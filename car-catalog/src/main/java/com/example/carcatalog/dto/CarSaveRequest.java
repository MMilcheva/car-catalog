package com.example.carcatalog.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class CarSaveRequest {

    //    private Model model;
    @NotNull(message = "Model name cannot be empty")
    @Size(min = 1, max = 32, message = "Model name must be between 1 and 32 symbols")
    private String modelName;

    @NotNull(message = "VIN cannot be empty")
    @Size(min = 17, max = 17, message = "VIN has to be exactly 17 chars. Numbers and letters only!")
    private String vin;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Fuel type name cannot be empty")
    @Size(min = 3, max = 15, message = "Fuel type has to be between 3 and 15 chars.")
    private String fuelTypeName;

    @NotNull(message = "Transmission type cannot be empty")
    @Size(min = 3, max = 15, message = "Fuel type has to be between 3 and 15 chars.")
    private String transmissionTypeName;


    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    @NotNull(message = "Date of registration cannot be empty")
    @PastOrPresent(message = "Date of registration cannot be in the future")
    private LocalDate registrationDate;

    private String userName;

    private String remarks;

    public CarSaveRequest() {
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public String getTransmissionTypeName() {
        return transmissionTypeName;
    }

    public void setTransmissionTypeName(String transmissionTypeName) {
        this.transmissionTypeName = transmissionTypeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

package com.example.carcatalog.dto;

import java.time.LocalDate;
import java.util.Optional;

public class CarFilterOptions {
    private Optional<Long> carId;
    private Optional<Long> userId;
    private Optional<String> modelName;
    private Optional<String> brandName;

    private Optional<String> fuelType;
    private Optional<String> transmissionType;
    private Optional<Double> price;
    private Optional<LocalDate> registrationDate;

    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public CarFilterOptions(Long carId,
                            Long userId,
                            String modelName,
                            String brandName,
                            String fuelType,
                            String transmissionType,
                            Double price,
                            LocalDate registrationDate,
                            String sortBy,
                            String sortOrder) {
        this.carId = Optional.ofNullable(carId);
        this.userId = Optional.ofNullable(userId);
        this.modelName = Optional.ofNullable(modelName);
        this.brandName = Optional.ofNullable(brandName);
        this.fuelType = Optional.ofNullable(fuelType);
        this.transmissionType = Optional.ofNullable(transmissionType);
        this.price = Optional.ofNullable(price);
        this.registrationDate = Optional.ofNullable(registrationDate);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<Long> getCarId() {
        return carId;
    }

    public void setCarId(Optional<Long> carId) {
        this.carId = carId;
    }

    public Optional<Long> getUserId() {
        return userId;
    }

    public void setUserId(Optional<Long> userId) {
        this.userId = userId;
    }

    public void setModelName(Optional<String> modelName) {
        this.modelName = modelName;
    }

    public void setBrandName(Optional<String> brandName) {
        this.brandName = brandName;
    }

    public void setFuelType(Optional<String> fuelType) {
        this.fuelType = fuelType;
    }

    public void setTransmissionType(Optional<String> transmissionType) {
        this.transmissionType = transmissionType;
    }

    public void setPrice(Optional<Double> price) {
        this.price = price;
    }

    public void setRegistrationDate(Optional<LocalDate> registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Optional<String> getModelName() {
        return modelName;
    }

    public Optional<String> getBrandName() {
        return brandName;
    }

    public Optional<String> getFuelType() {
        return fuelType;
    }

    public Optional<String> getTransmissionType() {
        return transmissionType;
    }

    public Optional<Double> getPrice() {
        return price;
    }

    public Optional<LocalDate> getRegistrationDate() {
        return registrationDate;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
    //    public Optional<Long> getCarId() {
//        return carId;
//    }
//
//    public void setCarId(Optional<Long> carId) {
//        this.carId = carId;
//    }
//
//
//    public Optional<String> getModelName() {
//        return modelName;
//    }
//
//    public void setModelName(Optional<String> modelName) {
//        this.modelName = modelName;
//    }
//
//
//    public Optional<LocalDate> getRegistrationDate() {
//        return registrationDate;
//    }
//
//    public void setRegistrationDate(Optional<LocalDate> registrationDate) {
//        this.registrationDate = registrationDate;
//    }
//
//    public Optional<String> getSortBy() {
//        return sortBy;
//    }
//
//    public void setSortBy(Optional<String> sortBy) {
//        this.sortBy = sortBy;
//    }
//
//    public Optional<String> getSortOrder() {
//        return sortOrder;
//    }
//
//    public void setSortOrder(Optional<String> sortOrder) {
//        this.sortOrder = sortOrder;
//    }
//
//    public Optional<String> getBrandName() {
//        return brandName;
//    }
//
//    public void setBrandName(Optional<String> brandName) {
//        this.brandName = brandName;
//    }
//
//    public Optional<String> getFuelType() {
//        return fuelType;
//    }
//
//    public void setFuelType(Optional<String> fuelType) {
//        this.fuelType = fuelType;
//    }
//
//    public Optional<String> getTransmissionType() {
//        return transmissionType;
//    }
//
//    public void setTransmissionType(Optional<String> transmissionType) {
//        this.transmissionType = transmissionType;
//    }
//
//    public Optional<Double> getPrice() {
//        return price;
//    }
//
//    public void setPrice(Optional<Double> price) {
//        this.price = price;
//    }
}

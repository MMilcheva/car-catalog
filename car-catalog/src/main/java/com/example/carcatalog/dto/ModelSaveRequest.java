package com.example.carcatalog.dto;

import com.example.carcatalog.models.Brand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ModelSaveRequest {
    @NotNull
    @Size(min = 2, max = 32, message = "Model name must be between 2 and 32 symbols")
    private String modelName;
    private Brand brand;
    private Long brandId;
    private String brandName;

    public ModelSaveRequest() {
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

   }

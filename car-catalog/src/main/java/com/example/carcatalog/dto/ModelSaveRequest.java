package com.example.carcatalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ModelSaveRequest {
    @NotNull
    @Size(min = 2, max = 32, message = "Model name must be between 2 and 32 symbols")
    private String modelName;

    @NotNull
    @Size(min = 2, max = 32, message = "Brand name must be between 2 and 32 symbols")
    private String brandName;
    public ModelSaveRequest() {
    }
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

   }

package com.example.carcatalog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class BrandSaveRequest {


    @Column(name = "brand_name")
    @Size(min = 2, max = 32, message = "Brand name must be between 2 and 32 symbols")
    private String brandName;


    public BrandSaveRequest() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }


}

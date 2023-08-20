package com.example.carcatalog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandSaveRequest {


    @Column(name = "brand_name")
    @Size(min = 2, max = 45, message = "Brand name must be between 2 and 45 symbols")
    private String brandName;

}

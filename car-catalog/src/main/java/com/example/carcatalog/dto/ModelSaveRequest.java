package com.example.carcatalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelSaveRequest {
    @NotNull
    @Size(min = 2, max = 45, message = "Model name must be between 2 and 45 symbols")
    private String modelName;

    @NotNull
    @Size(min = 2, max = 45, message = "Brand name must be between 2 and 45 symbols")
    private String brandName;

}

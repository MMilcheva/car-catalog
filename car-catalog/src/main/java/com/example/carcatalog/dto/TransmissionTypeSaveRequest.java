package com.example.carcatalog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransmissionTypeSaveRequest {
    @NotNull(message = "Transmission type name cannot be empty")
    @Size(min = 3, max = 45, message = "Transmission type name has to be between 3 and 45 chars.")
    @Column(name = "transmission_name")
    private String transmissionTypeName;

}

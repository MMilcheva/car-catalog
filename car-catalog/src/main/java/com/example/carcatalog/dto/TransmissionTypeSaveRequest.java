package com.example.carcatalog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransmissionTypeSaveRequest {
    @NotNull(message = "Transmission type name cannot be empty")
    @Size(min = 3, max = 15, message = "Transmission type name has to be between 3 and 15 chars.")
    @Column(name = "transmission_name")
    private String transmissionTypeName;

    public String getTransmissionTypeName() {
        return transmissionTypeName;
    }

    public void setTransmissionTypeName(String transmissionTypeName) {
        this.transmissionTypeName = transmissionTypeName;
    }
}

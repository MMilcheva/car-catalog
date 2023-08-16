package com.example.carcatalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "transmissions")
public class TransmissionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transmission_id", insertable = false)
    private Long transmissionTypeId;


    @NotNull(message = "Transmission type name cannot be empty")
    @Size(min = 3, max = 15, message = "Transmission type name has to be between 3 and 15 chars.")
    @Column(name = "transmission_name")
    private String transmissionTypeName;

    public TransmissionType() {
    }

    public Long getTransmissionTypeId() {
        return transmissionTypeId;
    }

    public void setTransmissionTypeId(Long transmissionTypeId) {
        this.transmissionTypeId = transmissionTypeId;
    }

    public String getTransmissionTypeName() {
        return transmissionTypeName;
    }

    public void setTransmissionTypeName(String transmissionTypeName) {
        this.transmissionTypeName = transmissionTypeName;
    }
}

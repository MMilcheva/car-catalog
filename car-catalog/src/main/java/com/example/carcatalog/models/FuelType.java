package com.example.carcatalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "fuel_types")
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_type_id", insertable = false)
    private Long fuelTypeId;


    @NotNull(message = "Fuel type name cannot be empty")
    @Size(min = 3, max = 15, message = "Fuel type name has to be between 3 and 15 chars.")
    @Column(name = "fuel_name")
    private String fuelTypeName;

    public FuelType() {
    }

    public Long getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Long fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }
}

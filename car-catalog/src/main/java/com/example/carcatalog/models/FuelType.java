package com.example.carcatalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fuel_types")
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_type_id", insertable = false)
    private Long fuelTypeId;


    @NotNull(message = "Fuel type name cannot be empty")
    @Size(min = 3, max = 45, message = "Fuel type name has to be between 3 and 45 chars.")
    @Column(name = "fuel_name")
    private String fuelTypeName;

}

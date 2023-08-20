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
@Table(name = "transmissions")
public class TransmissionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transmission_id", insertable = false)
    private Long transmissionTypeId;

    @NotNull(message = "Transmission type name cannot be empty")
    @Size(min = 3, max = 45, message = "Transmission type name has to be between 3 and 45 chars.")
    @Column(name = "transmission_name")
    private String transmissionTypeName;
}

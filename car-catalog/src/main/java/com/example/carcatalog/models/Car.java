package com.example.carcatalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", insertable = false)
    private Long carId;

    //e.g.: 123456789101112131
    @NotNull(message = "VIN cannot be empty")
    @Size(min = 17, max = 17, message = "VIN has to be exactly 17 chars. Numbers and letters only!")
    @Column(name = "vin_number", length = 17)
    private String vin;

    @NotNull(message = "Model name cannot be empty")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "models_model_id")
    private Model model;

    @NotNull(message = "Fuel type name cannot be empty")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_types_fuel_type_id")
    private FuelType fuelType;

    @NotNull(message = "Transmission type cannot be empty")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transmissions_transmission_id")
    private TransmissionType transmissionType;


    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be positive")
    @Column(name = "price")
    private Double price;

    @NotNull(message = "Date of registration cannot be empty")
    @PastOrPresent(message = "Date of registration cannot be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "reg_date")
    private LocalDate registrationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "users_user_id")
    private User user;

    @Column(name = "remarks")
    private String remarks;

}



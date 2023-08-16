package com.example.carcatalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "models_model_id")
    private Model model;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_types_fuel_type_id")
    private FuelType fuelType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transmissions_transmission_id")
    private TransmissionType transmissionType;


    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be positive")
    @Column(name="price")
    private Double price;

    @NotNull(message = "Date of registration cannot be empty")
    @PastOrPresent(message = "Date of registration cannot be in the future")
    @Column(name = "reg_date")
    private LocalDate registrationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "users_user_id")
    private User user;

    @Column(name = "remarks")
    private String remarks;
    public Car() {
    }

    public Car(Long carId, String vin, LocalDate registrationDate, Model model, User user) {
        this.carId = carId;

        this.vin = vin;
        this.registrationDate = registrationDate;
        this.model = model;
        this.user = user;

    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carId, car.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }


}

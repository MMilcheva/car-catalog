package com.example.carcatalog.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long priceId;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "car_maintenance_id")
//    private CarMaintenance carMaintenance;
    @Column(name = "amount")
    private Double amount;
    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    @Column(name = "valid_from")
    private LocalDate validFrom;
    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    @Column(name = "valid_to")
    private LocalDate validTo;
       public Price() {
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}

package com.example.carcatalog.repositories.contracts;

import com.example.carcatalog.dto.CarFilterOptions;
import com.example.carcatalog.models.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends BaseCRUDRepository<Car> {
    List<Car> getAllCarsByUserId(Long userId);

    List<Car> getAllCars(Optional<String> search);

    List<Car> getAllCarsFilter(CarFilterOptions carFilterOptions);

    List<Car> filter(Optional<Long> carId,
                     Optional<Long> userId,
                     Optional<String> modelName,
                     Optional<String> brandName,
                     Optional<String> fuelType,
                     Optional<String> transmissionType,
                     Optional<Double> price,
                     Optional<LocalDate> registrationDate,
                     Optional<String> sortBy,
                     Optional<String> sortOrder);


    List<Car> findCarByVin(String vin);
}

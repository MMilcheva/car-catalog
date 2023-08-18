package com.example.carcatalog.services.contracts;

import com.example.carcatalog.dto.CarFilterOptions;
import com.example.carcatalog.models.Car;
import com.example.carcatalog.models.User;

import java.util.List;
import java.util.Optional;

public interface CarService {


    Car getCarById(Long carId);

    List<Car> getAllCars(Optional<String> search);

    List<Car> getAllCarFilter(CarFilterOptions carFilterOptions);

    void deleteCar(long carId, User user);

    Car createCar(Car car);

    Car updateCar(Car car);

    List<Car> getAllCarsByUserId(Long userId);

}

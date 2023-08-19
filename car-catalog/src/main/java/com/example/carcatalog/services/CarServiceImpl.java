package com.example.carcatalog.services;

import com.example.carcatalog.dto.CarFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.models.Car;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.CarRepository;
import com.example.carcatalog.services.contracts.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car getCarById(Long carId) {
        return carRepository.getById(carId);
    }

    @Override
    public List<Car> getAllCars(Optional<String> search) {
        return carRepository.getAllCars(search);
    }

    @Override
    public List<Car> getAllCarFilter(CarFilterOptions carFilterOptions) {
        return carRepository.getAllCarsFilter(carFilterOptions);
    }

    @Override
    public void deleteCar(long carId, User user) {
        carRepository.delete(carId);
    }

    @Override
    public Car createCar(Car car) {
        car.setVin(car.getVin().toUpperCase());
        List<Car> existingCars = carRepository.findCarByVin(car.getVin());
        if (!existingCars.isEmpty()) {
            throw new DuplicateEntityException("Car", "VIN", existingCars.get(0).getVin());
        } else {
            carRepository.create(car);
            return car;
        }
    }

    @Override
    public Car updateCar(Car car) {
        car.setVin(car.getVin().toUpperCase());
        List<Car> existingCars = carRepository.findCarByVin(car.getVin());
        if (!existingCars.isEmpty()) {
            throw new DuplicateEntityException("Car", "VIN", existingCars.get(0).getVin());
        } else {
            carRepository.update(car);
            return car;
        }
    }

    @Override
    public List<Car> getAllCarsByUserId(Long userId) {
        return carRepository.getAllCarsByUserId(userId);
    }

}

package com.example.carcatalog.services;

import com.example.carcatalog.dto.CarFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.EntityNotFoundException;
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
//        checkModifyPermissions(user);
        carRepository.delete(carId);
    }

    @Override
    public Car createCar(Car car) {
        boolean duplicateVINExists = true;
        try {
            carRepository.create(car);
        } catch (EntityNotFoundException e) {
            duplicateVINExists = false;
        }
        if (duplicateVINExists) {
            throw new DuplicateEntityException("Car", "VIN", car.getVin());
        }
        return car;
    }

    @Override
    public Car updateCar(Car car) {
        boolean duplicateVINExists = true;
        try {
            carRepository.update(car);
        } catch (EntityNotFoundException e) {
            duplicateVINExists = false;
        }
        if (duplicateVINExists) {
            throw new DuplicateEntityException("Car", "VIN", car.getVin());
        }
        return car;
    }

    @Override
    public List<Car> getAllCarsByUserId(Long userId) {
        return carRepository.getAllCarsByUserId(userId);
    }


//    private void checkModifyPermissions(User user) {
//        String str = "admin";
//        if (!(user.getRole().getRoleName().equals(str))) {
//            throw new AuthorizationException(MODIFY_MODEL_ERROR_MESSAGE);
//        }
//    }

}

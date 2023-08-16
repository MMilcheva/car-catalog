package com.example.carcatalog.helpers;

import com.example.carcatalog.dto.CarResponse;
import com.example.carcatalog.dto.CarSaveRequest;
import com.example.carcatalog.models.*;
import com.example.carcatalog.services.contracts.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapper {

    private final UserService userService;
    private final ModelService modelService;
    private final CarService carService;
    private final BrandService brandService;
    private final FuelTypeService fuelTypeService;
    private final TransmissionTypeService transmissionTypeService;


    public CarMapper(UserService userService, ModelService modelService, CarService carService, BrandService brandService, FuelTypeService fuelTypeService, TransmissionTypeService transmissionTypeService) {
        this.userService = userService;
        this.modelService = modelService;
        this.brandService = brandService;
        this.carService = carService;
        this.fuelTypeService = fuelTypeService;
        this.transmissionTypeService = transmissionTypeService;
    }


    public Car convertToCar(@Valid CarSaveRequest carSaveRequest) {
        Car car = new Car();
        car.setVin(carSaveRequest.getVin());
        Model model = modelService.getModelByName(carSaveRequest.getModelName());
        car.setModel(model);
        car.setPrice(carSaveRequest.getPrice());
        car.setRegistrationDate(carSaveRequest.getRegistrationDate());
        TransmissionType transmissionType = transmissionTypeService.getTransmissionTypeByName(carSaveRequest.getTransmissionTypeName());
        car.setTransmissionType(transmissionType);
        FuelType fuelType = fuelTypeService.getFuelTypeByName(carSaveRequest.getFuelTypeName());
        car.setFuelType(fuelType);
        car.setRemarks(carSaveRequest.getRemarks());
        User user = userService.getUserByUsername(carSaveRequest.getUserName());
        car.setUser(user);
        return car;
    }

    public CarResponse convertToCarResponse(Car car) {

        CarResponse carResponse = new CarResponse();

        carResponse.setModelName(car.getModel().getModelName());
        carResponse.setBrandName(car.getModel().getBrand().getBrandName());
        carResponse.setVin(car.getVin());
        carResponse.setRegistrationDate(car.getRegistrationDate());
        carResponse.setFuelTypeName(car.getFuelType().getFuelTypeName());
        carResponse.setTransmissionTypeName(car.getTransmissionType().getTransmissionTypeName());
        carResponse.setRemarks(car.getRemarks());
        carResponse.setUserName(car.getUser().getUsername());
        return carResponse;
    }

    public Car convertToCarToBeUpdated(Long carToBeUpdatedId, CarSaveRequest carSaveRequest) {
        Car carToBeUpdated = carService.getCarById(carToBeUpdatedId);


        carToBeUpdated.setVin(carSaveRequest.getVin());
        Model model = modelService.getModelByName(carSaveRequest.getModelName());
        carToBeUpdated.setModel(model);
        carToBeUpdated.setPrice(carSaveRequest.getPrice());
        carToBeUpdated.setRegistrationDate(carSaveRequest.getRegistrationDate());
        TransmissionType transmissionType = transmissionTypeService.getTransmissionTypeByName(carSaveRequest.getTransmissionTypeName());
        carToBeUpdated.setTransmissionType(transmissionType);
        FuelType fuelType = fuelTypeService.getFuelTypeByName(carSaveRequest.getFuelTypeName());
        carToBeUpdated.setFuelType(fuelType);
        carToBeUpdated.setRemarks(carSaveRequest.getRemarks());
        User user = userService.getUserByUsername(carSaveRequest.getUserName());
        carToBeUpdated.setUser(user);

        return carToBeUpdated;
    }

    public List<CarResponse> convertToCarResponses(List<Car> cars) {

        List<CarResponse> carResponses = new ArrayList<>();

        cars.forEach(car -> carResponses.add(convertToCarResponse(car)));
        return carResponses;
    }
}

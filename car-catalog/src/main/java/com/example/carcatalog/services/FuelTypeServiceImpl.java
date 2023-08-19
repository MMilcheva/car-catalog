package com.example.carcatalog.services;

import com.example.carcatalog.dto.FuelTypeFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.FuelTypeCannotBeDeletedException;
import com.example.carcatalog.exceptions.ModelCannotBeDeletedException;
import com.example.carcatalog.models.Car;
import com.example.carcatalog.models.FuelType;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.CarRepository;
import com.example.carcatalog.repositories.contracts.FuelTypeRepository;
import com.example.carcatalog.services.contracts.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelTypeServiceImpl implements FuelTypeService {

    private final FuelTypeRepository fuelTypeRepository;
private final CarRepository carRepository;

    @Autowired
    public FuelTypeServiceImpl(FuelTypeRepository fuelTypeRepository, CarRepository carRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.carRepository = carRepository;
    }


    @Override
    public FuelType getFuelTypeById(Long fuelTypeId) {
        return fuelTypeRepository.getById(fuelTypeId);
    }

    @Override
    public List<FuelType> getAllFuelTypes(Optional<String> search) {
        return fuelTypeRepository.getAllFuelTypes(search);
    }

    @Override
    public List<FuelType> getAllFuelTypeFilter(FuelTypeFilterOptions fuelTypeFilterOptions) {
        return fuelTypeRepository.getAllFuelTypesFilter(fuelTypeFilterOptions);
    }


    @Override
    public FuelType createFuelType(FuelType fuelType) {
        fuelType.setFuelTypeName(fuelType.getFuelTypeName().toUpperCase());
        List<FuelType> existingFuelTypes = fuelTypeRepository.findByFuelTypeName(fuelType.getFuelTypeName());
        if (!existingFuelTypes.isEmpty()) {
            throw new DuplicateEntityException("FuelType", "name", existingFuelTypes.get(0).getFuelTypeName());
        } else {
            fuelTypeRepository.create(fuelType);
            return fuelType;
        }

    }

    @Override
    public FuelType updateFuelType(FuelType fuelType) {
        fuelType.setFuelTypeName(fuelType.getFuelTypeName().toUpperCase());
        List<FuelType> existingFuelTypes = fuelTypeRepository.findByFuelTypeName(fuelType.getFuelTypeName());
        if (!existingFuelTypes.isEmpty()) {
            throw new DuplicateEntityException("FuelType", "name", existingFuelTypes.get(0).getFuelTypeName());
        } else {
            fuelTypeRepository.update(fuelType);
            return fuelType;
        }
    }

    @Override
    public void deleteFuelType(long fuelTypeId, User user) {
        FuelType fuelTypeIdToBeDeleted = fuelTypeRepository.getById(fuelTypeId);
        List<Car> existingCars = carRepository.getAllCars(fuelTypeIdToBeDeleted.getFuelTypeName().describeConstable());
        if (!existingCars.isEmpty()) {
            throw new FuelTypeCannotBeDeletedException("Fuel type", "name", fuelTypeRepository.getByField("fuelTypeId", fuelTypeId).getFuelTypeName());
        } else {
            fuelTypeRepository.delete(fuelTypeId);
        }
    }

    @Override
    public List<FuelType> getAllCarsByUserId(Long userId) {
        return fuelTypeRepository.getAllFuelTypesByUserId(userId);
    }

    @Override
    public FuelType getFuelTypeByName(String fuelTypeName) {
        return fuelTypeRepository.getFuelTypeByName(fuelTypeName);
    }
}

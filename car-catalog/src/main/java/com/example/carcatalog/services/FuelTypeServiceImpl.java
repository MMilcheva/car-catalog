package com.example.carcatalog.services;

import com.example.carcatalog.dto.FuelTypeFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.models.FuelType;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.FuelTypeRepository;
import com.example.carcatalog.services.contracts.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelTypeServiceImpl implements FuelTypeService {

    private final FuelTypeRepository fuelTypeRepository;


    @Autowired
    public FuelTypeServiceImpl(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
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
    public void deleteFuelType(long fuelTypeId, User user) {
        fuelTypeRepository.delete(fuelTypeId);
    }

    @Override
    public FuelType createFuelType(FuelType fuelType) {

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
        List<FuelType> existingFuelTypes = fuelTypeRepository.findByFuelTypeName(fuelType.getFuelTypeName());
        if (!existingFuelTypes.isEmpty()) {
            throw new DuplicateEntityException("FuelType", "name", existingFuelTypes.get(0).getFuelTypeName());
        } else {
            fuelTypeRepository.update(fuelType);
            return fuelType;
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

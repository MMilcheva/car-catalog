package com.example.carcatalog.services.contracts;

import com.example.carcatalog.dto.FuelTypeFilterOptions;
import com.example.carcatalog.models.FuelType;
import com.example.carcatalog.models.User;

import java.util.List;
import java.util.Optional;

public interface FuelTypeService {
    FuelType getFuelTypeById(Long fuelTypeId);

    List<FuelType> getAllFuelTypes(Optional<String> search);

    List<FuelType> getAllFuelTypeFilter(FuelTypeFilterOptions fuelTypeFilterOptions);

    void deleteFuelType(long fuelTypeId, User user);

    FuelType createFuelType(FuelType fuelType);

    FuelType updateFuelType(FuelType fuelType);

    List<FuelType> getAllCarsByUserId(Long userId);

    FuelType getFuelTypeByName(String fuelTypeName);
}

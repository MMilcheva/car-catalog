package com.example.carcatalog.repositories.contracts;

import com.example.carcatalog.dto.FuelTypeFilterOptions;
import com.example.carcatalog.models.FuelType;

import java.util.List;
import java.util.Optional;

public interface FuelTypeRepository extends BaseCRUDRepository<FuelType> {
    List<FuelType> getAllFuelTypes(Optional<String> search);

    List<FuelType> getAllFuelTypesFilter(FuelTypeFilterOptions fuelTypeFilterOptions);

    List<FuelType> filter(Optional<String> fuelTypeName,
                          Optional<String> sortBy,
                          Optional<String> sortOrder);

    List<FuelType> getAllFuelTypesByUserId(Long userId);

    FuelType getFuelTypeByName(String fuelTypeName);
}

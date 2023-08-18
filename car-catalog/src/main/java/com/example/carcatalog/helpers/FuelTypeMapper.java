package com.example.carcatalog.helpers;


import com.example.carcatalog.dto.FuelTypeResponse;
import com.example.carcatalog.dto.FuelTypeSaveRequest;

import com.example.carcatalog.models.FuelType;

import com.example.carcatalog.services.contracts.FuelTypeService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FuelTypeMapper {
    private final FuelTypeService fuelTypeService;

    public FuelTypeMapper(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    public FuelType convertToFuelTypeToBeUpdated(Long fuelTypeToBeUpdatedId, FuelTypeSaveRequest fuelTypeSaveRequest) {
        FuelType fuelTypeToBeUpdated = fuelTypeService.getFuelTypeById(fuelTypeToBeUpdatedId);

        fuelTypeToBeUpdated.setFuelTypeName(fuelTypeSaveRequest.getFuelTypeName());


        return fuelTypeToBeUpdated;
    }

    public FuelType convertToFuelType(FuelTypeSaveRequest fuelTypeSaveRequest) {
        FuelType fuelType = new FuelType();
        fuelType.setFuelTypeName(fuelTypeSaveRequest.getFuelTypeName());

        return fuelType;
    }

    public FuelTypeResponse convertToFuelTypeResponse(FuelType fuelType) {

        FuelTypeResponse fuelTypeResponse = new FuelTypeResponse();

        fuelTypeResponse.setFuelTypeName(fuelType.getFuelTypeName());


        return fuelTypeResponse;
    }
    public List<FuelTypeResponse> convertToFuelTypeResponses(List<FuelType> fuelTypes) {

        List<FuelTypeResponse> fuelTypeResponses = new ArrayList<>();

        fuelTypes.forEach(ft->fuelTypeResponses.add(convertToFuelTypeResponse(ft)));
        return fuelTypeResponses;
    }
}

package com.example.carcatalog.services;

import com.example.carcatalog.dto.TransmissionTypeFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.exceptions.ModelCannotBeDeletedException;
import com.example.carcatalog.exceptions.TransmissionTypeCannotBeDeletedException;
import com.example.carcatalog.models.Car;
import com.example.carcatalog.models.TransmissionType;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.CarRepository;
import com.example.carcatalog.repositories.contracts.TransmissionTypeRepository;
import com.example.carcatalog.services.contracts.TransmissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransmissionTypeServiceImpl implements TransmissionTypeService {

    private final TransmissionTypeRepository transmissionTypeRepository;
    private final CarRepository carRepository;

    @Autowired
    public TransmissionTypeServiceImpl(TransmissionTypeRepository transmissionTypeRepository, CarRepository carRepository) {
        this.transmissionTypeRepository = transmissionTypeRepository;
        this.carRepository = carRepository;
    }

    @Override
    public TransmissionType getTransmissionTypeById(Long transmissionTypeId) {
        return transmissionTypeRepository.getById(transmissionTypeId);
    }

    @Override
    public List<TransmissionType> getAllTransmissionTypes(Optional<String> search) {
        return transmissionTypeRepository.getAllTransmissionTypes(search);
    }

    @Override
    public List<TransmissionType> getAllTransmissionTypeFilter(TransmissionTypeFilterOptions transmissionTypeFilterOptions) {
        return transmissionTypeRepository.getAllTransmissionTypesFilter(transmissionTypeFilterOptions);
    }


    @Override
    public TransmissionType createTransmissionType(TransmissionType transmissionType) {
        transmissionType.setTransmissionTypeName(transmissionType.getTransmissionTypeName().toUpperCase());
        List<TransmissionType> existingTransmissionTypes = transmissionTypeRepository.findByTransmissionTypeName(transmissionType.getTransmissionTypeName());
        if (!existingTransmissionTypes.isEmpty()) {
            throw new DuplicateEntityException("Transmission type", "name", existingTransmissionTypes.get(0).getTransmissionTypeName());
        } else {
            transmissionTypeRepository.create(transmissionType);
            return transmissionType;
        }

    }

    @Override
    public TransmissionType updateTransmissionType(TransmissionType transmissionType) {
        transmissionType.setTransmissionTypeName(transmissionType.getTransmissionTypeName().toUpperCase());
        List<TransmissionType> existingTransmissionTypes = transmissionTypeRepository.findByTransmissionTypeName(transmissionType.getTransmissionTypeName());
        if (!existingTransmissionTypes.isEmpty()) {
            throw new DuplicateEntityException("Transmission type", "name", existingTransmissionTypes.get(0).getTransmissionTypeName());
        } else {
            transmissionTypeRepository.update(transmissionType);
            return transmissionType;
        }

    }

    @Override
    public void deleteTransmissionType(long transmissionTypeId, User user) {

        TransmissionType transmissionTypeIdToBeDeleted = transmissionTypeRepository.getById(transmissionTypeId);
        List<Car> existingTransmissionTypes = carRepository.getAllCars(transmissionTypeIdToBeDeleted.getTransmissionTypeName().describeConstable());
        if (!existingTransmissionTypes.isEmpty()) {
            throw new TransmissionTypeCannotBeDeletedException("TransmissionType", "name", transmissionTypeRepository.getByField("transmissionTypeId", transmissionTypeId).getTransmissionTypeName());
        } else {
            transmissionTypeRepository.delete(transmissionTypeId);
        }
    }

    @Override
    public TransmissionType getTransmissionTypeByName(String transmissionTypeName) {
        return transmissionTypeRepository.getByField("transmissionTypeName", transmissionTypeName);
    }

}

package com.example.carcatalog.services;

import com.example.carcatalog.dto.TransmissionTypeFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.models.TransmissionType;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.TransmissionTypeRepository;
import com.example.carcatalog.services.contracts.TransmissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransmissionTypeServiceImpl implements TransmissionTypeService {

    private final TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    public TransmissionTypeServiceImpl(TransmissionTypeRepository transmissionTypeRepository) {
        this.transmissionTypeRepository = transmissionTypeRepository;
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
    public void deleteTransmissionType(long transmissionTypeId, User user) {
//        checkModifyPermissions(user);
        transmissionTypeRepository.delete(transmissionTypeId);
    }

    @Override
    public TransmissionType createTransmissionType(TransmissionType transmissionType) {

        TransmissionType existingTransmissionType = transmissionTypeRepository.getTransmissionTypeByName(transmissionType.getTransmissionTypeName());
        if (existingTransmissionType != null) {
            throw new DuplicateEntityException("Transmission type", "name", existingTransmissionType.getTransmissionTypeName());
        } else {
            transmissionTypeRepository.create(transmissionType);
            return transmissionType;
        }

    }

    @Override
    public TransmissionType updateTransmissionType(TransmissionType transmissionType) {
        TransmissionType existingTransmissionType = transmissionTypeRepository.getTransmissionTypeByName(transmissionType.getTransmissionTypeName());
        if (existingTransmissionType != null) {
            throw new DuplicateEntityException("Transmission type", "name", existingTransmissionType.getTransmissionTypeName());
        } else {
            transmissionTypeRepository.update(transmissionType);
            return transmissionType;
        }

    }

    @Override
    public List<TransmissionType> getAllTransmissionTypesByUserId(Long userId) {
        return transmissionTypeRepository.getAllTransmissionTypesByUserId(userId);
    }

    @Override
    public TransmissionType getTransmissionTypeByName(String transmissionTypeName) {
        return transmissionTypeRepository.getTransmissionTypeByName(transmissionTypeName);
    }

}

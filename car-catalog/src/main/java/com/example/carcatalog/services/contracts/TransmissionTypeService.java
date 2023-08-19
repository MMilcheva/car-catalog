package com.example.carcatalog.services.contracts;

import com.example.carcatalog.dto.TransmissionTypeFilterOptions;
import com.example.carcatalog.models.TransmissionType;
import com.example.carcatalog.models.User;

import java.util.List;
import java.util.Optional;

public interface TransmissionTypeService {

    TransmissionType getTransmissionTypeById(Long transmissionTypeId);

    List<TransmissionType> getAllTransmissionTypes(Optional<String> search);

    List<TransmissionType> getAllTransmissionTypeFilter(TransmissionTypeFilterOptions transmissionTypeFilterOptions);

    void deleteTransmissionType(long transmissionTypeId, User user);

    TransmissionType createTransmissionType(TransmissionType transmissionType);

    TransmissionType updateTransmissionType(TransmissionType transmissionType);

    TransmissionType getTransmissionTypeByName(String transmissionTypeName);
}

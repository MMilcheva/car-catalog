package com.example.carcatalog.repositories.contracts;

import com.example.carcatalog.dto.TransmissionTypeFilterOptions;
import com.example.carcatalog.models.TransmissionType;

import java.util.List;
import java.util.Optional;

public interface TransmissionTypeRepository extends BaseCRUDRepository<TransmissionType> {
    List<TransmissionType> getAllTransmissionTypesByUserId(Long userId);

    List<TransmissionType> getAllTransmissionTypes(Optional<String> search);

    List<TransmissionType> getAllTransmissionTypesFilter(TransmissionTypeFilterOptions transmissionTypeFilterOptions);

    List<TransmissionType> filter(Optional<String> transmissionTypeName,
                                  Optional<String> sortBy,
                                  Optional<String> sortOrder);

    TransmissionType getTransmissionTypeByName(String transmissionTypeName);

    List<TransmissionType> findByTransmissionTypeName(String transmissionTypeName);
}

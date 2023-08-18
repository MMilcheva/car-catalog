package com.example.carcatalog.helpers;


import com.example.carcatalog.dto.TransmissionTypeResponse;
import com.example.carcatalog.dto.TransmissionTypeSaveRequest;
import com.example.carcatalog.models.TransmissionType;
import com.example.carcatalog.services.contracts.TransmissionTypeService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransmissionTypeMapper {
    private final TransmissionTypeService transmissionTypeService;

    public TransmissionTypeMapper(TransmissionTypeService transmissionTypeService) {
        this.transmissionTypeService = transmissionTypeService;
    }

    public TransmissionType convertToTransmissionTypeToBeUpdated(Long transmissionTypeToBeUpdatedId, TransmissionTypeSaveRequest transmissionTypeSaveRequest) {
        TransmissionType transmissionTypeToBeUpdated = transmissionTypeService.getTransmissionTypeById(transmissionTypeToBeUpdatedId);

        transmissionTypeToBeUpdated.setTransmissionTypeName(transmissionTypeSaveRequest.getTransmissionTypeName());


        return transmissionTypeToBeUpdated;
    }

    public TransmissionType convertToTransmissionType(TransmissionTypeSaveRequest transmissionTypeSaveRequest) {
        TransmissionType transmissionType = new TransmissionType();
        transmissionType.setTransmissionTypeName(transmissionTypeSaveRequest.getTransmissionTypeName());

        return transmissionType;
    }

    public TransmissionTypeResponse convertToTransmissionTypeResponse(TransmissionType transmissionType) {

        TransmissionTypeResponse transmissionTypeResponse = new TransmissionTypeResponse();

        transmissionTypeResponse.setTransmissionTypeName(transmissionType.getTransmissionTypeName());


        return transmissionTypeResponse;
    }

    public List<TransmissionTypeResponse> convertToTransmissionTypeResponses(List<TransmissionType> transmissionTypes) {

        List<TransmissionTypeResponse> transmissionTypeResponses = new ArrayList<>();

        transmissionTypes.forEach(ft -> transmissionTypeResponses.add(convertToTransmissionTypeResponse(ft)));
        return transmissionTypeResponses;
    }
}

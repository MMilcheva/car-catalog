package com.example.carcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransmissionTypeFilterDto {

    private String transmissionTypeName;
    private String sortBy;
    private String sortOrder;

}

package com.example.carcatalog.dto;

import java.util.Optional;

public class TransmissionTypeFilterOptions {

    private Optional<String> TransmissionTypeName;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public TransmissionTypeFilterOptions(Optional<String> transmissionTypeName, Optional<String> sortBy, Optional<String> sortOrder) {
        TransmissionTypeName = transmissionTypeName;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public Optional<String> getTransmissionTypeName() {
        return TransmissionTypeName;
    }

    public void setTransmissionTypeName(Optional<String> transmissionTypeName) {
        TransmissionTypeName = transmissionTypeName;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }
}

package com.example.carcatalog.dto;

import java.util.Optional;

public class ModelFilterOptions {
    private Optional<Long> modelId;
    private Optional<String> modelName;
    private Optional<String> brandName;
    private Optional<String> sortBy;

    private Optional<String> sortOrder;

    private boolean combineResults;

    public ModelFilterOptions(Long modelId, String modelName, String brandName, String sortBy, String sortOrder) {
        this.modelId = Optional.ofNullable(modelId);
        this.brandName = Optional.ofNullable(brandName);
        this.modelName = Optional.ofNullable(modelName);

        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
        this.combineResults = true;
    }

    public Optional<Long> getModelId() {
        return modelId;
    }

    public void setModelId(Optional<Long> modelId) {
        this.modelId = modelId;
    }

    public Optional<String> getModelName() {
        return modelName;
    }

    public void setModelName(Optional<String> modelName) {
        this.modelName = modelName;
    }

    public Optional<String> getBrandName() {
        return brandName;
    }

    public void setBrandName(Optional<String> brandName) {
        this.brandName = brandName;
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

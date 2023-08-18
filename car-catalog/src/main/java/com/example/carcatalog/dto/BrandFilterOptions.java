package com.example.carcatalog.dto;

import java.util.Optional;

public class BrandFilterOptions {
    private Optional<String> brandName;
    private Optional<String> sortBy;

    private Optional<String> sortOrder;


    public BrandFilterOptions(String brandName, String sortBy, String sortOrder) {
        this.brandName = Optional.ofNullable(brandName);

        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public BrandFilterOptions() {
        this(null, null, null);
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

    public Optional<String> getBrandName() {
        return brandName;
    }

    public void setBrandName(Optional<String> brandName) {
        this.brandName = brandName;
    }


}

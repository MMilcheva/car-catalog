package com.example.carcatalog.repositories.contracts;

import com.example.carcatalog.models.Brand;
import com.example.carcatalog.dto.BrandFilterOptions;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends BaseCRUDRepository<Brand>{
    List<Brand> getAllBrands(Optional<String> search);

    List<Brand> getAllBrandsFilter(BrandFilterOptions brandFilterOptions);

    List<Brand> filter(Optional<String> brandName,
                       Optional<String> sortBy, Optional<String> sortOrder);

    Brand findBrandByName (String brandName);

    List<Brand> findByBrandName(String brandName);
}

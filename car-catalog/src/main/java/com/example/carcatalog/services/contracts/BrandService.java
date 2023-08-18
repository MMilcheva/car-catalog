package com.example.carcatalog.services.contracts;

import com.example.carcatalog.dto.BrandFilterOptions;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.models.User;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand getBrandById(Long brandToBeUpdatedId);

    Brand getBrandByName(String name);

    List<Brand> getAllBrands(Optional<String> search);

    List<Brand> getAllBrandOptions();

    List<Brand> getAllBrandsFilter(BrandFilterOptions brandFilterOptions);

    void deleteBrand(long brandId, User user);

    Brand createBrand(Brand brand);

    Brand updateBrand(Brand brand);
}

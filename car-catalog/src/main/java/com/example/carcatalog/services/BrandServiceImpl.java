package com.example.carcatalog.services;

import com.example.carcatalog.dto.BrandFilterOptions;
import com.example.carcatalog.exceptions.BrandCannotBeDeletedException;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.models.Model;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.BrandRepository;
import com.example.carcatalog.repositories.contracts.ModelRepository;
import com.example.carcatalog.services.contracts.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;

    public BrandServiceImpl(BrandRepository brandRepository, ModelRepository modelRepository) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public Brand getBrandById(Long brandId) {
        return brandRepository.getById(brandId);
    }

    @Override
    public Brand getBrandByName(String brandName) {
        return brandRepository.getByField("brandName", brandName);
    }

    @Override
    public List<Brand> getAllBrands(Optional<String> search) {
        return brandRepository.getAllBrands(search);
    }

    @Override
    public List<Brand> getAllBrandOptions() {
        return brandRepository.getAll();
    }

    @Override
    public List<Brand> getAllBrandsFilter(BrandFilterOptions brandFilterOptions) {
        return brandRepository.getAllBrandsFilter(brandFilterOptions);
    }

    @Override
    public Brand createBrand(Brand brand) {
        List<Brand> existingBrands = brandRepository.findByBrandName(brand.getBrandName());
        if (!existingBrands.isEmpty()) {
            throw new DuplicateEntityException("Brand", "name", existingBrands.get(0).getBrandName());
        } else {
            brandRepository.create(brand);
            return brand;
        }
    }

    @Override
    public Brand updateBrand(Brand brand) {
        List<Brand> existingBrands = brandRepository.findByBrandName(brand.getBrandName());
        if (!existingBrands.isEmpty()) {
            throw new DuplicateEntityException("Brand", "name", existingBrands.get(0).getBrandName());
        } else {
            brandRepository.update(brand);
            return brand;
        }
    }

    @Override
    public void deleteBrand(long brandId, User user) {
        List<Model> existingModels = modelRepository.getAllModelsByBrandId(brandId);
        if (!existingModels.isEmpty()) {
            throw new BrandCannotBeDeletedException("Brand", "name", brandRepository.getByField("brandId", brandId).getBrandName());
        } else {
            brandRepository.delete(brandId);
        }

    }

}

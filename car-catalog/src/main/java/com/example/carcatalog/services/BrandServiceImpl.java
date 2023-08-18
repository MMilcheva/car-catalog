package com.example.carcatalog.services;

import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.dto.BrandFilterOptions;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.BrandRepository;
import com.example.carcatalog.repositories.contracts.UserRepository;
import com.example.carcatalog.services.contracts.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    public BrandServiceImpl(BrandRepository brandRepository, UserRepository userRepository) {
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
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
    public void deleteBrand(long brandId, User user) {
        brandRepository.delete(brandId);
    }


    @Override
    public Brand createBrand(Brand brand) {
        List<Brand> existingBrands = brandRepository.findByBrandName(brand.getBrandName());
        if (!existingBrands.isEmpty()) {
            // Use the first existing brand
            throw new DuplicateEntityException("brand", "name", existingBrands.get(0).getBrandName());
        } else {
            brandRepository.create(brand);
            return brand;
        }
    }

//    @Override
//    public Brand createBrand(Brand brand) {
//        List<Brand> existingBrands = brandRepository.findByBrandName(brand.getBrandName());
//        if (!existingBrands.isEmpty()) {
//            // Use the first existing brand
//            return existingBrands.get(0);
//        } else {
//            brandRepository.create(brand);
//            return brand;
//        }
//    }
    public Brand checkIfBrandExists(Brand brand) {
        Brand existingBrand = brandRepository.findBrandByName(brand.getBrandName());
        if (existingBrand != null) {
            return existingBrand;
        }
        return brand;
    }

    @Override
    public Brand updateBrand(Brand brand) {
        brandRepository.update(brand);
        return brand;
    }

//    private void checkModifyPermissions(User user) {
//        String str = "admin";
//        if (!(user.getRole().getRoleName().equals(str))) {
//            throw new AuthorizationException(MODIFY_BRAND_ERROR_MESSAGE);
//        }
//    }
}

package com.example.carcatalog.helpers;

import com.example.carcatalog.dto.BrandResponse;
import com.example.carcatalog.dto.BrandSaveRequest;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.services.contracts.BrandService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandMapper {
    private final BrandService brandService;

    public BrandMapper(BrandService brandService) {
        this.brandService = brandService;
    }

    public Brand convertToBrandToBeUpdated(Long brandToBeUpdatedId, BrandSaveRequest brandSaveRequest) {
        Brand brandToBeUpdated = brandService.getBrandById(brandToBeUpdatedId);

        brandToBeUpdated.setBrandName(brandSaveRequest.getBrandName());


        return brandToBeUpdated;
    }

    public Brand convertToBrand(BrandSaveRequest brandSaveRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandSaveRequest.getBrandName());

        return brand;
    }

    public BrandResponse convertToBrandResponse(Brand brand) {

        BrandResponse brandResponse = new BrandResponse();

        brandResponse.setBrandName(brand.getBrandName());


        return brandResponse;
    }

    public List<BrandResponse> convertToBrandResponses(List<Brand> brands) {

        List<BrandResponse> brandResponses = new ArrayList<>();

        brands.forEach(brand -> brandResponses.add(convertToBrandResponse(brand)));
        return brandResponses;
    }
}

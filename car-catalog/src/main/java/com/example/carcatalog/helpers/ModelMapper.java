package com.example.carcatalog.helpers;


import com.example.carcatalog.dto.ModelResponse;
import com.example.carcatalog.dto.ModelSaveRequest;
import com.example.carcatalog.models.Brand;
import com.example.carcatalog.models.Model;
import com.example.carcatalog.services.contracts.BrandService;
import com.example.carcatalog.services.contracts.ModelService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelMapper {

    private final ModelService modelService;
    private final BrandService brandService;

    public ModelMapper(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }


    public Model convertToCarModel(ModelSaveRequest modelSaveRequest) {
        Model model = new Model();
        model.setModelName(modelSaveRequest.getModelName());

        Brand brand = brandService.getBrandById(modelSaveRequest.getBrandId());
        model.setBrand(brand);
//        model.setArchived(false);
        return model;
    }

    public ModelResponse convertToCarModelResponse(Model model) {

        ModelResponse modelResponse = new ModelResponse();
        modelResponse.setModelName(model.getModelName());
        modelResponse.setBrandName(model.getBrand().getBrandName());
        return modelResponse;
    }
    public List<ModelResponse> convertToCarModelResponses(List<Model> models) {

        List<ModelResponse> modelRespons = new ArrayList<>();

        models.forEach(carModel -> modelRespons.add(convertToCarModelResponse(carModel)));
        return modelRespons;
    }

    public ModelSaveRequest convertToCarModelSaveRequest(Model model) {
        ModelSaveRequest modelSaveRequest = new ModelSaveRequest();
        modelSaveRequest.setModelName(model.getModelName());
        modelSaveRequest.setBrand(model.getBrand());
        return modelSaveRequest;
    }

}

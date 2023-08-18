package com.example.carcatalog.services;

import com.example.carcatalog.dto.ModelFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
import com.example.carcatalog.models.FuelType;
import com.example.carcatalog.models.Model;
import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.BrandRepository;
import com.example.carcatalog.repositories.contracts.ModelRepository;
import com.example.carcatalog.services.contracts.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository, BrandRepository brandRepository) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public Model getModelById(Long modelToBeUpdatedId) {

        return modelRepository.getById(modelToBeUpdatedId);
    }

    @Override
    public Model getModelByName(String name) {
        return modelRepository.getModelByName(name);
    }


    @Override
    public List<Model> getAllModels(Optional<String> search) {

        return modelRepository.getAllModels(search);
    }

    @Override
    public List<Model> getAllModelsFilter(ModelFilterOptions modelFilterOptions) {
        return modelRepository.getAllModelsFilter(modelFilterOptions);
    }

    @Override
    public List<Model> getAllModelsByBrandId(Long brandId) {
        return modelRepository.getAllModelsByBrandId(brandId);
    }


    @Override
    public void deleteModel(long modelId) {
        modelRepository.delete(modelId);
    }

    @Override
    public Model createModel(Model model) {
        Model existingModel = modelRepository.getModelByName(model.getModelName());
        if (existingModel != null) {
            throw new DuplicateEntityException("Model", "name", existingModel.getModelName());
        } else {
            modelRepository.create(model);
            return model;
        }

    }

    @Override
    public Model updateModel(Model model) {
        Model existingModel = modelRepository.getModelByName(model.getModelName());
        if (existingModel != null) {
            throw new DuplicateEntityException("Model", "name", existingModel.getModelName());
        } else {
            modelRepository.update(model);
            return model;
        }

    }

    @Override
    public void block(User user, Long modelId) {
        Model model = modelRepository.getById(modelId);

        modelRepository.block(model.getModelId());
    }

//    getAllModels

//    private void checkModifyPermissions(User user) {
//        String str = "admin";
//        //TODO to check why throw exc while equals is applied
//        if (!(user.getRole().getRoleName().equals(str))) {
//            throw new AuthorizationException(MODIFY_MODEL_ERROR_MESSAGE);
//        }
//    }


}

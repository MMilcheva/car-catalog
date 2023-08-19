package com.example.carcatalog.services;

import com.example.carcatalog.dto.ModelFilterOptions;
import com.example.carcatalog.exceptions.DuplicateEntityException;
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
    public Model getModelByName(String modelName) {
        return modelRepository.getByName(modelName);
    }

    @Override
    public void deleteModel(long modelId) {
        modelRepository.delete(modelId);
    }

    @Override
    public Model createModel(Model model) {
        List<Model> existingModels = modelRepository.findModelsByName(model.getModelName());
        if (!existingModels.isEmpty()) {
            throw new DuplicateEntityException("Model", "name", existingModels.get(0).getModelName());
        } else {
            modelRepository.create(model);
            return model;
        }

    }

    @Override
    public Model updateModel(Model model) {
        List<Model> existingModels = modelRepository.findModelsByName(model.getModelName());
        if (!existingModels.isEmpty()) {
            throw new DuplicateEntityException("Model", "name", existingModels.get(0).getModelName());
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
}

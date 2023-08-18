package com.example.carcatalog.services.contracts;

import com.example.carcatalog.dto.ModelFilterOptions;
import com.example.carcatalog.models.Model;
import com.example.carcatalog.models.User;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    Model getModelById(Long modelToBeUpdatedId);

    Model getModelByName(String name);

    List<Model> getAllModels(Optional<String> search);

    List<Model> getAllModelsFilter(ModelFilterOptions modelFilterOptions);

    List<Model> getAllModelsByBrandId(Long brandId);

    void deleteModel(long modelId);


    void block(User user, Long carModelId);

    Model createModel(Model model);

    Model updateModel(Model model);
}
